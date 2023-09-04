package com.example.jetpackcompose_material2_demo.mealAppUi.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreenViewModel
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Preview
@Composable
fun HomeCategoryList(
    list: MutableList<Category> = arrayListOf(),
    onClickEvent: (pos: Int, model: Category) -> Unit = { pos: Int, mModel: Category -> },
    categoryLazyListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyListState = rememberLazyListState(),
) {
    LazyRow(content = {
        itemsIndexed(list) { index, model ->

            HomeCategoryListItem(
                index,
                model,
                onClickEvent = onClickEvent,
                categoryLazyListState,
                mealLazyListState,
            )
        }
    }, modifier = Modifier, state = categoryLazyListState)
}

@Preview
@Composable
fun HomeCategoryListItem(
    index: Int = 0,
    model: Category = Category(strCategory = "Chicken", isSelected = false),
    onClickEvent: (pos: Int, model: Category) -> Unit = { pos: Int, mModel: Category -> },
    categoryLazyListState: LazyListState = rememberLazyListState(),
    mealLazyListState: LazyListState = rememberLazyListState(),
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Box(Modifier.padding(end = 8.dp)) {
        Row(
            Modifier
                .background(
                    if (model.isSelected)
                        meal_color_primary
                    else
                        Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable {

                    if (!model.isSelected) {
                        val getModelData = model.copy(isSelected = true)
                        onClickEvent(index, getModelData)
                        coroutineScope.launch {
                            categoryLazyListState.animateScrollToItem(index)
                            mealLazyListState.animateScrollToItem(0)
                        }
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                AsyncImage(
                    model = model.strCategoryThumb,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = model.strCategory,
                color = if (model.isSelected)
                    Color.White
                else
                    Color.DarkGray.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}