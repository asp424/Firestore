package com.lm.repository.ui.viewmodels

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.models.User
import com.lm.repository.data.repository.firestore.FirestoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: FirestoreRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), DefaultLifecycleObserver {


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        readUser()
    }

    private val _bottomSheet = MutableStateFlow(1)
    val bottomSheet get() = _bottomSheet

    private val _drawerHeader = MutableStateFlow("Главная")
    val drawerHeader get() = _drawerHeader

    private var _authButton = MutableStateFlow(false)
    val authButton = _authButton

    fun invisibleButton(){ _authButton.value = false }

    fun putDataToDocument(
        path: FirePath,
        data: HashMap<String, String>,
        onSuccess: () -> Unit
    ) = viewModelScope.launch(dispatcher) {
        with(repository) { putDataToDocumentR(path, data) { onSuccess() } }
    }

    @ExperimentalCoroutinesApi
    suspend fun allDocumentsInCollection(path: FirePath): StateFlow<Resource<QuerySnapshot?>> =
        with(repository) {
            allDocumentsInCollection(path).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Resource.Loading
            )
        }

    @ExperimentalCoroutinesApi
    suspend fun dataFromDocumentV(path: FirePath): StateFlow<Resource<DocumentSnapshot?>> =
        with(repository) {
            dataFromDocument(path).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Resource.Loading
            )
        }

    fun deleteDocument(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            with(repository) { deleteDocument(path) { onSuccess() } }
        }

    fun deleteField(path: FirePath, field: String, onSuccess: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            with(repository) { deleteField(path, field) { onSuccess() } }
        }

    fun clearDocument(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            with(repository) { clearDocument(path) { onSuccess() } }
        }

    fun deleteCollection(path: FirePath, onSuccess: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            with(repository) { deleteCollection(path) { onSuccess() } }
        }

    fun bottomSheetAction() = _bottomSheet.value++

    fun setDrawerHeader(header: String) {
        _drawerHeader.value = header
    }

    fun user(): User = repository.user()

   fun readUser() = viewModelScope.launch {
        repository.readUser {
            _authButton.value = true
        }
    }
}