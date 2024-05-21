package com.yogaveda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.yogaveda.navigation.SetupNavGraph
import com.yogaveda.screens.home.HomeViewModel
import com.yogaveda.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val viewModel = remember { HomeViewModel() }
                SetupNavGraph(navController, viewModel)
            }
        }
    }
}

