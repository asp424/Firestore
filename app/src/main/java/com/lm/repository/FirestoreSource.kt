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

interface FirestoreSource {
    // String - имя коллекции
    suspend fun String.getAllDocumentsInCollection(): Flow<Resource<QuerySnapshot?>>

    suspend fun String.getDataFromDocument(docName: String): Flow<Resource<DocumentSnapshot?>>

    suspend fun String.setDataToDocument(
        docName: String,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    )

    class Base @Inject constructor() : FirestoreSource {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun String.getAllDocumentsInCollection() =
            runFlow(collection.get())

        override suspend fun String.getDataFromDocument(docName: String) =
            runFlow(collection.document(docName).get())

        override suspend fun String.setDataToDocument(
            docName: String,
            data: HashMap<String, String>,
            onSuccess: () -> Unit
        ) {
            runCatching {
                if (docName.isNotEmpty())
                    collection.document(docName).set(data, SetOptions.merge())
                        .apply { success(this) { onSuccess() } }
                else collection.add(data).apply { success(this) { onSuccess() } }
            }
        }

        private val String.collection get() = db.collection(this)

        private val db by lazy { Firebase.firestore }

        private fun <T> success(task: Task<T>, suc: (T) -> Unit) {
            task.addOnSuccessListener { suc(it) }
        }

        private fun <T> runFlow(task: Task<T>): Flow<Resource<T>> = callbackFlow {
                success(task) { trySendBlocking(Resource.Success(it)) }
                awaitClose()
            }
    }
}


