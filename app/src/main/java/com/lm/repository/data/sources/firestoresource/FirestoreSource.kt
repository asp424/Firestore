package com.lm.repository.data.sources.firestoresource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.models.User
import kotlinx.coroutines.flow.Flow

interface FirestoreSource {

    suspend fun takeAllDocumentsInCollection(path: FirePath): Flow<Resource<QuerySnapshot?>>

    suspend fun dataFromDocument(path: FirePath): Flow<Resource<DocumentSnapshot?>>

    suspend fun putDataToDocument(
        path: FirePath, data: HashMap<String, String>,
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

    fun user(): User

    suspend fun readUser()

}




