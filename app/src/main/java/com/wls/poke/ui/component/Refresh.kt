@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.wls.poke.ui.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Modifier.pullRefreshIndicatorTransform(
    state: PullToRefreshState,
    scale: Boolean = false,
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "pullRefreshIndicatorTransform"
    properties["state"] = state
    properties["scale"] = scale
}) {
    Modifier
        // Essentially we only want to clip the at the top, so the indicator will not appear when
        // the position is 0. It is preferable to clip the indicator as opposed to the layout that
        // contains the indicator, as this would also end up clipping shadows drawn by items in a
        // list for example - so we leave the clipping to the scrolling container. We use MAX_VALUE
        // for the other dimensions to allow for more room for elevation / arbitrary indicators - we
        // only ever really want to clip at the top edge.
        .drawWithContent {
            clipRect(
                top = 0f,
                left = -Float.MAX_VALUE,
                right = Float.MAX_VALUE,
                bottom = Float.MAX_VALUE
            ) {
                this@drawWithContent.drawContent()
            }
        }
        .graphicsLayer {
            translationY = state.verticalOffset - size.height

            if (scale && !state.isRefreshing) {
                val scaleFraction = LinearOutSlowInEasing
                    .transform(state.verticalOffset / state.positionalThreshold)
                    .coerceIn(0f, 1f)
                scaleX = scaleFraction
                scaleY = scaleFraction
            }
        }
}


@Composable
fun LoadingMore(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator()
        Text(text = "加载中", style = MaterialTheme.typography.labelMedium,
            color = Color.LightGray)
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "请求出错啦", style = MaterialTheme.typography.displayLarge)
        Button(modifier = Modifier.padding(top = 20.dp),onClick = { retry() }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer,
            )) {
            Text(text = "点击重试")
        }

    }
}

@Composable
fun ErrorItem(retry: () -> Unit, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = { retry() },
            colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        ) {
            Text(text = "重试")
        }
    }
}

@Composable
fun NoMoreItem() {
    Box( modifier = Modifier
        .height(50.dp)
        .fillMaxWidth(),) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "没有更多了",
            textAlign = TextAlign.Center
        )
    }

}
@Composable
fun NoMoreContent() {
    Column( modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "没有更多了",
            textAlign = TextAlign.Center
        )
    }

}
@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
            CircularProgressIndicator(
                modifier=Modifier.align(Alignment.Center)
            )
    }
}

@Preview
@Composable
private fun PreLoadingItem(){
    LoadingItem()
}
@Preview
@Composable
private fun PreNoMoreItem(){
    NoMoreItem()
}
@Preview
@Composable
private fun PreErrorItem(){
    ErrorItem(retry = {})
}
@Preview
@Composable
private fun PreLoadingMore(){
    LoadingMore()
}
@Preview
@Composable
private fun PreErrorContent(){
    ErrorContent(retry = {})
}
@Preview
@Composable
private fun PreNoMoreContent(){
    NoMoreContent()
}

