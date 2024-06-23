package com.younes.oauth_signing_tabs_demo.ui.profile

import com.younes.oauth_signing_tabs_demo.data.entities.GoogleProfile
import com.younes.oauth_signing_tabs_demo.domain.model.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userProfile: UserProfile? = null
)

sealed class ProfileUiEvent {
    data object OnLogout : ProfileUiEvent()
}

sealed class ProfileNavigationEvent {
    data object ToLoginScreen : ProfileNavigationEvent()

}
