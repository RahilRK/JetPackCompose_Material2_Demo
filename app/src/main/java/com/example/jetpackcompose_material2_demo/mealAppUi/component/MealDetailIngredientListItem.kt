package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealDetailIngredientList

@Preview
@Composable
fun MealDetailIngredientListItem(
    model: MealDetailIngredientList = MealDetailIngredientList(
        strIngredient = "soy sauce",
        strMeasure = "3/4 cup"
    )
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterStart),
            text = model.strIngredient,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterEnd),
            text = model.strMeasure,
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
