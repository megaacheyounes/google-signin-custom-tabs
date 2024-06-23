package com.younes.oauth_signing_tabs_demo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * base screen that render screen title, and handle loading and error states
 */
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean,
    error: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
        )

        val progressBarHeight = 4.dp
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .height(progressBarHeight - 2.dp)
                    .fillMaxWidth()
            )
        } else {
            Spacer(modifier.height(progressBarHeight))
        }

        content()

        error?.let {
           ErrorAlert(message = it)
        }
    }
}