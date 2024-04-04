package com.yogaveda.navigation

import com.yogaveda.Constants.POST_ID_PARAM
import com.yogaveda.Constants.QUERY_PARAM
import com.yogaveda.Constants.UPDATED_PARAM

sealed class Screen(val route: String) {
    object AdminHome : Screen(route = "/admin/")
    object AdminLogin : Screen(route = "/admin/login")
    object AdminCreate: Screen(route = "/admin/create") {
        fun passPostId(id: String) = "$route?${POST_ID_PARAM}=$id"
    }
    object AdminMyPosts: Screen(route = "/admin/myposts") {
        fun searchByTitle(query: String): String {
            return "$route?${QUERY_PARAM}=$query"
        }

    }
    object AdminSuccess: Screen(route = "/admin/success") {
        fun postUpdate() = "$route?$UPDATED_PARAM=true"
    }
}
