package com.younes.oauth_signing_tabs_demo.data.entities

import com.google.gson.annotations.SerializedName

data class GoogleErrorResponse(
    @SerializedName("error")
    val error: String ,
    @SerializedName("error_description")
    val errorDescription: String
)