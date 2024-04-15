@file:OptIn(ExperimentalMaterial3Api::class)

package com.wls.poke.ui.component

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.wls.poke.ui.component.ListState.Error
import com.wls.poke.ui.component.ListState.Loading
import com.wls.poke.ui.component.ListState.NotLoading

@Composable
fun <T : Any> RefreshList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(10.dp),
    reverseLayout: Boolean = false,
    listData: ListData<T>,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    refresh: () -> Unit = { },
    loadMore: () -> Unit = {},
    loadMoreRemainCountThreshold: Int = 3,
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
    headContent: @Composable () -> Unit = {},
    footContent: @Composable () -> Unit = {},
    itemContent: @Composable (index: Int, T) -> Unit,

    ) {
    //当前最后一个可见的 index
    var currentLastVisibleIndex by remember {
        mutableIntStateOf(-1)
    }
    var totalCount by remember {
        mutableIntStateOf(0)
    }

// 滑动结束后最后一个可见index
    var index = -1
    val lastVisibleIndex by remember {
        derivedStateOf {
            if (lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && !lazyListState.isScrollInProgress) {
                index = lazyListState.layoutInfo.visibleItemsInfo.last().index
            }
            index
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .collect {
                currentLastVisibleIndex = it.visibleItemsInfo.last().index
                totalCount = it.totalItemsCount
            }
    }
    val isLoadMore by remember {
        derivedStateOf {
            //填充满屏幕并且在限制范围内才加载更多
            val isLoadMore =
                currentLastVisibleIndex > 4 && totalCount - currentLastVisibleIndex - 1 in 0..loadMoreRemainCountThreshold
            //向下滑动
            if (lastVisibleIndex <= currentLastVisibleIndex) {
                isLoadMore
            } else {
                false
            }

        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled
        ) {
            item {
                headContent.invoke()
            }
            items(count = listData.data.size) { index ->
                itemContent(index, listData.data[index])
            }
            item {
                footContent.invoke()
            }

            //初始化页面
            if (listData.refresh is Loading && listData.data.isEmpty()) {
                if (!pullToRefreshState.isRefreshing) {
                    item {
                        LoadingItem()
                    }
                }

            }
            if (listData.refresh is ListState.Empty) {
                item {
                    NoMoreItem()
                }
            }
            if (listData.refresh is Error) {
                if (listData.data.isEmpty()) {
                    item {
                        ErrorContent(retry = { refresh() })
                    }
                }
            }

            if (listData.loadMore is Loading) {
                item {
                    LoadingMore()
                }
            }
            if (listData.loadMore is ListState.Empty) {
                item {
                    NoMoreItem()
                }
            }
            if (listData.loadMore is Error) {
                item {
                    ErrorItem(retry = {
                        if (!lazyListState.isScrollInProgress) {
                               more(isLoadMore) {
                                   loadMore()
                               }

                        }
                    })
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
    //下拉刷新
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            refresh()
        }
    }
    //结束刷新
    LaunchedEffect(listData.refresh) {
        if (listData.refresh !is Loading) {
            pullToRefreshState.endRefresh()
        }
    }
    //加载更多
    LaunchedEffect(isLoadMore) {
        if (!lazyListState.isScrollInProgress) {
            more(isLoadMore) {
                loadMore()
            }
        }

    }

}

@Synchronized
private fun more(isLoadMore: Boolean, loadMore: () -> Unit) {
    if (isLoadMore) {
        loadMore()
    }
}

class ListData<T>(
    var data: SnapshotStateList<T>,
) {
    private var _refresh by mutableStateOf<ListState>(NotLoading)
    private var _loadMore by mutableStateOf<ListState>(NotLoading)
    val refresh get() = _refresh
    val loadMore get() = _loadMore

    fun end(isRefresh: Boolean) {
        if (isRefresh) {
            if (_refresh !is ListState.Empty) {
                _refresh = NotLoading
            }
        } else {
            if (_loadMore !is ListState.Empty) {
                _loadMore = NotLoading
            }
        }
    }

    fun error(isRefresh: Boolean) {
        if (isRefresh) {
            _refresh = Error
        } else {
            _loadMore = Error
        }
    }

    fun start(isRefresh: Boolean) {
        if (isRefresh) {
            _refresh = Loading
        } else {
            _loadMore = Loading
        }
    }

    fun calculateData(isRefresh: Boolean, dataList: List<T>) {
        if (isRefresh) {
            data.removeAll(data)
            if (dataList.isEmpty()) {
                _refresh = ListState.Empty
            }
        } else {
            if (dataList.isEmpty()) {
                _loadMore = ListState.Empty
            }
        }
        data.addAll(dataList)


    }

}

/**
 * @property Loading 加载中
 * @property NotLoading 加载结束
 * @property Error 请求出错
 */
sealed interface ListState {
    data object Loading : ListState
    data object NotLoading : ListState
    data object Error : ListState
    data object Empty : ListState
}
