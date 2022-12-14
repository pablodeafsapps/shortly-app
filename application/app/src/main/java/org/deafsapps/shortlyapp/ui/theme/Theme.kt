package org.deafsapps.shortlyapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ColorPalette = lightColors(
    primary = Cyan,
    primaryVariant = DarkViolet,
    secondary = Red
)

@Composable
fun ShortlyAppTheme(content: @Composable () -> Unit) {
    val colors = ColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
