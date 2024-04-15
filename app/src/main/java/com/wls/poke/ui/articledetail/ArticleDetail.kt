@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.wls.poke.ui.articledetail

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.wls.poke.ui.articledetail.viewmodel.ArticleDetailViewModel


@Composable
fun ArticleDetailRoute(
    id: Int,
    link: String,
    title: String,
    collect: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    val collectMap = remember {
        viewModel.collectMap
    }
    if (collect) {
        collectMap[link] = true
    }
    ArticleDetailScreen(
        id = id,
        link = link,
        title = title,
        onBackClick = onBackClick,
        state = rememberWebViewState(url = link),
        collectArticle = viewModel::collectArticle,
        collectMap = collectMap,
        modifier = modifier,

        )
}

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun ArticleDetailScreen(
    id: Int,
    title: String,
    link: String,
    collectMap: SnapshotStateMap<String, Boolean>,
    onBackClick: () -> Unit,
    state: WebViewState,
    collectArticle: (id: Int,link:String,collect:Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var pageTitle by remember { mutableStateOf(title) }
    var progress by remember { mutableFloatStateOf(0.1f) }
    val collect by remember {
        derivedStateOf { link in collectMap }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = pageTitle,
                    style = MaterialTheme.typography.titleMedium
                )
            }, actions = {
                IconButton(modifier=Modifier.size(if (link==state.lastLoadedUrl) Dp.Unspecified else 0.dp),onClick = {
                    if (link == state.lastLoadedUrl) {
                        collectArticle(id,link,collect)
                    } else {
                        //收藏网址

                    }
                }) {
                    Icon(
                        imageVector = if (collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (collect) Color.Red.copy(0.7f) else Color.Unspecified
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            WebView(
                state = state,
                modifier = Modifier
                    .fillMaxSize(),
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
            if (state.isLoading){
                CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreArticleDetail() {
//    ArticleDetailScreen(
//        id = 1,
//        link = "",
//        title = "title",
//        collect = true,
//        onBackClick = {},
//        state = rememberWebViewState(url = ""),
//    )
}