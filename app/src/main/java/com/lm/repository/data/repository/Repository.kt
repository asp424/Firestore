package com.lm.repository.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.sources.FirestoreSource
import com.lm.repository.data.sources.FirestoreSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {

    suspend fun setDataToDocumentR(map: List<String>, data: HashMap<String, String>,
        onSuccess: () -> Unit
    )

    suspend fun getAllDocumentsInCollection(map: List<String>): Flow<Resource<QuerySnapshot?>>

    suspend fun getDataFromDocument(map: List<String>): Flow<Resource<DocumentSnapshot?>>

    suspend fun deleteDocument(map: List<String>, onSuccess: () -> Unit)

    suspend fun deleteField(map: List<String>, field: String, onSuccess: () -> Unit)

    suspend fun clearDocument(map: List<String>, onSuccess: () -> Unit)

    suspend fun deleteCollection(map: List<String>, onSuccess: () -> Unit)

}
