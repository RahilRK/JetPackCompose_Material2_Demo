package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.remoteModel.Ingredient
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun IngredientList(
    list: MutableList<Ingredient> = arrayListOf(),
    onClickEvent: (pos: Int, model: Ingredient) -> Unit = { pos: Int, mModel: Ingredient -> },
    mListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyGridState = rememberLazyGridState()
) {
    LazyRow(content = {
        itemsIndexed(list) { index, model ->

            IngredientListItem(index, model, onClickEvent = onClickEvent, mListState, mealLazyListState = mealLazyListState)
        }
    }, modifier = Modifier, state = mListState)
}

@Preview(showSystemUi = true)
@Composable
fun IngredientListItem(
    index: Int = 0,
    model: Ingredient = Ingredient(strIngredient = "Chicken", isSelected = true),
    onClickEvent: (pos: Int, model: Ingredient) -> Unit = { pos: Int, mModel: Ingredient -> },
    mListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyGridState = rememberLazyGridState()
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .padding(8.dp)
            .clickable {

                if (!model.isSelected) {
                    val getModelData = model.copy(isSelected = true)
                    onClickEvent(index, getModelData)
                    coroutineScope.launch {
                        mListState.animateScrollToItem(index)
                        mealLazyListState.animateScrollToItem(0)
                    }
                }

            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = model.strIngredient,
            color = if (model.isSelected) meal_color_primary else Color.Gray,
            fontSize = 18.sp,
            fontWeight = if (model.isSelected) FontWeight.Bold else FontWeight.Medium
        )
        Divider(
            color = if (model.isSelected) meal_color_primary else Color.Transparent,
            modifier = Modifier
                .width(16.dp)
                .height(12.dp)
                .padding(top = 4.dp, bottom = 4.dp)
                .shadow(elevation = if (model.isSelected) 1.dp else 0.dp, shape = RoundedCornerShape(16.dp))
        )
    }
}