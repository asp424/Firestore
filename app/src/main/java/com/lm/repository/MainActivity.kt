package com.lm.repository

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.lm.core.Resource
import com.lm.core.appComponent
import com.lm.repository.databinding.ActivityMainBinding
import com.lm.ui.viewmodels.MainViewModel
import com.lm.ui.viewmodels.viewmodelsfactory.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val vm: MainViewModel by viewModels { viewModelFactory }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        appComponent.inject(this)

        vm.apply { with("ddd") {
                setDataToDocument("Dick", hashMapOf("Color" to "Yellow")) {
                    lifecycleScope.launch {
                        getAllDocumentsInCollection().collect(::collectionCollector)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun collectionCollector(resource: Resource<QuerySnapshot?>) {
        when (resource) {
            is Resource.Success -> { resource.data?.documents?.forEach {
                    it.data?.keys?.forEach { t -> binding.tv.text =
                        "${binding.tv.text}\n ${it.id} [ $t: ${it[t]} ]"
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
            is Resource.Success -> { with(resource.data){
                this?.data?.keys?.forEach {
                    binding.tv.text = "${binding.tv.text}\n $id:: [ $it: ${get(it)} ]"
                    }
                }
            }
            is Resource.Failure -> Unit
            is Resource.Loading -> Unit
        }
    }
}

