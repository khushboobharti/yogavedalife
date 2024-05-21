package com.yogaveda.screens.home

import MONGODB_APP_ID
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogaveda.model.Post
import com.yogaveda.model.RequestState
import com.yogaveda.repository.MongoSync
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _allPosts: MutableState<RequestState<List<Post>>> =
        mutableStateOf(RequestState.Idle)
    val allPosts: State<RequestState<List<Post>>> = _allPosts
    private val _searchedPosts: MutableState<RequestState<List<Post>>> =
        mutableStateOf(RequestState.Idle)
    val searchedPosts: State<RequestState<List<Post>>> = _searchedPosts

    init {
        Log.d("HomeViewModel", "init called")
        val realmApp = App.create(MONGODB_APP_ID)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("HomeViewModel", "Before")
            realmApp.login(Credentials.anonymous())
            Log.d("HomeViewModel", "After")
            fetchAllPosts()
        }
    }

    fun initializeRealm() {

    }

    private suspend fun fetchAllPosts() {
        withContext(Dispatchers.Main) {
            _allPosts.value = RequestState.Loading
        }
        MongoSync.readAllPosts().collectLatest {
            _allPosts.value = it
        }
    }

    fun searchPostsByTitle(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _searchedPosts.value = RequestState.Loading
            }
            /*MongoSync.searchPostsByTitle(query = query).collectLatest {
                _searchedPosts.value = it
            }*/
        }
    }

    fun resetSearchedPosts() {
        _searchedPosts.value = RequestState.Idle
    }
}