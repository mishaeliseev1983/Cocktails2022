package com.melyseev.cocktails2022.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.melyseev.cocktails2022.R

@Composable
fun CheckHearts(showCase: ()->Boolean) {
    if (showCase()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, top = 30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(id = R.string.favorite),
                tint = Color.Red,
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp),
            )
            Text(
                modifier = Modifier
                    .padding(5.dp),
                text = stringResource(R.string.check_hearts),
                style = MaterialTheme.typography.h6
            )
        }
    }
}