package com.wls.poke.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun Favorite() {
    val ints by remember {
        mutableIntStateOf(0)
    }
    Box{
      
        Text(text = "$ints",color= Color.Magenta)

    }
}