package com.example.plantcare.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantcare.R
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun CategoryItems(
    photo : Int,
    name : String,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 10.dp))
    ){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.body1
                    .copy(fontWeight = FontWeight.Normal)
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun CategoryItemPreview() {
    PlantCareTheme {
        CategoryItems(photo = R.drawable.noto_tomato, name = "Tomat")
    }
}