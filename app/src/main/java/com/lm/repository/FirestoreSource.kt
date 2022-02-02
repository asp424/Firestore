package com.lm.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lm.core.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirestoreSource {
    // String - имя коллекции
    suspend fun String.getAllDocumentsInCollection(): Flow<Resource<QuerySnapshot?>>

    suspend fun String.getDataFromDocument(docName: String): Flow<Resource<DocumentSnapshot?>>

    suspend fun String.setDataToDocument(
        docName: String,
        data: HashMap<String, String>,
        onSuccess: (Any?) -> Unit
    )

    class Base @Inject constructor() : FirestoreSource {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun String.getAllDocumentsInCollection() = runFlow(getDocuments)

        override suspend fun String.getDataFromDocument(docName: String) =
            runFlow(collection.document(docName).get())

        override suspend fun String.setDataToDocument(
            docName: String,
            data: HashMap<String, String>,
            onSuccess: (Any?) -> Unit
        ) { collection.apply {
                if (docName.isNotEmpty())
                     onSuccess(runTask(document(docName).set(data, SetOptions.merge())))
                else onSuccess(runTask(add(data)))
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
}


