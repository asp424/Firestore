package com.lm.repository.data.sources

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lm.repository.core.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirestoreSource {
   
    suspend fun getAllDocumentsInCollection(map: List<String>): Flow<Resource<QuerySnapshot?>>

    suspend fun getDataFromDocument(map: List<String>): Flow<Resource<DocumentSnapshot?>>

    suspend fun setDataToDocument(map: List<String>, data: HashMap<String, String>,
        onSuccess: (Any?) -> Unit
    )

    suspend fun deleteDocument(map: List<String>, onSuccess: (Any?) -> Unit)

    suspend fun deleteField(
        map: List<String>,
        field: String,
        onSuccess: (Any?) -> Unit
    )

    suspend fun clearDocument(map: List<String>, onSuccess: () -> Unit)

    suspend fun deleteCollection(map: List<String>, onSuccess: () -> Unit)

}




