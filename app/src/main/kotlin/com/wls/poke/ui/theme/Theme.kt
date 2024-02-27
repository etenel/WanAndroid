package com.wls.poke.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark
)
private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
//@Stable
//class WanAndroidColors(
//    bottomBar: Color,
//    statusBar: Color,
//) {
//    var bottomBar: Color by mutableStateOf(bottomBar)
//    var statusBar: Color by mutableStateOf(statusBar)
//
//
//}
//private val WanLightColorsScheme = WanAndroidColors(
//    bottomBar = primaryDark,
//    statusBar = onPrimaryContainerDark
//)
//private val WanDarkColorsScheme = WanAndroidColors(
//    bottomBar = tertiaryContainerLight,
//    statusBar = onTertiaryContainerDark
//)
//private val WanGrayColorsScheme = WanAndroidColors(
//    bottomBar = secondaryDark,
//    statusBar = onSecondaryContainerDark
//)
//private val LocalWanColorScheme = compositionLocalOf {
//    WanLightColorsScheme
//}
//object WanAndroidTheme {
//    val colors: WanAndroidColors
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalWanColorScheme.current
//
//    enum class Theme {
//        Light,
//        Dark,
//        Gray,
//    }
//}
//


@Composable
fun WanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,

    ) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    //自定义主题颜色
//    val theme: WanAndroidTheme.Theme = if (darkTheme) {
//        WanAndroidTheme.Theme.Dark
//    } else {
//        WanAndroidTheme.Theme.Light
//    }
//    val targetColors = when (theme) {
//        WanAndroidTheme.Theme.Light -> {
//            WanLightColorsScheme
//        }
//
//        WanAndroidTheme.Theme.Dark -> {
//            WanDarkColorsScheme
//        }
//
//        WanAndroidTheme.Theme.Gray -> {
//            WanGrayColorsScheme
//        }
//    }
//    CompositionLocalProvider(value = LocalWanColorScheme provides targetColors) {
//        MaterialTheme(
//            typography = Typography,
//            content = content
//        )
//    }
    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}