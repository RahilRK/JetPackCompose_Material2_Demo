package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose_material2_demo.data.remoteModel.Area
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AreaList(
    list: MutableList<Area> = arrayListOf(),
    onClickEvent: (pos: Int, model: Area) -> Unit = { pos: Int, mModel: Area -> },
    countryLazyListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyGridState = rememberLazyGridState(),
) {
    LazyRow(content = {
        itemsIndexed(list) { index, model ->

            AreaListItem(index, model, onClickEvent = onClickEvent, countryLazyListState, mealLazyListState)
        }
    }, modifier = Modifier, state = countryLazyListState)

}

@Preview
@Composable
fun AreaListItem(
    index: Int = 0,
    model: Area = Area(strArea = "America", isSelected = true),
    onClickEvent: (pos: Int, model: Area) -> Unit = { pos: Int, mModel: Area -> },
    countryLazyListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyGridState = rememberLazyGridState(),
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(end = 8.dp)) {
        Box(
            modifier = Modifier
                .then(
                    if (model.isSelected)
                        Modifier
                            .background(
                                meal_color_primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    else
                        Modifier
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp)

                )
                .clickable {
                    if (!model.isSelected) {
                        val getModelData = model.copy(isSelected = true)
                        onClickEvent(index, getModelData)
                        coroutineScope.launch {
                            countryLazyListState.animateScrollToItem(index)
                            mealLazyListState.animateScrollToItem(0)
                        }
                    }
                }
        ) {
            Text(
                text = model.strArea,
                color = if (model.isSelected)
                    meal_color_primary
                else
                    Color.Gray.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }

    }
}