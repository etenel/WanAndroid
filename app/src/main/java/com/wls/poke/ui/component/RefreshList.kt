@file:OptIn(ExperimentalMaterial3Api::class)

package com.wls.poke.ui.component

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T : Any> RefreshList(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<T>,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(10.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    refresh: () -> Unit = { },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
    headContent: @Composable () -> Unit = {},
    footContent: @Composable () -> Unit = {},
    itemContent: @Composable (T) -> Unit,

    ) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize(),
            state = state,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled
        ) {
            item {
                headContent.invoke()
            }
            items(count = pagingItems.itemCount, key = pagingItems.itemKey()) { index ->
                pagingItems[index].let {
                    if (it != null) {
                        itemContent(it)
                    }
                }
            }
            item {
                footContent.invoke()
            }
            pagingItems.apply {
//                LogUtils.e(loadState.refresh, loadState.append, loadState.prepend)
//                初次加载或者刷新数据
                val loading = when {
                    loadState.prepend is LoadState.Loading -> loadState.prepend as LoadState.Loading
                    loadState.refresh is LoadState.Loading -> loadState.refresh as LoadState.Loading
                    else -> null
                }
                val loadError = when {
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }

                    else -> {
                        null
                    }
                }
//                      加载更多时出现
                val loadingMore = when {

                    loadState.prepend is LoadState.Loading -> loadState.prepend as LoadState.Loading
                    loadState.append is LoadState.Loading -> loadState.append as LoadState.Loading
                    else -> null
                }

                val loadMoreError = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    else -> null
                }
//            刷新初始化数据
                if (loading != null) {
                    repeat((0..10).count()) {
                        item {
                            LoadingItem()
                        }
                    }
                } else {
                    coroutineScope.launch {
                        delay(200)
                        pullToRefreshState.endRefresh()
                    }
                }
//            初始化失败
                if (loadError != null) {
                    item {
                        ErrorContent(
                            retry = {
                                pagingItems.refresh()
                                refresh()
                            }
                        )
                    }
                }
//            加载更多中
                if (loadingMore != null) {
                    item {
                        LoadingMore()
                    }
                }
//            加载更多失败
                if (loadMoreError != null) {
                    item {
                        ErrorItem(
                            modifier = Modifier.fillMaxWidth(),
                            retry = { retry() },
                            message = loadMoreError.error.message.orEmpty()
                        )
                    }
                }

            }
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .pullRefreshIndicatorTransform(pullToRefreshState)
        )
    }
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            pagingItems.refresh()
            refresh()
        }
    }

}

