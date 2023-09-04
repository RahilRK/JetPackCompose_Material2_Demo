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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jetpackcompose_material2_demo.data.remoteModel.Meal
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreenViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.IngredientScreenViewModel
import com.example.jetpackcompose_material2_demo.ui.theme.item_bg_color
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun IngredientWiseMealList(
    list: MutableList<Meal> = arrayListOf(),
    onClickEvent: (pos: Int, model: Meal) -> Unit = { pos: Int, mModel: Meal -> },
    mealLazyListState: LazyGridState = rememberLazyGridState(),
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val viewModel: IngredientScreenViewModel = hiltViewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    val mFirstVisibleItemIndex = remember {
        derivedStateOf {
            mealLazyListState.firstVisibleItemIndex
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val isScrollDown = delta > 0
//                Log.d("TAG", "isScrollDown: $isScrollDown")

//                if(mFirstVisibleItemIndex.value > 0) {
                onScrollEvent(isScrollDown)
//                }
                return super.onPreScroll(available, source)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {

                /*val delta = available.y
                val isScrollDown = delta > 0
//                Log.d("TAG", "isScrollDown: $isScrollDown")
                onScrollEvent(isScrollDown)*/
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        viewModel.updateIsRefreshValue(true)
        viewModel.getCategoryList()
    }) {
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 12.dp).nestedScroll(nestedScrollConnection),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            state = mealLazyListState
        ) {
            itemsIndexed(list) { index, model ->

                IngredientWiseMealListItem(index, model = model, onClickEvent = onClickEvent)
            }
        }
    }
}

@Preview
@Composable
fun IngredientWiseMealListItem(
    index: Int = 0,
    model: Meal = Meal(strMeal = "Canadian Butter Tarts"),
    onClickEvent: (pos: Int, model: Meal) -> Unit = { pos: Int, mModel: Meal -> },
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {

    Card(
        elevation = 8.dp, backgroundColor = Color.White, shape = RoundedCornerShape(8.dp),
        modifier = Modifier.clickable {

            onClickEvent(index, model)

            val mHideBottomNav = !hideBottomNav
            onScrollEvent(mHideBottomNav)
        }
    ) {
        Column(Modifier.height(320.dp)) {
            Box(
                Modifier
                    .fillMaxWidth()
//                    .align(Alignment.TopCenter)
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
            }

            Column(
                modifier = Modifier
//                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = model.strMeal,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    softWrap = true
                )
            }

        }
    }
}