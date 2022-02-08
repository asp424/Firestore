package com.lm.repository.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel, sharedPreferences: SharedPrefProvider) {
    var text by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(true){
        mainViewModel.apply {
            with(FirePath("aaa", "add")) {
                putDataToDocument(this, hashMapOf(
                    Calendar.getInstance().time.time.toString() to "Hi")
                ) {
                    coroutine.launch {
                        allDocumentsInCollection(this@with).collect{
                            when (it) {
                                is Resource.Success -> {
                                    it.data?.documents?.forEach { doc ->
                                        doc.data?.keys?.forEach { t ->
                                            text = "${text}\n ${doc.id} [ $t: ${doc[t]} ]"
                                        }
                                    }
                                }

                                is Resource.Failure -> Unit
                                is Resource.Loading -> Unit
                            }
                        }
                    }
                }
            }
        }
    }

    ColumnFMS {
        Text(text = text)
        Text(text = sharedPreferences.read())
    }
}