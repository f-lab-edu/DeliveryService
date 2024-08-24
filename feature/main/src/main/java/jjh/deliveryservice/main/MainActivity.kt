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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import jjh.deliveryservice.common.hasNotificationAccess
import jjh.deliveryservice.common.openNotificationPermissions
import jjh.deliveryservice.main.screens.DeliveryNavHost
import jjh.deliveryservice.resource.DeliveryServiceTheme
import jjh.deliveryservice.resource.R
import jjh.deliveryservice.ui.TwoButtonDialog
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val viewModel: MainViewModel by viewModels()

  @SuppressLint("CoroutineCreationDuringComposition")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.getCompanyList()
    viewModel.update()
    getTime(viewModel::saveRefreshTime)
    enableEdgeToEdge()
    setContent {
      RequestPermission()
      DeliveryServiceTheme {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
          color = Color.Transparent,
          darkIcons = true
        )

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

  @Composable
  private fun RequestPermission() {
    val context = LocalContext.current
    val notificationAccess = remember { hasNotificationAccess(context) }

    if (!notificationAccess) {
      var isShowing by remember { mutableStateOf(true) }
      if (isShowing) {
        TwoButtonDialog(
          message = stringResource(id = R.string.notification_permission_info_message),
          leftButtonText = stringResource(id = R.string.close),
          onLeftClick = { isShowing = false },
          rightButtonText = stringResource(id = R.string.confirm),
          onRightButtonClick = {
            openNotificationPermissions(this)
            isShowing = false
          },
        )
      }
    }
  }

  private fun getTime(listener: (Long) -> Unit) {

    val reference: DatabaseReference = Firebase.database.reference
    reference.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(snapshot: DataSnapshot) {
        val hashMap = (snapshot.value as? HashMap<*, *>?)
        listener(hashMap?.get("repeatTime") as? Long ?: 3600000L)
      }

      override fun onCancelled(error: DatabaseError) {
        listener(TimeUnit.HOURS.toMillis(1))
      }
    })
  }
}