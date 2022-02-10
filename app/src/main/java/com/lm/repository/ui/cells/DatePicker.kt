package com.lm.repository.ui.cells

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.lm.repository.R
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun DatePicker(
    date: (Boolean, String) -> Unit
) {
    var res by rememberSaveable { mutableStateOf("") }
    ColumnFMS {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            border = BorderStroke(1.dp, Black),
            shape = RoundedCornerShape(20.dp), elevation = 20.dp
        ) {
            AndroidView(
                { android.widget.DatePicker(it, null, R.style.DatePickerSpinnerStyle) },
                update = { view ->
                    view.apply {
                        spinnersShown = true
                        calendarViewShown = false
                        Calendar.getInstance().apply {
                            init(
                                get(Calendar.YEAR),
                                get(Calendar.MONTH),
                                get(Calendar.DAY_OF_MONTH)
                            ) { _, i, i2, i3 -> res = "${if(i3.toString().length == 1) "0$i3" 
                            else i3}.${if(i2.toString().length == 1) "0${i2 + 1}" else i2 + 1}.${i}" }
                        }
                    }
                }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, end = 40.dp), horizontalArrangement = Arrangement.End
        ) {
            Icon(Icons.Outlined.Cancel,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 40.dp)
                    .clickable {
                        date(false, "")
                    })

            Icon(Icons.Outlined.Done, contentDescription = null, Modifier.clickable {
                if (res.isNotEmpty()) date(true, res)
            })
        }
    }
}

