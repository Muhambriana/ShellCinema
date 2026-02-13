package com.mshell.shellcinema.ui.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mshell.shellcinema.ui.navigation.AppNavigation
import com.mshell.shellcinema.ui.ui.theme.ShellCinemaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShellCinemaTheme {
                AppNavigation()
            }
        }
    }
}

