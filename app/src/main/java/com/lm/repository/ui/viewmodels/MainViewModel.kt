package com.lm.repository.ui.viewmodels

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.repository.firestore.FirestoreRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(),
    DefaultLifecycleObserver {

    private val _pagerState = MutableStateFlow(0)
    val pagerState get() = _pagerState

    private var timerJob: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
       timer()
    }

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

    private fun timer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(dispatcher) {
            (0 until 1000).asSequence().asFlow().onEach { delay(3_000) }
                .collect { _pagerState.value = it }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
       timerJob?.cancel()
    }
}