package com.lm.repository.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.repository.firestore.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: FirestoreRepository) : ViewModel() {

    fun putDataToDocument(
        path: FirePath,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        with(repository) { putDataToDocumentR(path, data) { onSuccess() } }
    }

    @ExperimentalCoroutinesApi
    suspend fun allDocumentsInCollection(path: FirePath): StateFlow<Resource<QuerySnapshot?>> =
        with(repository){ allDocumentsInCollection(path).flatMapLatest { flowOf(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Loading
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun dataFromDocumentV(path: FirePath): StateFlow<Resource<DocumentSnapshot?>> =
        with(repository){ dataFromDocument(path).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Resource.Loading
            )
        }

    fun deleteDocument(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteDocument(path) { onSuccess() } }
        }

    fun deleteField(path: FirePath, field: String, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteField(path, field) { onSuccess() } }
        }

    fun clearDocument(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { clearDocument(path) { onSuccess() } }
        }

    fun deleteCollection(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteCollection(path) { onSuccess() } }
        }
}