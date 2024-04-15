@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)

package com.wls.poke.ui.favorite

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wls.poke.R
import com.wls.poke.entity.CollectArticleEntity
import com.wls.poke.ui.component.ListData
import com.wls.poke.ui.component.RefreshList
import com.wls.poke.ui.favorite.viewmodel.FavoriteViewModel

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
    articleDetail: (article: CollectArticleEntity.Data) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    FavoriteScreen(
        modifier = modifier,
        articleList = viewModel.listData,
        articles = viewModel::articles,
        cancelCollect = viewModel::cancelCollect,
        articleDetail = articleDetail
    )
}

@Composable
fun FavoriteScreen(
    articleList: ListData<CollectArticleEntity.Data>,
    modifier: Modifier = Modifier,
    articles: (refresh: Boolean) -> Unit,
    cancelCollect: (article: CollectArticleEntity.Data) -> Unit,
    articleDetail: (article: CollectArticleEntity.Data) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val state = rememberLazyListState()
    RefreshList(
        listData = articleList,
        pullToRefreshState = pullToRefreshState,
        lazyListState = state,
        refresh = { articles(true) },
        loadMore = { articles(false) })
    { index, data ->
        FavoriteArticleItem(
            article = data,
            cancelCollect = cancelCollect,
            articleDetail = articleDetail
        )
    }

}

@Composable
fun FavoriteArticleItem(
    article: CollectArticleEntity.Data,
    cancelCollect: (article: CollectArticleEntity.Data) -> Unit,
    articleDetail: (article: CollectArticleEntity.Data) -> Unit,
) {
    val state = remember {
        AnchoredDraggableState(
            initialValue = 0,
            anchors = DraggableAnchors {
                for (i in 0 until 3) {
//                    每个页面锚点需要向左滑动的宽度。
                    i at -i * 300f
                }
            },
            positionalThreshold = { it * 0.5f },
            velocityThreshold = { 125.dp.value },
            animationSpec = tween(),
            confirmValueChange = {
                if (it == 2) {
                    cancelCollect(article)
                }
                false
            }
        )
    }
    Card(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .anchoredDraggable(state = state, orientation = Orientation.Horizontal)
            .graphicsLayer {
                translationX = state.requireOffset()
            }
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
                    text = article.chapterName,
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
                        text = if (article.author.isNotEmpty()) {
                            stringResource(
                                R.string.author,
                                article.author
                            )
                        } else {
                            ""
                        },
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

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreFavorite() {

//    FavoriteScreen(
//
//    )
}