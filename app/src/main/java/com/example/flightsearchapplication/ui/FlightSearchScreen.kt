package com.example.flightsearchapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.NavigationItem
import com.example.flightsearchapplication.navigation.Screen


@Composable
fun FlightSearchApp() {
    FlightSearchScreen()
}


@Composable
fun FlightSearchScreen(modifier: Modifier = Modifier) {

    val flightSearchScreenViewModel: FlightSearchScreenViewModel = viewModel(
        factory = FlightSearchScreenViewModel.Factory
    )
    val currentAirport =
        remember {
            mutableStateOf(
                Airport(
                    name = "",
                    iataCode = "",
                    passengers = 0
                )
            )
        }

    val selectedNavigationItem by flightSearchScreenViewModel.navState.collectAsState()

    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                currentPage = stringResource(selectedNavigationItem.title),
                navHostController = navHostController
            )
        },

                bottomBar = {
            NavigationBar(modifier = modifier) {
                val items = listOf<NavigationItem>(
                    NavigationItem.Home,
                    NavigationItem.Favorite
                )
                items.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.screen.route == navBackStackEntry?.destination?.route,
                        onClick = {
                            navHostController.navigate(navigationItem.screen.route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            //flightSearchScreenViewModel.selectNavItem(navigationItem)
                        },
                        icon = {
                            Icon(
                                imageVector = navigationItem.icon,
                                contentDescription = stringResource(id = navigationItem.title)
                            )
                        },
                        label = {
                            Text(text = stringResource(id = navigationItem.title))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                FlightsSearchField(
                    flightSearchScreenViewModel = flightSearchScreenViewModel,
                    paddingValues = innerPadding,
                    navHostController = navHostController,
                    currentAirport = currentAirport
                )
                flightSearchScreenViewModel.selectNavItem(NavigationItem.Home)
            }
            composable("Airport") {
                AirportScreen(paddingValues = innerPadding, currentAirport = currentAirport.value)
                flightSearchScreenViewModel.selectNavItem(NavigationItem.Airport)
            }
            composable(Screen.Favorities.route) {
                FavoriteScreen(paddingValues = innerPadding)
                flightSearchScreenViewModel.selectNavItem(NavigationItem.Favorite)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(currentPage: String, navHostController: NavHostController) {

    androidx.compose.material3.TopAppBar(
        title = {
            Text(text = currentPage)
        },
        navigationIcon = {
            if (currentPage != stringResource(NavigationItem.Home.title)) {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = currentPage
                    )
                }
            }
        }
    )
}

@Composable
fun FlightsSearchField(
    flightSearchScreenViewModel: FlightSearchScreenViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    currentAirport: MutableState<Airport>
) {

    var firstRun by rememberSaveable {
        mutableStateOf(true)
    }
    val uiState = flightSearchScreenViewModel.uiState.collectAsState().value
    var text by rememberSaveable {
        mutableStateOf("")
    }

    if (firstRun && uiState.searchPhrase.isNotBlank()) {
        text = uiState.searchPhrase
        firstRun = false
    }

    Column(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
    ) {

        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Icon",
                    tint = Color.White
                )
            },
            value = text,
            onValueChange =
            { it ->
                text = it
                flightSearchScreenViewModel.savePhrase(it)
            },
            label = { Text("Input airport here") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    flightSearchScreenViewModel.savePhrase("")
                    text = ""
                },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                )
            }
        )
        val airports =
            flightSearchScreenViewModel.getAirportsLike(uiState.searchPhrase).collectAsState(
                emptyList()
            )
        SearchResultList(
            airports = airports.value,
            navHostController = navHostController,
            uiState.searchPhrase,
            currentAirport = currentAirport
        )
    }
}

@Composable
fun SearchResultList(
    airports: List<Airport>,
    navHostController: NavHostController,
    searchPhrase: String,
    modifier: Modifier = Modifier,
    currentAirport: MutableState<Airport>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = airports,
            key = { airport -> airport.id }
        ) { airport ->
            AirportCard(
                airport,
                highlitedPosition(
                    airport = airport,
                    searchPhrase = searchPhrase
                ),
                navHostController,
                currentAirport = currentAirport
            )
        }
    }
}

fun highlitedPosition(airport: Airport, searchPhrase: String): List<Int> {
    val first = "${airport.iataCode} ${airport.name}".indexOf(searchPhrase)
    if (first < 0) {
        return listOf(0, 0)
    }
    return listOf(first, first + searchPhrase.length)
}

@Preview
@Composable
fun FlightSearchScreenPreview() {
    FlightSearchScreen(

    )
}
