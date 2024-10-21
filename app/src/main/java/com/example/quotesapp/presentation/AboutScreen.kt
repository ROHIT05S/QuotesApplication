package com.example.quotesapp.presentation

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotesapp.utils.kalamFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(quote: String) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Quotes Application!",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "by RPS",
                    style = TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = quote, modifier = Modifier.basicMarquee(), fontSize = 30.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = kalamFamily,
                    fontWeight = FontWeight.Bold,

                    )

            }
        }
    )


}
