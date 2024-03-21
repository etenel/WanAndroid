@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.articledetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.wls.poke.R
import com.wls.poke.ui.articledetail.viewmodel.ArticleDetailViewModel

@Composable
fun ArticleDetailRoute(
    link: String,
    title: String,
    collect: Boolean,
    onBackClick:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {

    ArticleDetailScreen(
        title = title,
        collect = collect,
        onBackClick=onBackClick,
        state = rememberWebViewState(url = link),
        modifier = modifier,

        )
}

@Composable
fun ArticleDetailScreen(
    title: String,
    collect: Boolean,
    onBackClick:()->Unit,
    state: WebViewState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = if (collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (collect) Color.Red else Color.Unspecified
                    )
                }
            }, navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
            )

        },
    ) { it ->
        WebView(
            state = state, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreArticleDetail() {
    ArticleDetailScreen(
        title = "title",
        collect = true,
        onBackClick = {},
        state = rememberWebViewState(url = ""),
    )
}