package com.example.flightsearchapplication

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.ui.AirportCard
import com.example.flightsearchapplication.ui.FlightSearchScreenViewModel


@Composable
fun FlightSearchApp(

) {
    FlightSearchScreen(

    )
}


@Composable
fun FlightSearchScreen(

) {
    val flightSearchScreenViewModel: FlightSearchScreenViewModel = viewModel(
        factory = FlightSearchScreenViewModel.Factory
    )
    Scaffold { innerPaddng ->
        FlightsSearchField(
            flightSearchScreenViewModel = flightSearchScreenViewModel,
            paddingValues = innerPaddng,

            )

    }
}


@Composable
fun FlightsSearchField(
    flightSearchScreenViewModel: FlightSearchScreenViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,

    ) {

    val uiState = flightSearchScreenViewModel.uiState.collectAsState().value
    Column(
        modifier = modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
    ) {

        var text by remember { mutableStateOf("") }
        if (uiState.searchPhrase.isNotEmpty() && text.isEmpty()) {
            text = uiState.searchPhrase
            //Log.d("FlightSearchScreen", "current saved phrase is: ${uiState.searchPhrase}")
        }


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
                flightSearchScreenViewModel.savePhrase(text)

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
            uiState.searchPhrase
        )
    }

}

@Composable
fun SearchResultList(
    airports: List<Airport>,
    searchPhrase: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = airports,
            key = {airport -> airport.id}
        ){airport ->


            AirportCard(airport,
                highlitedPosition(
                    airport = airport,
                    searchPhrase = searchPhrase),
                {}
            )
        }

    }
}

fun highlitedPosition(airport: Airport, searchPhrase: String): List<Int>{
    val first = "${airport.iataCode} ${airport.name}".indexOf(searchPhrase)
    if (first < 0){
        return listOf(0,0)
    }
    return listOf(first, first + searchPhrase.length)
}

@Preview
@Composable
fun FlightSearchScreenPreview() {
    FlightSearchScreen(

    )
}
