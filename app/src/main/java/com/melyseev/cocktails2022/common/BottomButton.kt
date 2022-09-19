package com.melyseev.cocktails2022.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(textButton: String, onClick: ()->Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp, top = 30.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = {
            onClick()
        }) {
            Text(text = textButton)
        }
    }
}