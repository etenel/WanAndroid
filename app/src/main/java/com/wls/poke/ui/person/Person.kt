@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.person

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wls.poke.ui.person.viewmodel.PersonViewModel

@Composable
fun PersonRoute(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel(),
) {
    PersonScreen(

    )
}

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrePerson() {

    PersonScreen(

    )
}