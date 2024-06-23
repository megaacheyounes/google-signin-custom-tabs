package com.younes.oauth_signing_tabs_demo.data.entities

data class GoogleProfile(
    val id: String,
    val email: String,
    val verified_email: Boolean,
    val name: String,
    val given_name: String,
    val family_name: String,
    val picture: String,
    val locale: String
)