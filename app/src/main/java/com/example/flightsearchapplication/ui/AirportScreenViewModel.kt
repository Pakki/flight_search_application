package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.FavoriteDao
import kotlinx.coroutines.flow.Flow

class AirportScreenViewModel(
    private val airportDao: AirportDao,

) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                AirportScreenViewModel(
                    application.database.airportDao()

                )
            }
        }
    }

    fun getRelatedAirports(airportId: Int): Flow<List<Airport>> =
        airportDao.getRelatedAirports(airportId)
}