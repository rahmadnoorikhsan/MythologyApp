package com.ikhsan.compose.mythology.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
    object DetailMythology : Screen("home/{mythologyId}") {
        fun createRoute(mythologyId: Int) = "home/$mythologyId"
    }
}
