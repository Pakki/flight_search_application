package com.example.flightsearchapplication.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapplication.data.Airport
import com.example.flightsearchapplication.ui.theme.FlightSearchApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportCard(
    airport: Airport,
    highlitedPosition: List<Int>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = ShapeDefaults.ExtraSmall,
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp),
        onClick = { onClick }
    ) {
        Row {
            Text(
                text =
                AnnotatedString(
                    text = "${airport.iataCode} ${airport.name}",
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(
                                background = MaterialTheme.colorScheme.primary,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            ),
                            highlitedPosition[0], highlitedPosition[1]
                        )
                    ),
                )
            )
        }
    }
}

@Preview
@Composable
fun AirportCardPreview() {
    FlightSearchApplicationTheme {
        AirportCard(
            airport = Airport(
                id = 1,
                iataCode = "ASD",
                name = "Average standart Airport",
                passengers = 44
            ),
            listOf(6, 3),
            {}
        )
    }

}