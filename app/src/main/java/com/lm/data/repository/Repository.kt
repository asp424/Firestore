package com.lm.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.core.Resource
import com.lm.repository.FirestoreSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {

    suspend fun String.setDataToDocumentR(
        docName: String,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    )

    suspend fun String.getAllDocumentsInCollection(): Flow<Resource<QuerySnapshot?>>

    suspend fun String.getDataFromDocument(doc: String): Flow<Resource<DocumentSnapshot?>>

    class Base @Inject constructor(private val fireStoreSource: FirestoreSource) : Repository {

        override suspend fun String.setDataToDocumentR(
            docName: String,
            data: HashMap<String, String>,
            onSuccess: () -> Unit
        ) {
            fireStoreSource.apply { setDataToDocument(docName, data) { onSuccess() } }
        }

        override suspend fun String.getAllDocumentsInCollection() = with(fireStoreSource) { getAllDocumentsInCollection() }

        override suspend fun String.getDataFromDocument(doc: String) =
            with(fireStoreSource) { getDataFromDocument(doc) }
    }
}
