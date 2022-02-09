package com.lm.repository.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.lm.repository.ui.cells.ColumnFMS



@Composable
fun MyProfile() {
    var name by rememberSaveable { mutableStateOf("") }
    var patr by rememberSaveable { mutableStateOf("") }
    var sName by rememberSaveable { mutableStateOf("") }
    var yo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var sex by rememberSaveable { mutableStateOf("") }
    //val navController = rememberNavController()
ColumnFMS(vertArr = Arrangement.Top) {
    Row {
        Text(text = "Имя: ")
        TextField(value = name, onValueChange = { name = it })
    }
    Row {
        Text(text = "Отчество: ")
        TextField(value = patr, onValueChange = { patr = it })
    }
    Row {
        Text(text = "Фамилия: ")
        TextField(value = sName, onValueChange = { sName = it })
    }
    Row {
        Text(text = "Дата рождения: ")
        TextField(value = yo, onValueChange = { yo = it })
    }
    Row {
        Text(text = "Email: ")
        TextField(value = email, onValueChange = { email = it })
    }
    Row {
        Text(text = "Пол: ")
        TextField(value = sex, onValueChange = { sex = it })
    }
}

}