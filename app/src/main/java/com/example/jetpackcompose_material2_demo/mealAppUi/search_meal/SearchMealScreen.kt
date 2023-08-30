package com.example.jetpackcompose_material2_demo.mealAppUi.search_meal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.component.SearchMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.SearchView
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state.SearchMealListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.util.Constants
import kotlinx.coroutines.CoroutineScope

@Preview
@Composable
fun SearchMealScreen() {

    val viewModel: SearchMealScreenViewModel = hiltViewModel()
    val searchTextState by viewModel.searchTextState
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
            SearchView(
                text = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                    viewModel.getSearchMealList()
//                    Log.d(SEARCH_MEAL_SCREEN_TAG, "onTextChange: $it")
                },
                onCloseClick = {
                    viewModel.updateSearchTextState(newValue = "")
               },
                onSearchClick = {

                    viewModel.updateSearchTextState(newValue = it)
                    viewModel.getSearchMealList()
//                    Log.d(SEARCH_MEAL_SCREEN_TAG, "onSearchClick: $it")
                },
            )

            MealSearchList(viewModel, context)
        }
    }

}

@Composable
fun MealSearchList(
    viewModel: SearchMealScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val searchMealListState =
        viewModel.mealList.collectAsState(initial = SearchMealListState.Loading)
    when (val result = searchMealListState.value) {
        SearchMealListState.Loading -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "LoadMealList Loading...")
        }

        is SearchMealListState.Success -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "LoadMealList Success: ${result.list}")
            SearchMealList(result.list, onClickEvent = { pos, model ->

            })
        }

        is SearchMealListState.Empty -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "LoadMealList Empty: ")
        }

        is SearchMealListState.Error -> {
            Log.e(Constants.SEARCH_MEAL_SCREEN_TAG, "LoadMealList Error: $result")
            Toast.makeText(context, "Unable to show search meal result", Toast.LENGTH_SHORT).show()
        }
    }
}