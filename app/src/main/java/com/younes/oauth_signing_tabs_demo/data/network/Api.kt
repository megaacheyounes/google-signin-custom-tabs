package com.younes.oauth_signing_tabs_demo.data.network

import com.younes.oauth_signing_tabs_demo.data.entities.GoogleProfile
import com.younes.oauth_signing_tabs_demo.data.entities.GoogleTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OAuthApi {
    @FormUrlEncoded
    @POST("token")
    suspend fun exchangeCodeForToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String = "authorization_code"
    ): GoogleTokenResponse
}

interface UserApi {
    @GET("userinfo")
    suspend fun fetchUserProfile(
        @Header("Authorization") authorization: String
    ): GoogleProfile
}