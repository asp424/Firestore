package com.lm.repository.data.sources

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lm.repository.core.Resource
import com.lm.repository.data.FirePath
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

 class FirestoreSourceImpl @Inject constructor() : FirestoreSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun takeAllDocumentsInCollection(path: FirePath) =
         runFlow(path.collection.doc)

    override suspend fun dataFromDocument(path: FirePath) =
        runFlow(path.collection.col.document(path.document).get())

    override suspend fun putDataToDocument(
        path: FirePath,
        data: HashMap<String, String>,
        onSuccess: (Any?) -> Unit
    ) = with(path.collection.col) {
        if (path.document.isNotEmpty())
            onSuccess(runTask(document(path.document).set(data, SetOptions.merge())))
        else onSuccess(runTask(add(data)))
    }

    override suspend fun deleteDocument(path: FirePath, onSuccess: (Any?) -> Unit) =
        onSuccess(runTask(path.collection.col.document(path.document).delete()))

    override suspend fun deleteField(
        path: FirePath,
        field: String,
        onSuccess: (Any?) -> Unit
    ) = onSuccess(
        runTask(
            path.collection.col.document(path.document)
                .update(hashMapOf<String, Any>(field to FieldValue.delete()))
        )
    )

    override suspend fun clearDocument(
        path: FirePath,
        onSuccess: () -> Unit
    ) = dataFromDocument(path).collect {
        if (it is Resource.Success) it.data.data?.forEach { maps ->
            deleteField(path, maps.key) { onSuccess() }
        }
    }

     override suspend fun deleteCollection(path: FirePath, onSuccess: () -> Unit) =
         takeAllDocumentsInCollection(path).collect{
             if (it is Resource.Success) it.data.documents.forEach { doc ->
                 deleteDocument(FirePath(path.collection, doc.id) ){ onSuccess() }
             }
         }

     private suspend fun <T> runTask(task: Task<T>): T = suspendCoroutine { cont ->
        runCatching { task.addOnSuccessListener { cont.resume(it) } }
    }

    private suspend fun <T> runFlow(task: Task<T>): Flow<Resource<T>> = callbackFlow {
        trySendBlocking(Resource.Success(runTask(task)))
        awaitClose()
    }

    private val String.doc get() = col.get()

    private val String.col get() = db.collection(this)

    private val db by lazy { Firebase.firestore }

}
