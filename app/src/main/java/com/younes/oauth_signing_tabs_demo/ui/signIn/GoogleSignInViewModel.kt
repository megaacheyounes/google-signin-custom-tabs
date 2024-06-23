package com.younes.oauth_signing_tabs_demo.ui.signIn

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.younes.oauth_signing_tabs_demo.App
import com.younes.oauth_signing_tabs_demo.BuildConfig
import com.younes.oauth_signing_tabs_demo.data.MyDataStore
import com.younes.oauth_signing_tabs_demo.data.repo.TokenRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch



class GoogleSignInViewModel(
) : ViewModel() {

    private val dataStore = MyDataStore(App.instance)
    private val repo = TokenRepo()

    var state by mutableStateOf(GoogleSignInUiState())
        private set

    private val _navigationEvent = MutableSharedFlow<GoogleSignInNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkIfSessionValid()
        watchForGoogleSignInCode( )
    }

    /**
     * this method will receive oauth code when [MainActivity] is created from
     * Chrome Custom Tab redirect
     */
    private fun watchForGoogleSignInCode( ) = viewModelScope.launch {
        dataStore.googleSignInCodeFlow.collect { code ->
            if (!code.isNullOrBlank())
                exchangeCodeForTokenThenNavigate(code )
        }
    }

    /**
     * check if user already signed in
     * by fetching any previously saved accessToken
     */
    private fun checkIfSessionValid() = viewModelScope.launch {
        state = state.copy(
            isLoading = true
        )
        dataStore.googleAccessToken.collect { accessToken ->
            if (!accessToken.isNullOrEmpty()) {
                
                onAccessTokenReceived(accessToken)
            }
            state = state.copy(isLoading = false)
        }
    }

    fun onEvent(event: GoogleSignInUiEvent) = viewModelScope.launch {
        when (event) {
            GoogleSignInUiEvent.OnLogin -> {
                signInWithGoogle()
            }
        }
    }

    private suspend fun signInWithGoogle() {
        state = state.copy(
            error = null,
            isLoading = true
        )
        launchOAuthTab()
        delay(300)
        state = state.copy(
            error = null,
            isLoading = false
        )
    }


    /**
     * launch chrome tab to get user authorization
     * after authorization user will be redirected the app with a uri
     * which is handled in [MainActivity]
     */
    private fun launchOAuthTab() {
        val scopes = listOf("email", "profile")
        val authUri = Uri.Builder()
            .scheme("https")
            .authority("accounts.google.com")
            .path("o/oauth2/v2/auth")
            .appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .appendQueryParameter("redirect_uri", BuildConfig.REDIRECT_URI)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scopes.joinToString(" "))
            .build()

        val customTabsIntent = CustomTabsIntent.Builder()
            .build()
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabsIntent.launchUrl(App.instance, authUri)
    }

    private suspend fun exchangeCodeForTokenThenNavigate(
        code: String
    ) {
        state = state.copy(
            error = null,
            isLoading = true,
        )
        repo.getAccessToken(code).collect { result ->
            result.onSuccess { token ->
                dataStore.deleteCode()//no longer needed
                
                state = state.copy(
                    isLoading = false
                )
                onAccessTokenReceived(token.access_token)
            }.onFailure {
                state = state.copy(
                    isLoading = false,
                    error = it.message
                )
            }
        }
    }

    private suspend fun onAccessTokenReceived(accessToken: String) {
        dataStore.saveGoogleAccessToken(accessToken)
        _navigationEvent.emit(GoogleSignInNavigationEvent.ToProfileScreen(accessToken))
    }

}