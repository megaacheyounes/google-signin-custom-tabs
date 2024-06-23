package com.younes.oauth_signing_tabs_demo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.younes.oauth_signing_tabs_demo.ui.theme.GoogleOauthSigninTheme

/**
 * simple error alert dialog used in [Screen]
 */
@Composable
fun ErrorAlert(
    message: String,
) {

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog)
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.Warning,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Error",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            text = {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = message,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall
                )

            },
            confirmButton = { }, // No confirm button needed for errors
            dismissButton = {
                TextButton(
                    modifier = Modifier.padding(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        showDialog = false
                    },
                    content = { Text(text = "close") }
                )
            }
        )

}
@Preview
@Composable
private fun Preview() {
    GoogleOauthSigninTheme {
        ErrorAlert(
            message = "Lorem ipsum dolor shit amet "
        )
    }
}