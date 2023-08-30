package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetpack_compose_demo.data.model.Meal
import com.example.jetpackcompose_material2_demo.data.remoteModel.SearchMeal
import com.example.jetpackcompose_material2_demo.ui.theme.item_bg_color


@Preview
@Composable
fun SearchMealList(
    list: MutableList<SearchMeal> = arrayListOf(),
    onClickEvent: (pos: Int, model: SearchMeal) -> Unit = { pos: Int, mModel: SearchMeal -> },
) {
    LazyColumn(content = {
        itemsIndexed(list) { index, model ->

            SearchMealListItem(index, model, onClickEvent = onClickEvent)
        }
    }, modifier = Modifier, state = rememberLazyListState())

}

@Preview
@Composable
fun SearchMealListItem(
    index: Int = 0,
    model: SearchMeal = SearchMeal(
        strMeal = "Chicken",
        strArea = "Jamaican",
        strCategory = "Seafood"
    ),
    onClickEvent: (pos: Int, model: SearchMeal) -> Unit = { pos: Int, mModel: SearchMeal -> },
) {
    Card(
        elevation = 8.dp, backgroundColor = Color.White, shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .clickable {
                onClickEvent(index, model)
            }
            .padding(top = 12.dp)
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

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = model.strArea,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = model.strMeal,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        imageVector = Icons.Outlined.WatchLater,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "15 min",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomEnd).background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp))
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = model.strCategory,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )

            }
        }
    }
}