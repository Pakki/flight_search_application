package com.example.flightsearchapplication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.media3.test.utils.TestUtil
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.data.AirportDao
import com.example.flightsearchapplication.data.FlightDatabase
import com.google.common.base.Predicates.equalTo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class FlightSearchDBTest {


    private lateinit var airportDao: AirportDao
    private lateinit var db: FlightDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(
            context, FlightDatabase::class.java).build()
        airportDao = db.airportDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readAirportsInList() = runBlocking{


        val airport: Airport = Airport(
            id =1,
            iataCode = "ISD",
            name = "Airport",
            passengers = 1
        )

        airportDao.insert(airport)

        val byName = airportDao.getAirportsLike("%rpo%").first()

        assertEquals(byName[0], airport)
    }

}