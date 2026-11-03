package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.PreferencesManager
import com.example.ui.MainApp
import com.example.ui.theme.WorldHistoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefsManager = PreferencesManager(applicationContext)

        setContent {
            WorldHistoryTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainApp(prefsManager = prefsManager)
                }
            }
        }
    }
}
