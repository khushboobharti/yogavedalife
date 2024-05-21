package com.yogaveda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yogaveda.screens.home.HomeScreen
import com.yogaveda.screens.home.HomeViewModel

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(posts = viewModel.allPosts.value)
            /*posts = RequestState.Success<List<Post>>(data = listOf()),
            searchedPosts = RequestState.Success<List<Post>>(data = listOf()),
            query = "",
            searchBarOpened = false,
            active = true,
            onActiveChange = {},
            onQueryChange = {},
            onCategorySelect = {},
            onSearchBarChange = {},
            onSearch = {},
            onPostClick = {}*/
        }
        composable(route = Screen.Category.route) { TODO() }
        composable(route = Screen.Details.route) { TODO() }
    }
}