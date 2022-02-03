package com.lm.repository.data.sources

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lm.repository.core.Resource
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
    override suspend fun getAllDocumentsInCollection(map: List<String>) = runFlow(map[0].getDocuments)

    override suspend fun getDataFromDocument(map: List<String>) =
        runFlow(map[0].collection.document(map[1]).get())

    override suspend fun setDataToDocument(
        map: List<String>,
        data: HashMap<String, String>,
        onSuccess: (Any?) -> Unit
    ) = with(map[0].collection) {
        if (map[1].isNotEmpty())
            onSuccess(runTask(document(map[1]).set(data, SetOptions.merge())))
        else onSuccess(runTask(add(data)))
    }

    override suspend fun deleteDocument(map: List<String>, onSuccess: (Any?) -> Unit) =
        onSuccess(runTask(map[0].collection.document(map[1]).delete()))

    override suspend fun deleteField(
        map: List<String>,
        field: String,
        onSuccess: (Any?) -> Unit
    ) = onSuccess(
        runTask(
            map[0].collection.document(map[1])
                .update(hashMapOf<String, Any>(field to FieldValue.delete()))
        )
    )

    override suspend fun clearDocument(
        map: List<String>,
        onSuccess: () -> Unit
    ) = getDataFromDocument(map).collect {
        if (it is Resource.Success) it.data.data?.forEach { maps ->
            deleteField(map, maps.key) { onSuccess() }
        }
    }

     override suspend fun deleteCollection(map: List<String>, onSuccess: () -> Unit) =
         getAllDocumentsInCollection(map).collect{
             if (it is Resource.Success) it.data.documents.forEach { doc ->
                 deleteDocument(listOf(map[0], doc.id)){ onSuccess() }
             }
         }

     private suspend fun <T> runTask(task: Task<T>): T = suspendCoroutine { cont ->
        runCatching { task.addOnSuccessListener { cont.resume(it) } }
    }

    private suspend fun <T> runFlow(task: Task<T>): Flow<Resource<T>> = callbackFlow {
        trySendBlocking(Resource.Success(runTask(task)))
        awaitClose()
    }

    private val String.getDocuments get() = collection.get()

    private val String.collection get() = db.collection(this)

    private val db by lazy { Firebase.firestore }

}
