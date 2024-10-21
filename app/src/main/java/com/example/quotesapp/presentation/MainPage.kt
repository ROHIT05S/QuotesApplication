package com.example.quotesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.quotesapp.R
import com.example.quotesapp.data.QuotesResult
import com.example.quotesapp.presentation.utils.CenteredCircularProgressIndicator
import com.example.quotesapp.ui.theme.QuotesAppTheme
import com.example.quotesapp.utils.NetworkConstants.BASE_URL
import com.example.quotesapp.utils.NetworkConstants.IMAGE_URL
import com.example.quotesapp.utils.QuotesState
import com.example.quotesapp.utils.kalamFamily
import com.example.quotesapp.viewmodels.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainPage : ComponentActivity() {
    private val viewModel: QuotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Red
                ) {
                    // Initialize the NavController
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    val currentDestination = navBackStackEntry?.destination
                    Scaffold(bottomBar = {
                        // Create Bottom Navigation Bar
                        BottomNavigationBar(navController = navController, currentDestination)
                    }) { paddingVal ->
                        // Create NavHost to navigate between tabs
                        NavHost(
                            modifier = Modifier.padding(paddingVal),
                            navController = navController,
                            startDestination = BottomNavScreens.Home.route
                        ) {
                            // Define screens for each tab
                            composable(BottomNavScreens.Home.route) {
                                HomeScreen(viewModel)
                            }

                            composable(BottomNavScreens.Quote.route) {
                                QuoteScreen(viewModel)
                            }

                            composable(BottomNavScreens.About.route) {
                                val randomQuoteState by viewModel.randomQuoteResponse.collectAsState()
 when (randomQuoteState) {
     is QuotesState.Success -> {
         val randomQuoteData = (randomQuoteState as QuotesState.Success<List<QuotesResult?>>).data
         var quote = randomQuoteData[0]?.quote!!+"--"+randomQuoteData[0]?.author!!
         AboutScreen(quote)
     }

     is QuotesState.Error -> {
         // Handle error state for random quotes
         val errorMessage = (randomQuoteState as QuotesState.Error).message
         // Render UI with errorMessage
         ShowError(errorMessage)
     }

     is QuotesState.Loading -> {
         // Handle loading state for random quotes
         // Render loading indicator or any UI
         CenteredCircularProgressIndicator()
     }
 }
                            }

                        }
                    }
                }
            }
        }
    }
}

sealed class BottomNavScreens(val route: String) {
    object Home : BottomNavScreens("home")
    object Quote : BottomNavScreens("quotes")
    object About : BottomNavScreens("about")
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentDestination: NavDestination?) {
    val items = listOf(
        BottomNavScreens.Home, BottomNavScreens.Quote, BottomNavScreens.About
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(icon = {
                when (screen) {
                    BottomNavScreens.Home -> Icon(
                        Icons.Default.Home, contentDescription = null, tint = Color.Red
                    )

                    BottomNavScreens.Quote -> Icon(
                        Icons.Default.FormatQuote, contentDescription = null, tint = Color.Red
                    )

                    BottomNavScreens.About -> Icon(
                        Icons.Default.Info, contentDescription = null, tint = Color.Red
                    )

                }
            },
                label = { Text(screen.route.capitalize(), color = Color.Red) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        }
    }
}


@Composable
fun HomeScreen(viewModel: QuotesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quote Of The Day",  fontSize = 44.sp,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = kalamFamily,
        fontWeight = FontWeight.Bold)
        viewModel.fetchRandomQuotes()
        RandomQuoteScreen(viewModel = viewModel)
    }
}

@Composable
fun QuoteScreen(viewModel: QuotesViewModel) {
    val quoteState by viewModel.quoteResponse.collectAsState()
    when (quoteState) {
        is QuotesState.Success -> {
            // Handle success state for regular quotes
            val quoteData = (quoteState as QuotesState.Success<List<QuotesResult?>>)
            // Render UI with quoteData
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            ShowQuotesList(quoteData)

        }

        is QuotesState.Error -> {
            // Handle error state for regular quotes
            val errorMessage = (quoteState as QuotesState.Error).message
            // Render UI with errorMessage
            ShowError(errorMessage)
        }

        is QuotesState.Loading -> {
            // Handle loading state for regular quotes
            // Render loading indicator or any UI
            CenteredCircularProgressIndicator()
        }
    }
}

@Composable
fun ShowQuotesList(quoteData: QuotesState.Success<List<QuotesResult?>>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        val quotesList = (quoteData as QuotesState.Success<List<QuotesResult?>>).data
        items(quotesList) { item: QuotesResult? ->
            QuoteCard(item?.quote!!, item.author)
        }
    }

}
@Composable
fun RandomQuoteScreen(viewModel: QuotesViewModel) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(BASE_URL+IMAGE_URL)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier.fillMaxWidth()
    )
}



@Composable
fun ShowError(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.SignalWifiStatusbarConnectedNoInternet4,
                contentDescription = "contentDescription",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(message)
        }

    }

}
