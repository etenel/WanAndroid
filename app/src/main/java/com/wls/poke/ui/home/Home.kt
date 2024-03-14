@file:OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wls.poke.R
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.ui.component.Banner
import com.wls.poke.ui.component.Indicator
import com.wls.poke.ui.component.RefreshList
import com.wls.poke.ui.component.rememberIndicatorState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
fun HomeRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val banner by viewModel.banner.collectAsState()
    val items = viewModel.homeArticles.collectAsLazyPagingItems()
    HomeScreen(
        onShowSnackbar = onShowSnackbar,
        banner = banner,
        pagingItems = items,
        refresh = viewModel::refresh,
        onBannerClick = onBannerClick,
        collectArticle = viewModel::collectArticle,
        modifier = modifier.padding(paddingValues),
    )

}

@Composable
internal fun HomeScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    banner: List<BannerEntity>,
    pagingItems: LazyPagingItems<HomeArticleEntity.Data>,
    refresh: () -> Unit,
    onBannerClick: (String) -> Unit,
    collectArticle: (article: HomeArticleEntity.Data) -> Unit,
    modifier: Modifier = Modifier,
) {
    RefreshList(
        modifier = modifier,
        pagingItems = pagingItems,
        refresh = {
            refresh()
        },
        headContent = {
            Banners(banner, onShowSnackbar, onBannerClick)

        }) {
        ArticlesItem(article = it, collectArticle)
    }

}


//文章列表item
@Composable
fun ArticlesItem(
    article: HomeArticleEntity.Data,
    collectArticle: (article: HomeArticleEntity.Data) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clickable {

            }, colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = if (article.author.isEmpty()) stringResource(
                            R.string.share_user,
                            article.shareUser
                        ) else stringResource(R.string.author, article.author),
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (!article.niceDate.isEmpty()) stringResource(
                            R.string.date,
                            article.niceDate
                        ) else "",
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

            }
            IconButton(onClick = {
                collectArticle(article)
            }) {
                Icon(
                    imageVector = if (article.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (article.collect) Color.Red else Color.LightGray
                )
            }
        }

    }
}

//轮播图
@Composable
fun Banners(
    banner: List<BannerEntity>, onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit
) {
    if (banner.isEmpty()) return
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        val state = rememberIndicatorState()
        Banner(data = banner, indicatorState = state) { index, item, dragState, width ->
            BannerItem(banner = item, index, onShowSnackbar, onBannerClick)
        }

        Indicator(
            modifier = Modifier.align(
                Alignment.BottomCenter
            ),
            state = state,
        )
    }


}

@Composable
fun BannerItem(
    banner: BannerEntity, index: Int, onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    GlideImage(
        model = banner.imagePath, contentDescription = banner.desc,
        contentScale = ContentScale.FillBounds, modifier = Modifier.clickable {
            coroutineScope.launch {
                onShowSnackbar(banner.title, null)
            }
            onBannerClick(banner.url)

        })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreHome() {
    val viewModel: HomeViewModel = hiltViewModel()
    val data = PagingData.from<HomeArticleEntity.Data>(
        listOf(
            HomeArticleEntity.Data(
                title = "WanAndroid",
                collect = false,
                author = "android",
                niceDate = "2024-03-04"
            ),
            HomeArticleEntity.Data(
                title = "WanAndroid",
                collect = true,
                shareUser = "flutter",
                niceDate = "2024-03-12"
            )
        )
    )
    HomeScreen(
        onShowSnackbar = { message, action ->
            false
        },
        banner = listOf(
            BannerEntity(url = "https://t7.baidu.com/it/u=1956604245,3662848045&fm=193&f=GIF"),
            BannerEntity(url = "https://t7.baidu.com/it/u=1956604245,3662848045&fm=193&f=GIF")
        ),
        pagingItems = flowOf(data).collectAsLazyPagingItems(),
        refresh = { },
        onBannerClick = {},
        collectArticle = {

        },
    )
}


