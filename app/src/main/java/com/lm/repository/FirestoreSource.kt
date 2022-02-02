package com.lm.repository

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
        onSuccess: () -> Unit
    )

    class Base @Inject constructor() : FirestoreSource {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun String.getAllDocumentsInCollection():
                Flow<Resource<QuerySnapshot?>> =
            callbackFlow { collection.get()
                .addOnSuccessListener { trySendBlocking(Resource.Success(it)) }
                awaitClose()
            }

        override suspend fun String.getDataFromDocument(docName: String):
                Flow<Resource<DocumentSnapshot?>> =
            callbackFlow { collection.apply { document(docName).get()
                .addOnCompleteListener { trySendBlocking(Resource.Success(it.result)) }
                }
                awaitClose()
            }

        override suspend fun String.setDataToDocument(
            docName: String,
            data: HashMap<String, String>,
            onSuccess: () -> Unit
        ) {
            runCatching {
                if (docName.isNotEmpty())
                    collection.document(docName).set(data, SetOptions.merge()).addOnCompleteListener { onSuccess() }
                else
                // автоген имени документа
                    collection.add(data).addOnCompleteListener { onSuccess() }
            }
        }

        private val String.collection get() = db.collection(this)

        private val db by lazy { Firebase.firestore }
    }
}