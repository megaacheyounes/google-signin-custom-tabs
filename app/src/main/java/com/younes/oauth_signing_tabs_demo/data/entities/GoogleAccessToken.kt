package com.younes.oauth_signing_tabs_demo.data.entities

data class GoogleTokenResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val refresh_token: String?,
    val scope: String
)

