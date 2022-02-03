package com.lm.repository.data.repository

import com.lm.repository.data.sources.FirestoreSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val fireStoreSource: FirestoreSource) :
    Repository {

    override suspend fun setDataToDocumentR(
        map: List<String>, data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = with(fireStoreSource){ setDataToDocument(map, data) { onSuccess() } }

    override suspend fun getAllDocumentsInCollection(map: List<String>) =
        with(fireStoreSource) { getAllDocumentsInCollection(map) }

    override suspend fun getDataFromDocument(map: List<String>) =
        with(fireStoreSource) { getDataFromDocument(map) }

    override suspend fun deleteDocument(map: List<String>, onSuccess: () -> Unit) =
        with(fireStoreSource) { deleteDocument(map) { onSuccess() } }

    override suspend fun deleteField(map: List<String>, field: String, onSuccess: () -> Unit) =
        with(fireStoreSource) { deleteField(map, field) { onSuccess() } }

    override suspend fun clearDocument(map: List<String>, onSuccess: () -> Unit) =
        with(fireStoreSource) { clearDocument(map) { onSuccess() } }

    override suspend fun deleteCollection(map: List<String>, onSuccess: () -> Unit) {
        with(fireStoreSource) { deleteCollection(map){ onSuccess() } }
        }
    }
