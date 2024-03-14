package com.wls.poke.ui.person

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wls.poke.R

@Composable
fun Person() {
    val resource = stringResource(id = R.string.person)
    Box{
        Text(text = resource,color= Color.Magenta)

    }
}