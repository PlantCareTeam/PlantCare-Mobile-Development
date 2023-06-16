package com.example.plantcare.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantcare.R
import com.example.plantcare.ui.theme.PlantCareTheme
import java.util.Date


@Composable
fun HistoryItems(
    photo : Int,
    nameDisease : String,
    causeBy : String,
    category : String,
    date : String,
    modifier: Modifier = Modifier,
){
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(size = 10.dp))
    ) {
        Row(modifier = Modifier) {
            Image(
                painter = painterResource(photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(96.dp)
                    .width(96.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 12.dp, top = 4.dp)
            ) {
                Text(
                    text =  nameDisease,
                    style = MaterialTheme.typography.h6
                        .copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text =  causeBy,
                    style = MaterialTheme.typography.body2
                        .copy(fontWeight = FontWeight.Medium)
                )
            }
            Column (
                modifier = Modifier,
                horizontalAlignment = Alignment.End,
            ){
                Box(
                    modifier = Modifier
                        .width(66.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(bottomStart = 10.dp, topEnd = 10.dp))
                        .background(Color.Green)
                ){
                    Text(
                        text =  category,
                        style = MaterialTheme.typography.h6
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                }
                Text(
                    text =  date.toString(),
                    style = MaterialTheme.typography.body2
                        .copy(fontWeight = FontWeight.Light),
                    modifier = Modifier
                        .paddingFromBaseline(top = 50.dp)

                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun HistoryItemPreview() {
    PlantCareTheme {
        HistoryItems(
            photo = R.drawable.dummy_diseases,
            nameDisease = "Busuk Daun",
            causeBy = "Phytophtheora infestans",
            category = "Tomat",
            date = "23 Mei 2023"
        )
    }
}