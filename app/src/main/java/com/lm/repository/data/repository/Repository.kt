package com.lm.repository.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.FirePath
import com.lm.repository.data.sources.FirestoreSource
import com.lm.repository.data.sources.FirestoreSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {

    suspend fun putDataToDocumentR(
        path: FirePath, data: HashMap<String, String>,
        onSuccess: () -> Unit
    )

    suspend fun takeAllDocumentsInCollection(path: FirePath): Flow<Resource<QuerySnapshot?>>

    suspend fun dataFromDocument(path: FirePath): Flow<Resource<DocumentSnapshot?>>

    suspend fun deleteDocument(path: FirePath, onSuccess: () -> Unit)

    suspend fun deleteField(path: FirePath, field: String, onSuccess: () -> Unit)

    suspend fun clearDocument(path: FirePath, onSuccess: () -> Unit)

    suspend fun deleteCollection(path: FirePath, onSuccess: () -> Unit)

}
