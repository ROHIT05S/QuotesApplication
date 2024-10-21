package com.example.quotesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotesapp.data.QuotesResult
import com.example.quotesapp.utils.QuotesState
import com.example.quotesapp.utils.kalamFamily

@Composable
fun QuoteCard(quote:String, author:String) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        //GradientStart: “#D9FF43”, GradientCenter: “#F67831”, GradientEnd: “#FF1493”
                        //generateRandomGradient()
                        brush = Brush.verticalGradient(
                            0.0f to Color.Magenta, 1.0f to Color.Cyan, startY = 0.0f, endY = 1500.0f
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = quote,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = kalamFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = author,
                        fontFamily = kalamFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                // Quote icon button partially outside the card
                Icon(imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomCenter)
                        .background(Color.Transparent)
                        .clickable {
                            // Handle click action
                        })
            }


}
