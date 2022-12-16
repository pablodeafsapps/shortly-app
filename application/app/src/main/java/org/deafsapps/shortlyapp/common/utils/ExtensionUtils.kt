package org.deafsapps.shortlyapp.common.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import org.deafsapps.shortlyapp.common.domain.model.Url

fun Url.isValid(): Boolean = value.isNotEmpty()

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
