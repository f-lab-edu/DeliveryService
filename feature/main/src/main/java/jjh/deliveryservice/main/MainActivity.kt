package jjh.deliveryservice.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jjh.deliveryservice.main.screens.DeliveryNavHost
import jjh.deliveryservice.resource.DeliveryServiceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel: MainViewModel by viewModels()

  @SuppressLint("CoroutineCreationDuringComposition")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      DeliveryServiceTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DeliveryNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
          ) // DeliveryNavHost
        } // Scaffold
      }
    }
  }
}