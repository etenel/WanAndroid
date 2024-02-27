@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)

package com.wls.poke.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wls.poke.entity.BannerEntity
import kotlinx.coroutines.launch


@Composable
fun HomeRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBannerClick:(String)->Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        onShowSnackbar = onShowSnackbar,
        banner = viewModel.banner,
        onBannerClick = onBannerClick
    )

}

@Composable
fun HomeScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    banner: MutableState<List<BannerEntity>>,
    onBannerClick:(String)->Unit
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
            .fillMaxSize().background(MaterialTheme.colorScheme.errorContainer),

    ) {
        Banner(banner.value, onShowSnackbar, onBannerClick)
    }

}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun Banner(banner: List<BannerEntity>, onShowSnackbar: suspend (String, String?) -> Boolean,
           onBannerClick:(String)->Unit) {
    if (banner.isEmpty()) return
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        val pagerSwipeState = rememberPagerSwipeState()
        LinearPager(
            pagerSwipeState = pagerSwipeState,
            widthPx = LocalContext.current.resources.displayMetrics.widthPixels.toFloat(),
            data = banner
        ) { item, index ->
            BannerItem(banner = item, index,onShowSnackbar, onBannerClick)
        }
        ScrollBallIndicator(
            swipeState = pagerSwipeState,
            unSelectColor = MaterialTheme.colorScheme.tertiary,
            selectColor = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )
    }


}

@Composable
fun BannerItem(banner: BannerEntity, index:Int,onShowSnackbar: suspend (String, String?) -> Boolean,
               onBannerClick:(String)->Unit) {
    val coroutineScope = rememberCoroutineScope()
    GlideImage(
        model = banner.imagePath, contentDescription = banner.desc,
        contentScale = ContentScale.FillBounds, modifier = Modifier.clickable {
            coroutineScope.launch {
                onShowSnackbar(banner.title, null)
            }
            onBannerClick("main${index+1}")

        })
}
