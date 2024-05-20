package com.yogaveda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.yogaveda.navigation.SetupNavGraph
import com.yogaveda.ui.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
    YogaVedaApp()
}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun YogaVedaApp() {
    AppTheme {
        
    }
}