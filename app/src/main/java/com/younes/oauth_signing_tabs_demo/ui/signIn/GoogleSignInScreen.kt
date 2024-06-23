package com.younes.oauth_signing_tabs_demo.ui.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.younes.oauth_signing_tabs_demo.R
import com.younes.oauth_signing_tabs_demo.ui.Routes
import com.younes.oauth_signing_tabs_demo.ui.components.Screen
import com.younes.oauth_signing_tabs_demo.ui.theme.GoogleOauthSigninTheme

@Composable
fun GoogleSignInScreen(
    navController: NavController,
    viewModel: GoogleSignInViewModel = viewModel()
) {
    val state = viewModel.state

    val navigationState = viewModel.navigationEvent.collectAsState(initial = null).value

    LaunchedEffect(navigationState) {
        navigationState?.let {
            when (it) {
                is GoogleSignInNavigationEvent.ToProfileScreen -> {
                    navController.navigate(Routes.ProfileScreen) {
                        //to prevent navigating back to sign in screen
                        popUpTo(Routes.SignInScreen)
                    }
                }
            }
        }
    }

    GoogleSignInContent(
        state = state
    ) {
        viewModel.onEvent(GoogleSignInUiEvent.OnLogin)
    }
}

@Composable
fun GoogleSignInContent(
    state: GoogleSignInUiState,
    onLogin: () -> Unit = {},
) {
    Screen(
        title = "Google OAuth Sign In Demo ",
        isLoading = state.isLoading,
        error = state.error
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "user",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sign in to continue",
                style = MaterialTheme.typography.titleMedium
            )


            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = onLogin,
                enabled = !state.isLoading
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Continue with Google",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


        }
    }
}


@Preview
@Composable
private fun Preview() {
    GoogleOauthSigninTheme {
        GoogleSignInContent(
            state = GoogleSignInUiState(
                isLoading = false
            )
        )
    }
}