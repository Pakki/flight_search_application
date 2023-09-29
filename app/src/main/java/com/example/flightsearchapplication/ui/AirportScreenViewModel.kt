package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.Favorite
import com.example.flightsearchapplication.data.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AirportScreenViewModel(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
    ) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                AirportScreenViewModel(
                    application.database.airportDao(),
                    application.database.favoriteDao()
                )
            }
        }
    }

    fun getRelatedAirports(iataCode: String): Flow<List<Airport>> =
        airportDao.getRelatedAirports(iataCode)

    fun addFavorite(
        departureCode: String,
        destinationCode: String
    ){
        viewModelScope.launch {
            favoriteDao.insert(
                Favorite(
                    departureCode = departureCode,
                    destinationCode = destinationCode
                )
            )
        }
    }
}