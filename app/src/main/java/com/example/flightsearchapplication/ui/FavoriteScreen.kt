package com.example.flightsearchapplication.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier){
    val favoriteScreenViewModel: FavoriteScreenViewModel = viewModel(
        factory = FavoriteScreenViewModel.Factory
    )
    val favoriteAirports = favoriteScreenViewModel
        .getFavoriteAirport()
        .collectAsState(emptyList()).value
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = favoriteAirports,
            key = { airport -> airport.id}
        ){airport ->
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    //favoriteScreenViewModel.addFavorite(currentAirport.iataCode, airport.iataCode)
                }) {
                    Icon(
                        imageVector = if (true){
                            Icons.Outlined.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        }
                        ,
                        contentDescription = "favorite"
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "${airport.departureCode} ${airport.departureName} -> ${airport.destinationCode} ${airport.destinationName}")
            }
        }
    }
}