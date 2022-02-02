package com.lm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.core.Resource
import com.lm.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun String.setDataToDocument(
        docName: String,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        with(repository) { setDataToDocumentR(docName, data) { onSuccess() } }
    }

    @ExperimentalCoroutinesApi
    suspend fun String.getAllDocumentsInCollection(): StateFlow<Resource<QuerySnapshot?>> =
        with(repository){ getAllDocumentsInCollection().flatMapLatest { flowOf(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Loading
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun String.getDataFromDocument(doc: String): StateFlow<Resource<DocumentSnapshot?>> =
        with(repository){ getDataFromDocument(doc).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Resource.Loading
            )
        }
}