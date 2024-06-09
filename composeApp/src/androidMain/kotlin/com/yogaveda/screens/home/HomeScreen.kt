package com.yogaveda.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.yogaveda.components.NavigationDrawer
import com.yogaveda.components.PostCardsView
import com.yogaveda.model.Category
import com.yogaveda.model.Post
import com.yogaveda.model.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    posts: RequestState<List<Post>>,
    searchedPosts: RequestState<List<Post>>,
    query: String,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onCategorySelect: (Category) -> Unit,
    onSearchBarChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    searchBarOpened: Boolean,
    onPostClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var showLogin by remember { mutableStateOf(false) }
    val auth = remember { Firebase.auth }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(
        drawerState = drawerState,
        onCategorySelect = {
            scope.launch {
                drawerState.close()
            }
            onCategorySelect(it)
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Blog")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Drawer Icon"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                onSearchBarChange(true)
                                onActiveChange(true)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(
                            onClick = {
                                      showLogin = !showLogin
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
                if (searchBarOpened) {
                    SearchBar(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = onSearch,
                        active = active,
                        onActiveChange = onActiveChange,
                        placeholder = { Text(text = "Search here...") },
                        leadingIcon = {
                            IconButton(onClick = { onSearchBarChange(false) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back Arrow Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    ) {
                        PostCardsView(
                            posts = searchedPosts,
                            topMargin = 12.dp,
                            onPostClick = onPostClick
                        )
                    }
                }
            }
        ) {
            if(showLogin) {
                LoginView(auth)
            } else {
                PostCardsView(
                    posts = posts,
                    topMargin = it.calculateTopPadding(),
                    hideMessage = true,
                    onPostClick = onPostClick
                )
            }
        }
    }
}

@Composable
fun LoginView(auth: FirebaseAuth) {
    val scope = rememberCoroutineScope()
    var firebaseUser: FirebaseUser? by remember { mutableStateOf(null) }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (firebaseUser == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    placeholder = { Text(text = "Email Address") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = userPassword,
                    onValueChange = { userPassword = it },
                    placeholder = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                auth.createUserWithEmailAndPassword(
                                    email = userEmail,
                                    password = userPassword

                                )
                                firebaseUser = auth.currentUser
                            } catch (e: Exception) {
                                auth.signInWithEmailAndPassword(
                                    email = userEmail,
                                    password = userPassword
                                )
                                firebaseUser = auth.currentUser
                            }
                        }
                    }
                ) {
                    Text(text = "Sign IN")
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = firebaseUser?.uid ?: "Unknown ID")
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch {
                            auth.signOut()
                            firebaseUser = auth.currentUser
                        }
                    }
                ) {
                    Text(text = "Sign Out")
                }
            }
        }
    }
}