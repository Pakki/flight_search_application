package com.example.flightsearchapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapplication.FlightSearchApplication
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.UserInputRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FlightSearchScreenViewModel(
    private val userInputRepository: UserInputRepository,
    private val airportDao: AirportDao
) : ViewModel() {

    val uiState: StateFlow<FlightSearchUiState> =
        userInputRepository.searchPhrase.map { searchPhrase ->
            FlightSearchUiState(searchPhrase)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FlightSearchUiState()
            )

    fun savePhrase(searchPhrase: String) {

        viewModelScope.launch {
            userInputRepository.saveSearchPhrase(searchPhrase)
        }
    }

    fun getAirportsLike(searchPhrase: String): Flow<List<Airport>> =
        airportDao.getAirportsLike("%"+searchPhrase+"%")



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchScreenViewModel(
                    application.userInputRepository,
                    application.database.airportDao()
                    )
            }
        }
    }
}

data class FlightSearchUiState(
    val searchPhrase: String = ""
)