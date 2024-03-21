package com.wls.poke.ui.articledetail.navigation
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.ui.articledetail.ArticleDetailRoute
import java.net.URLDecoder
import java.net.URLEncoder

const val article_detail_Route = "article_detail_route"
internal const val ARTICLE_LINK_ARG = "link"
internal const val ARTICLE_TITLE_ARG = "title"
internal const val ARTICLE_COLLECT_ARG = "collect"
private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()


fun NavController.navigateToArticleDetail(article: HomeArticleEntity.Data) {
    val encodedLink = URLEncoder.encode(article.link, URL_CHARACTER_ENCODING)
    val encodedTitle = URLEncoder.encode(article.title, URL_CHARACTER_ENCODING)
    val encodedCollected =article.collect.toString()
    this.navigate("$article_detail_Route/$encodedLink/$encodedTitle/$encodedCollected")
}

fun NavGraphBuilder.articleDetailScreen(onBackClick:()->Unit) {
    composable(
        route = "$article_detail_Route/{$ARTICLE_LINK_ARG}/{$ARTICLE_TITLE_ARG}/{$ARTICLE_COLLECT_ARG}",
        arguments = listOf(navArgument(ARTICLE_LINK_ARG) { type = NavType.StringType },
            navArgument(ARTICLE_TITLE_ARG){type= NavType.StringType}, navArgument(
                ARTICLE_COLLECT_ARG){type=NavType.BoolType}
        )
    ) { navBackStackEntry ->
        val link = navBackStackEntry.arguments?.getString(ARTICLE_LINK_ARG)
        val title = navBackStackEntry.arguments?.getString(ARTICLE_TITLE_ARG).orEmpty()
        val collect = navBackStackEntry.arguments?.getBoolean(ARTICLE_COLLECT_ARG) ?: false
        link?.let {
            ArticleDetailRoute(
                link = URLDecoder.decode(it, URL_CHARACTER_ENCODING),
                title=URLDecoder.decode(title, URL_CHARACTER_ENCODING),
                collect= collect,
                onBackClick=onBackClick,

            )
        }
    }
}