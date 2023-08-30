package com.example.jetpackcompose_material2_demo.mealAppUi.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_material2_demo.R
import com.example.jetpackcompose_material2_demo.mealAppUi.component.HomeCategoryList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.HomeMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.HomeStaticSearch
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.CategoryListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.util.Constants.HOME_SCREEN_TAG
import com.example.jetpackcompose_material2_demo.util.Constants.SEARCH_MEAL_SCREEN_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .background(bg_color)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Header(context, onSearchClick = {
                navController.navigate(SEARCH_MEAL_SCREEN_ROUTE)
            })

            LoadCategoryList(viewModel, context)

            LoadMealList(viewModel, context)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Header(
    context: Context = LocalContext.current,
    onSearchClick: () -> Unit = {}
) {
    Column {
        Row() {
            Column(modifier = Modifier.weight(0.7f)) {
                Text(
                    text = "Hello, Lisa",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "What would you like to cook today?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Image(
                painterResource(id = R.drawable.ic_lady),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
                    .border(1.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        HomeStaticSearch(context, onSearchClick)
    }
}

@Composable
fun LoadCategoryList(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val categoryListState =
        viewModel.categoryList.collectAsState(initial = CategoryListState.Loading)
    when (val result = categoryListState.value) {
        CategoryListState.Loading -> {
            Log.d(HOME_SCREEN_TAG, "LoadCategoryList Loading...")
        }

        is CategoryListState.Success -> {
            Log.d(HOME_SCREEN_TAG, "LoadCategoryList Success:")

            val listState = rememberLazyListState()

            HomeCategoryList(result.list, onClickEvent = { pos, model ->

                viewModel.selectCategory(pos, model, result.list)
            }, listState)
        }

        is CategoryListState.Empty -> {
            Log.d(HOME_SCREEN_TAG, "LoadCategoryList Empty: ")
        }

        is CategoryListState.Error -> {
            Log.e(HOME_SCREEN_TAG, "LoadCategoryList Error: $result")
            Toast.makeText(context, "Unable to load category", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun LoadMealList(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val mealListState =
        viewModel.mealList.collectAsState(initial = MealListState.Loading)
    when (val result = mealListState.value) {
        MealListState.Loading -> {
            Log.d(HOME_SCREEN_TAG, "LoadMealList Loading...")
        }

        is MealListState.Success -> {
            Log.d(HOME_SCREEN_TAG, "LoadMealList Success:")
            HomeMealList(result.list, onClickEvent = { pos, model ->

            })
        }

        is MealListState.Empty -> {
            Log.d(HOME_SCREEN_TAG, "LoadMealList Empty: ")
        }

        is MealListState.Error -> {
            Log.e(HOME_SCREEN_TAG, "LoadMealList Error: $result")
            Toast.makeText(context, "Unable to load meal list", Toast.LENGTH_SHORT).show()
        }
    }
}