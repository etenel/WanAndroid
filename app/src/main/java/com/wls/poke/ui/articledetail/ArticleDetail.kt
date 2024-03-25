@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.articledetail

import android.annotation.SuppressLint
import android.webkit.WebView
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.wls.poke.ui.articledetail.viewmodel.ArticleDetailViewModel


@Composable
fun ArticleDetailRoute(
    link: String,
    title: String,
    collect: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    ArticleDetailScreen(
        title = title,
        collect = collect,
        onBackClick = onBackClick,
        state = rememberWebViewState(url = link),
        modifier = modifier,

        )
}

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun ArticleDetailScreen(
    title: String,
    collect: Boolean,
    onBackClick: () -> Unit,
    state: WebViewState,
    modifier: Modifier = Modifier,
) {
    var pageTitle by remember { mutableStateOf(title) }
    var progress by remember { mutableFloatStateOf(0.1f) }
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
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            chromeClient = remember {
                object : AccompanistWebChromeClient() {
                    override fun onReceivedTitle(view: WebView, title: String?) {
                        super.onReceivedTitle(view, title)
                        pageTitle = title ?: ""
                    }

                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        progress = newProgress / 100.0f
                    }
                }

            },
            onCreated = {
                it.settings.run {
                    javaScriptEnabled = true
                    //微信文章文字显示
                    domStorageEnabled = true
                    //加载http请求的图片
                    blockNetworkImage = false
                }
            },
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