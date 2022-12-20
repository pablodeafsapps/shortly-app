package org.deafsapps.shortlyapp.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ShortlyDestination(val icon: ImageVector, val route: String)

/**
 * Shortly app navigation destinations
 */
object Welcome : ShortlyDestination(icon = Icons.Filled.Home, route = "welcome")

object ShortenedUrlHistory : ShortlyDestination(icon = Icons.Filled.History, route = "history")
