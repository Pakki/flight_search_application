package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.FavoriteAirport
import com.example.flightsearchapplication.data.FavoriteAirportDao
import kotlinx.coroutines.flow.Flow

class FavoriteScreenViewModel(
    private val favoriteAirportDao: FavoriteAirportDao): ViewModel() {


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                FavoriteScreenViewModel(
                    application.database.favoriteAirportDao()
                )
            }
        }
    }

    fun getFavoriteAirport(): Flow<List<FavoriteAirport>> =
        favoriteAirportDao.getFavoriteAirport()

}