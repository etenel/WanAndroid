package com.wls.poke.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.http.ApiService

private const val HOME_ARTICLES_STARTING_PAGE_INDEX = 0

class ArticlesPagingSource(private val service: ApiService) :
    PagingSource<Int, HomeArticleEntity.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeArticleEntity.Data> {
        val currentKey = params.key ?: HOME_ARTICLES_STARTING_PAGE_INDEX
        return try {
            val response = service.homeArticles(currentKey)
            val data = response.data?.datas.orEmpty()
            LoadResult.Page(
                data = data,
                prevKey = if (currentKey == HOME_ARTICLES_STARTING_PAGE_INDEX) null else currentKey - 1,
                //服务器返回总页数，没有返回直接page+1
                nextKey = if (currentKey == response.data?.pageCount) null else currentKey.plus(1),
                itemsAfter = 10
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HomeArticleEntity.Data>): Int? {

        // 根据preKey和nextKey中找到离anchorPosition最近页面的键值,保证刷新时不多请求数据
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}