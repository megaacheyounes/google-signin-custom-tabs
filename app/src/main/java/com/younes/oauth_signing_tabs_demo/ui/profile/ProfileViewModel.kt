package com.younes.oauth_signing_tabs_demo.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.younes.oauth_signing_tabs_demo.App
import com.younes.oauth_signing_tabs_demo.data.MyDataStore
import com.younes.oauth_signing_tabs_demo.data.repo.ProfileRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

val TAG = "mylog"

class ProfileViewModel(

) : ViewModel() {

    val repo = ProfileRepo()
    private val dataStore = MyDataStore(App.instance)

    var state by mutableStateOf(ProfileUiState())
        private set

    private val _navigationEvent = MutableSharedFlow<ProfileNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        getAccessTokenThenFetchUseProfile()
    }

    private fun getAccessTokenThenFetchUseProfile() = viewModelScope.launch {
        state = state.copy(
            error = null,
            isLoading = true
        )
        delay(500)
        dataStore.googleAccessToken.collect { accessToken ->
            if (accessToken.isNullOrBlank()) {
                _navigationEvent.emit(ProfileNavigationEvent.ToLoginScreen)
            } else {
                fetchUserProfile(accessToken)
            }
        }
    }

    fun onEvent(event: ProfileUiEvent) = viewModelScope.launch {
        when (event) {
            ProfileUiEvent.OnLogout -> logout()
        }
    }

    private suspend fun fetchUserProfile(accessToken: String) {
        state = state.copy(
            error = null,
            isLoading = true
        )
        repo.getUserProfile(accessToken).collect { result ->
            result.onSuccess {
                state = state.copy(
                    userProfile = it,
                    isLoading = false
                )
            }.onFailure {
                //invalid token, clear datastore
                dataStore.clear()
                state = state.copy(
                    isLoading = false,
                    error = it.message
                )
            }
        }
    }

    private suspend fun logout() {
        dataStore.clear()
        _navigationEvent.emit(ProfileNavigationEvent.ToLoginScreen)
    }

}