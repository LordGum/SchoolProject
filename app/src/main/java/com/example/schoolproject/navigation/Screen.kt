package com.example.schoolproject.navigation

sealed class Screen(
    val route: String
) {
    data object EnterScreen: Screen(ROUTE_ENTER)
    data object MainScreen: Screen(ROUTE_MAIN)
    data object DetailScreen: Screen(ROUTE_DETAIL) {
        private const val ROUTE_FOR_ARGS = "route_detail"
        fun getArgs(id: String): String {
            return "$ROUTE_FOR_ARGS/${id}"
        }
    }

    companion object {
        const val ID = "id"

        private const val ROUTE_ENTER = "route_enter"
        private const val ROUTE_MAIN = "route_main"
        private const val ROUTE_DETAIL = "route_detail/{$ID}"
    }
}
