package com.example.flightsearchapplication

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.navigation.testing.TestNavHostController
import com.example.flightsearchapplication.data.NavigationItem
import org.junit.Rule
import org.junit.Test
import com.example.flightsearchapplication.R

class FavoriteFlightsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Test
    fun makeFlightFavorite(){

    }
    @Test
    fun navigateToAirportScreen(){
        composeTestRule
            .onNodeWithContentDescription(label = composeTestRule.activity.getString(R.string.search_field_clear_button))
            .performClick()
        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.search_field_label))
            .performTextInput("opo")
        composeTestRule.onAllNodesWithTag(testTag = composeTestRule.activity.getString(R.string.airport_tag))[0]
            .performClick()
        navController.assertCurrentRouteName(NavigationItem.Airport.screen.route)

        //
        var favoriteButton = composeTestRule
            .onAllNodesWithContentDescription(label = composeTestRule.activity.getString(R.string.favorite_button))[0]
        favoriteButton.assertExists()
        Log.d("FavoriteButton", favoriteButton.toString())
        favoriteButton.printToLog("FavoriteButton")
        favoriteButton.performClick()
        favoriteButton.printToLog("FavoriteButton after click")

    }


}