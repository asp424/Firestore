package com.lm.repository.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.di.MainDep.depends

@Composable
fun TextFieldProfile(init: String, field: String, callback: (String) -> Unit, onClick: () -> Unit) {
    depends.apply {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = field, color = Color.Gray, fontSize = 11.sp)
            Column(Modifier.padding(start = 10.dp)) {
                BasicTextField(
                    value = init,
                    onValueChange = { callback(it) },
                    textStyle = TextStyle(fontSize = 18.sp),
                    singleLine = true,
                    modifier = Modifier
                        .width(screenWidth)
                        .clickable { onClick() },
                    readOnly = true,
                    enabled = false
                )
                Spacer(
                    modifier = Modifier
                        .width(screenWidth)
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}