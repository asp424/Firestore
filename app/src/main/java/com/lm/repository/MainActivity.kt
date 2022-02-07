package com.lm.repository

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.repository.core.Resource
import com.lm.repository.core.appComponent
import com.lm.repository.data.models.FirePath
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodelsfactory.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val vm: MainViewModel by viewModels { viewModelFactory }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        vm.apply {
            with(FirePath("aaa", "add")) {
                putDataToDocument(this, hashMapOf(
                    Calendar.getInstance().time.time.toString() to "Hi")) {
                    lifecycleScope.launch {
                        allDocumentsInCollection(this@with).collect(::collectionCollector)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun collectionCollector(resource: Resource<QuerySnapshot?>) {
        when (resource) {
            is Resource.Success -> {
                resource.data?.documents?.forEach {
                    it.data?.keys?.forEach { t ->
                        //binding.tv.text =
                           // "${binding.tv.text}\n ${it.id} [ $t: ${it[t]} ]"
                    }
                }
            }

            is Resource.Failure -> Unit
            is Resource.Loading -> Unit
        }
    }

    @SuppressLint("SetTextI18n")
    private fun documentCollector(resource: Resource<DocumentSnapshot?>) {
        when (resource) {
            is Resource.Success -> {

            }
            is Resource.Failure -> Unit
            is Resource.Loading -> Unit
        }
    }
}


