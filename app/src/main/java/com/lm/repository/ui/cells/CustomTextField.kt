package com.lm.repository.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    code: String,
    res: (String) -> Unit
) {
    var text by remember { mutableStateOf(code)}
    BasicTextField(modifier = modifier
        .background(
            MaterialTheme.colors.surface,
            MaterialTheme.shapes.small,
        )
        .width(LocalConfiguration.current.screenWidthDp.dp - 100.dp),
        value = text,
        onValueChange = {
            text = it
            res(text)
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}



