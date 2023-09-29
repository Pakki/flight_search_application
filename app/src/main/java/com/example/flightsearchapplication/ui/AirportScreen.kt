package com.example.flightsearchapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapplication.data.Airport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportScreen(
    currentAirport: Airport,
    paddingValues: PaddingValues
) {
    val airportScreenViewModel: AirportScreenViewModel = viewModel(
        factory = AirportScreenViewModel.Factory
    )
    val airports =
        airportScreenViewModel
            .getRelatedAirports(currentAirport.id)
            .collectAsState(emptyList()).value

    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Text(text = currentAirport.name)
        LazyColumn(modifier = Modifier) {
            items(
                items = airports,
                key = { airport -> airport.id }
            ) { airport ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "favorite"
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "${airport.iataCode} ${airport.name}")
                }
            }
        }
    }
}