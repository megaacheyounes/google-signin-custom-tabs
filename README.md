# Google OAuth2 Sign-In for Android without Google SDK

## Introduction

This project demonstrates how to implement Google Sign-In on Android devices without using the Google Sign-In SDK. Instead, it leverages OAuth 2.0 Web APIs and Chrome Custom Tabs to authenticate users and fetch their profiles.

## Contents

- [Introduction](#introduction)
- [Contents](#contents)
- [How It Works](#how-it-works)
- [Project Setup](#coding-steps)
- [Demo](#demo)
- [License](#license)

## How It Works

This project uses the following steps to achieve Google Sign-In without Google SDK:

<p width={400} align="center">
<img width='400' src='screenshots/googel-auth-flow.png' /> 
</p>

1. **OAuth 2.0 Authorization**: Redirects the user to Google's OAuth 2.0 authorization endpoint using Chrome Custom Tabs.
2. **Redirection**: After the user authorizes the app, Google web server then redirects the user to the custom URI scheme of the app, which will launch the app.
3. **Token Exchange**: The app receives an authorization code,which will exchange it for an access token using Retrofit.
4. **Fetch User Profile**: The access token is used to fetch the user's profile information.

## Demo

> video is loading, be patient!

> <small> [click here if demo fails to load](https://github.com/megaacheyounes/google-signin-custom-tabs/blob/master/screenshot/demo.mp4) </small>

## Project Setup

### **Go to the Google Developers Console**

- Login to google developer console: <https://console.cloud.google.com/>
- Create a new project or select an existing one.
- create OAuth consent screen: <https://console.cloud.google.com/apis/credentials/consent>t
- Navigate to the `Credentials` tab: <https://console.cloud.google.com/apis/credentials>s
- Create an OAuth 2.0 Client ID and choose `Web application` as the application type.
- Set the authorized redirect URI to a web server you control, e.g., `https://younes-signin-demo.com/oauth2redirect`.
- (optional) for testing, it is recommended to add a test account: go to `credentials` tab, click on your OAuth 2.0 client ID, scroll to test users section, and click on `ADD USERS`

See Google's official documentation for more details: <https://developers.google.com/identity/protocols/oauth2/javascript-implicit-flow>

### Add Google Credentials to your project

In `properties.gradle`, add your Google OAuth2.0 credentials as follows (see `properties.example.gradle`):

```gradle
# google oauth sign in
CLIENT_ID = "your_client_id"
CLIENT_SECRET = "your_client_secret"
REDIRECT_URI = "http://your-domain.com/oauth2callback"
```

### Configure AndroidManifest.xml file

Ensure your app can handle Google sign in redirection uri from Chrome Custom tab, by declaring an activity as handler of Google OAuth callback. In `AndroidManifest.json`, add an intent filter with your OAuth redirect URI, e.g:

```xml
        <activity
            android:name=".MainActivity"
            android:exported="true"
            >

            <!-- OTHER CODE -->

            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />

                <category android:name="android.intent.category.BROWSABLE" />

                <!-- update the domain below to match the REDIRECT_URI in gradle.properties -->
                <data
                    android:host="your-domain.com"
                    android:scheme="http" />

            </intent-filter>

        </activity>
```

Run and test the demo

## License

```txt
               DO WHAT YOU WANT TO PUBLIC LICENSE

Copyright (C) 2024 Younes Megaache

Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.

                DO WHAT YOU WANT TO PUBLIC LICENSE

TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

1. You just DO WHAT YOU WANT TO.

```
