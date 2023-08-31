package com.example.jetpackcompose_material2_demo.mealAppUi.country_meal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.component.AreaList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.AreaWiseMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.TopBar
import com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.state.AreaListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.util.Constants

@Composable
fun CountryMealScreen() {


    Scaffold(
        modifier = Modifier
            .background(bg_color)
            .fillMaxSize(),
        topBar = {
            TopBar(navigationIcon = Icons.Outlined.LocationOn, title = "Choose country's special")
        },
        content = {
            Column(
                Modifier
                    .padding(it)
                    .background(bg_color)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp)
                ) {
                    LoadAreaList()
                }
            }
        }
    )
}

@Composable
fun LoadAreaList(
    viewModel: CountryScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val areaListState =
        viewModel.areaList.collectAsState(initial = AreaListState.Loading)
    when (val result = areaListState.value) {
        AreaListState.Loading -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Loading...")
        }

        is AreaListState.Success -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Success:")

            val listState = rememberLazyListState()

            AreaList(result.list, onClickEvent = { pos, model ->

                viewModel.selectArea(pos, model, result.list)
            }, listState)

            LoadAreaWiseMealList()
        }

        is AreaListState.Empty -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadCategoryList Empty: ")
        }

        is AreaListState.Error -> {
            Log.e(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Error: $result")
            Toast.makeText(context, "Unable to area list", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun LoadAreaWiseMealList(
    viewModel: CountryScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val areaWiseMealListState =
        viewModel.areaWiseMealList.collectAsState(initial = MealListState.Loading)
    when (val result = areaWiseMealListState.value) {
        MealListState.Loading -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Loading...")
        }

        is MealListState.Success -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Success:")
            AreaWiseMealList(result.list, onClickEvent = { pos, model ->
                Toast.makeText(context, model.strMeal, Toast.LENGTH_SHORT).show()
            })
        }

        is MealListState.Empty -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Empty: ")
        }

        is MealListState.Error -> {
            Log.e(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Error: $result")
            Toast.makeText(context, "Unable to load area wise meal list", Toast.LENGTH_SHORT).show()
        }
    }
}