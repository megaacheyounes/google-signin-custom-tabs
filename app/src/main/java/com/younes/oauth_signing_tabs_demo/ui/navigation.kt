package com.younes.oauth_signing_tabs_demo.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes{
    @Serializable
    data object SignInScreen : Routes() // pure data object without any primitive

    @Serializable
    data object ProfileScreen   : Routes() // data class with custom primitive
}