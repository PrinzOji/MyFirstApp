package com.Ojiem.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview
import com.Ojiem.myfirstapp.navigation.AppNavHost
import com.Ojiem.myfirstapp.ui.theme.MyFirstAppTheme
import com.Ojiem.myfirstapp.ui.theme.screens.homescreen.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavHost()
        }
    }
}