package com.example.flightsearchapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(airport: Airport)

    @Query("select * from airport where iata_code like :searchPhrase or name like :searchPhrase")
    fun getAirportsLike(searchPhrase: String): Flow<List<Airport>>

    @Query("select * from airport where iata_code != :iataCode")
    fun getRelatedAirports(iataCode: String): Flow<List<Airport>>
}