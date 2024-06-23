package com.younes.oauth_signing_tabs_demo.ui.signIn

data class GoogleSignInUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed class GoogleSignInUiEvent {
    data object OnLogin : GoogleSignInUiEvent()
}

sealed class GoogleSignInNavigationEvent {
    data class ToProfileScreen(val accessToken:String) : GoogleSignInNavigationEvent()

}
