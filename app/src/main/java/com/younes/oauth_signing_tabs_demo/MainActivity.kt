package com.younes.oauth_signing_tabs_demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.younes.oauth_signing_tabs_demo.data.MyDataStore
import com.younes.oauth_signing_tabs_demo.ui.Routes
import com.younes.oauth_signing_tabs_demo.ui.profile.ProfileScreen
import com.younes.oauth_signing_tabs_demo.ui.signIn.GoogleSignInScreen
import com.younes.oauth_signing_tabs_demo.ui.theme.GoogleOauthSigninTheme
import kotlinx.coroutines.launch

val TAG = "mylog"

class MainActivity : ComponentActivity() {

    val datastore = MyDataStore(App.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSignInCodeFromIntent(intent)

        setContent {
            GoogleOauthSigninTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.SignInScreen) {
                        composable<Routes.SignInScreen> {
                            GoogleSignInScreen(navController)
                        }
                        composable<Routes.ProfileScreen> {
                            ProfileScreen(
                                navController
                            )
                        }
                    }
                }
            }
        }
    }

    // If your app is in opened in background and you attempt to open an url, it
    // will call this function instead of onCreate()
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        
        getSignInCodeFromIntent(intent)
    }

    private fun getSignInCodeFromIntent(intent: Intent) {
        val uri = intent.data
        
        if (uri != null && uri.toString().contains("oauth2callback")) {
            val code = uri.getQueryParameter("code")
            if (code != null) {

                lifecycleScope.launch {
                    //
                    /**
                     * [GoogleSignInViewModel] will be notified with this code
                     */
                    datastore.saveGoogleSignInCode(code)
                }

            }
        }
    }


}

