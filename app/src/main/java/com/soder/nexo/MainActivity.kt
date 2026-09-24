package com.soder.nexo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.soder.nexo.ui.NexoApp
import com.soder.nexo.ui.theme.NexoTheme
import com.soder.nexo.ui.viewmodel.FileManagerViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: FileManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexoTheme {
                NexoApp(viewModel = viewModel)
            }
        }
    }
}
