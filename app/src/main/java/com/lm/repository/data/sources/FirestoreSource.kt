package com.lm.repository.data.sources

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
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
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirestoreSource {

    suspend fun takeAllDocumentsInCollection(path: FirePath): Flow<Resource<QuerySnapshot?>>

    suspend fun dataFromDocument(path: FirePath): Flow<Resource<DocumentSnapshot?>>

    suspend fun putDataToDocument(path: FirePath, data: HashMap<String, String>,
        onSuccess: (Any?) -> Unit
    )

    suspend fun deleteDocument(path: FirePath, onSuccess: (Any?) -> Unit)

    suspend fun deleteField(
        path: FirePath,
        field: String,
        onSuccess: (Any?) -> Unit
    )

    suspend fun clearDocument(path: FirePath, onSuccess: () -> Unit)

    suspend fun deleteCollection(path: FirePath, onSuccess: () -> Unit)

}




