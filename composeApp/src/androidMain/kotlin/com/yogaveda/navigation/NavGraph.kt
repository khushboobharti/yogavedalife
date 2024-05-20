package com.yogaveda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yogaveda.model.RequestState
import com.yogaveda.models.Post
import com.yogaveda.screens.home.HomeScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                posts = RequestState.Success<List<Post>>(data = listOf()),
                searchedPosts = RequestState.Success<List<Post>>(data = listOf()),
                query = "",
                searchBarOpened = false,
                active = true,
                onActiveChange = {},
                onQueryChange = {},
                onCategorySelect = {},
                onSearchBarChange = {},
                onSearch = {},
                onPostClick = {}
            )
        }
        composable(route = Screen.Category.route) {}
        composable(route = Screen.Details.route) {}
    }
}