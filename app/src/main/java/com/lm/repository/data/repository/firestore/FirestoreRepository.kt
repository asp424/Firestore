package com.lm.repository.data.repository.firestore


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.models.User
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun putDataToDocumentR(
        path: FirePath, data: HashMap<String, String>,
        onSuccess: () -> Unit
    )

    suspend fun allDocumentsInCollection(path: FirePath): Flow<Resource<QuerySnapshot?>>

    suspend fun dataFromDocument(path: FirePath): Flow<Resource<DocumentSnapshot?>>

    suspend fun deleteDocument(path: FirePath, onSuccess: () -> Unit)

    suspend fun deleteField(path: FirePath, field: String, onSuccess: () -> Unit)

    suspend fun clearDocument(path: FirePath, onSuccess: () -> Unit)

    suspend fun deleteCollection(path: FirePath, onSuccess: () -> Unit)

    fun user (): User

    suspend fun readUser(onSuccess: () -> Unit)

}
