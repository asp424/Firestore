package com.lm.repository.data.repository.firestore

import com.lm.repository.data.models.FirePath
import com.lm.repository.data.sources.firestoresource.FirestoreSource
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(private val fireStoreSource: FirestoreSource) :
    FirestoreRepository {

    override suspend fun putDataToDocumentR(
        path: FirePath, data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = with(fireStoreSource){ putDataToDocument(path, data) { onSuccess() } }

    override suspend fun allDocumentsInCollection(path: FirePath) =
        with(fireStoreSource) { takeAllDocumentsInCollection(path) }

    override suspend fun dataFromDocument(path: FirePath) =
        with(fireStoreSource) { dataFromDocument(path) }

    override suspend fun deleteDocument(path: FirePath, onSuccess: () -> Unit) =
        with(fireStoreSource) { deleteDocument(path) { onSuccess() } }

    override suspend fun deleteField(path: FirePath, field: String, onSuccess: () -> Unit) =
        with(fireStoreSource) { deleteField(path, field) { onSuccess() } }

    override suspend fun clearDocument(path: FirePath, onSuccess: () -> Unit) =
        with(fireStoreSource) { clearDocument(path) { onSuccess() } }

    override suspend fun deleteCollection(path: FirePath, onSuccess: () -> Unit) {
        with(fireStoreSource) { deleteCollection(path){ onSuccess() } }
        }
    }
