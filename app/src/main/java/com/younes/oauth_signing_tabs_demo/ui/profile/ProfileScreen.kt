package com.younes.oauth_signing_tabs_demo.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.younes.oauth_signing_tabs_demo.domain.model.UserProfile
import com.younes.oauth_signing_tabs_demo.ui.Routes
import com.younes.oauth_signing_tabs_demo.ui.components.Screen
import com.younes.oauth_signing_tabs_demo.ui.theme.GoogleOauthSigninTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
) {
    val navigationState = viewModel.navigationEvent.collectAsState(initial = null).value

    LaunchedEffect(navigationState) {
        navigationState?.let {
            when (it) {
                ProfileNavigationEvent.ToLoginScreen -> {
                    navController.navigate(Routes.SignInScreen) {
                        //to prevent navigating back to profile screen
                        popUpTo(Routes.SignInScreen)
                    }
                }
            }
        }
    }

    ProfileScreenContent(
        state = viewModel.state
    ) {
        viewModel.onEvent(ProfileUiEvent.OnLogout)
    }
}

@Composable
fun ProfileScreenContent(
    state: ProfileUiState = ProfileUiState(),
    onLogoutClicked: () -> Unit = {}
) {
    Screen(
        title = "Your Google Profile",
        isLoading = state.isLoading,
        error = state.error
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            state.userProfile?.let { profile ->
                Image(
                    painter = rememberImagePainter(profile.avatarUrl),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .padding(16.dp)
                        .background(Color.Gray) ,
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(32.dp))
                ProfileItem(name = "Name", value = profile.name)
                Spacer(Modifier.height(16.dp))
                ProfileItem(name = "Email", value = profile.email)
                Spacer(Modifier.height(64.dp))
                Button(
                    onClick = onLogoutClicked
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "logout"
                        )
                        Text("Logout")
                    }

                }
            }
        }
    }
}

@Composable
fun ProfileItem(
    name: String, value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black
        )

        Spacer(Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
private fun Preview() {
    GoogleOauthSigninTheme {
        ProfileScreenContent(
            state = ProfileUiState(
                userProfile = UserProfile(
                    name = "John Doe",
                    email = "example@gmail.com",
                    avatarUrl = "https://i.pravatar.cc/300"
                )
            )
        )
    }
}