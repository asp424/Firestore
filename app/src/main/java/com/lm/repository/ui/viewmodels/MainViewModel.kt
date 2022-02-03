package com.lm.repository.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun setDataToDocument(
        map: List<String>,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        with(repository) { setDataToDocumentR(map, data) { onSuccess() } }
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllDocumentsInCollection(map: List<String>): StateFlow<Resource<QuerySnapshot?>> =
        with(repository){ getAllDocumentsInCollection(map).flatMapLatest { flowOf(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = com.lm.repository.core.Resource.Loading
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun getDataFromDocument(map: List<String>): StateFlow<Resource<DocumentSnapshot?>> =
        with(repository){ getDataFromDocument(map).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = com.lm.repository.core.Resource.Loading
            )
        }

    fun deleteDocument(map: List<String>, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteDocument(map) { onSuccess() } }
        }

    fun deleteField(map: List<String>, field: String, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteField(map, field) { onSuccess() } }
        }

    fun clearDocument(map: List<String>, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { clearDocument(map) { onSuccess() } }
        }

    fun deleteCollection(map: List<String>, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            with(repository) { deleteCollection(map) { onSuccess() } }
        }
}