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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
import com.wls.poke.base.MyApp
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.ui.component.Banner
import com.wls.poke.ui.component.Indicator
import com.wls.poke.ui.component.RefreshPagingList
import com.wls.poke.ui.component.rememberIndicatorState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
fun HomeRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit,
    articleDetail: (article: HomeArticleEntity.Data) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val banner by viewModel.banner.collectAsState()
    val items = viewModel.homeArticles.collectAsLazyPagingItems()
    val collectMap: SnapshotStateMap<Int, Boolean> = remember {
       MyApp.appViewModel.articleCollectList
    }
    HomeScreen(
        onShowSnackbar = onShowSnackbar,
        banner = banner,
        pagingItems = items,
        refresh = viewModel::refresh,
        onBannerClick = onBannerClick,
        articleDetail = articleDetail,
        collectClick = viewModel::collectArticle,
        collectMap = collectMap,
        modifier = modifier,
    )
}

@Composable
internal fun HomeScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    banner: List<BannerEntity>,
    collectMap: SnapshotStateMap<Int, Boolean>,
    pagingItems: LazyPagingItems<HomeArticleEntity.Data>,
    refresh: () -> Unit,
    onBannerClick: (String) -> Unit,
    collectClick: (id: Int, collect: Boolean) -> Unit,
    articleDetail: (article: HomeArticleEntity.Data) -> Unit,
    modifier: Modifier = Modifier,
) {
    RefreshPagingList(
        modifier = modifier,
        pagingItems = pagingItems,
        refresh = {
            refresh()
        },
        headContent = {
            Banners(banner, onShowSnackbar, onBannerClick)

        }) { index, it ->
        if (it.collect) {
            collectMap[it.id] = true
        }
        ArticlesItem(
            collectMap = collectMap,
            article = it,
            collectClick = collectClick,
            articleDetail = articleDetail,
        )
    }

}


//文章列表item
@Composable
fun ArticlesItem(
    collectMap: Map<Int, Boolean>,
    article: HomeArticleEntity.Data,
    collectClick: (id: Int, collected: Boolean) -> Unit,
    articleDetail: (article: HomeArticleEntity.Data) -> Unit,
) {
    val articleID = article.id
    val collect by remember {
        derivedStateOf {
            articleID in collectMap.keys
        }
    }
    Card(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clickable {
                articleDetail(article)
            }, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.superChapterName+"·"+article.chapterName,
                    style = MaterialTheme.typography.labelSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
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
                        text = if (article.niceDate.isNotEmpty()) stringResource(
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
                collectClick(articleID, collect)
            }) {

                Icon(
                    imageVector = if (collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (collect) Color.Red.copy(alpha = 0.7f) else Color.LightGray
                )
            }
        }

    }
}

//轮播图
@Composable
fun Banners(
    banner: List<BannerEntity>, onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick: (String) -> Unit,
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
    onBannerClick: (String) -> Unit,
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

@Preview
@Composable
fun PreArticleItem() {
    ArticlesItem(collectMap = remember {
        mutableStateMapOf(2 to true)
    }, article = HomeArticleEntity.Data(
        id=2,
        title = "WanAndroid",
        collect = true,
        author = "android",
        niceDate = "2024-03-04",
        superChapterName = "公众号",
        chapterName = "weixin"
    ), collectClick = { _, _ -> }, articleDetail = {})
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreHome() {
    val data = PagingData.from<HomeArticleEntity.Data>(
        listOf(
            HomeArticleEntity.Data(
                id = 1,
                title = "WanAndroid",
                collect = false,
                author = "android",
                niceDate = "2024-03-04",
                superChapterName = "公众号",
                chapterName = "weixin"
            ),
            HomeArticleEntity.Data(
                id = 2,
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
        refresh = {},
        onBannerClick = {},
        collectClick = {_,_->},
        articleDetail = {},
        collectMap = remember {
            mutableStateMapOf(2 to true)
        }

    )
}


