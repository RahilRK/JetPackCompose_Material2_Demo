package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetpackcompose_material2_demo.data.remoteModel.Meal
import com.example.jetpackcompose_material2_demo.ui.theme.item_bg_color


@Composable
fun AreaWiseMealList(
    list: MutableList<Meal> = arrayListOf(),
    onClickEvent: (pos: Int, model: Meal) -> Unit = { pos: Int, mModel: Meal -> },
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 12.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(list) { index, model ->

            AreaWiseMealListItem(index, model = model, onClickEvent = onClickEvent)
        }
    }
}

@Preview
@Composable
fun AreaWiseMealListItem(
    index: Int = 0,
    model: Meal = Meal(strMeal = "Canadian Butter Tarts Cheeze"),
    onClickEvent: (pos: Int, model: Meal) -> Unit = { pos: Int, mModel: Meal -> },
) {

    Card(
        elevation = 8.dp, backgroundColor = Color.White, shape = RoundedCornerShape(8.dp),
        modifier = Modifier.clickable {
            onClickEvent(index, model)
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            AsyncImage(
                model = model.strMealThumb,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(item_bg_color),
            ) {

            }

            Image(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = model.strMeal,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
            }
        }
    }
}