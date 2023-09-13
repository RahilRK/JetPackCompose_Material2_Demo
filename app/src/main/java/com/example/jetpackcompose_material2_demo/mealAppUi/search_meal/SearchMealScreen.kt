package com.example.jetpackcompose_material2_demo.mealAppUi.search_meal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.component.SearchMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.SearchView
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state.SearchMealListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.ui.theme.meal_color_primary
import com.example.jetpackcompose_material2_demo.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val searchMealListState =
        viewModel.mealList.collectAsState(initial = SearchMealListState.Loading)
    when (val result = searchMealListState.value) {
        SearchMealListState.Loading -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "MealSearchList Loading...")
        }

        is SearchMealListState.Success -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "MealSearchList Success: ${result.list}")

            val searchLazyListState = rememberLazyListState()
            /*
                        LaunchedEffect(key1 = Unit) {
                            coroutineScope.launch{
                                searchLazyListState.animateScrollToItem(0)
                            }
                        }
            */

            SearchMealList(result.list, onClickEvent = { pos, model ->

            }, searchLazyListState)
        }

        is SearchMealListState.Empty -> {
            Log.d(Constants.SEARCH_MEAL_SCREEN_TAG, "MealSearchList Empty: ")
            Toast.makeText(context, "No result found", Toast.LENGTH_SHORT).show()
        }

        is SearchMealListState.Error -> {
            Log.e(Constants.SEARCH_MEAL_SCREEN_TAG, "MealSearchList Error: $result")
            Toast.makeText(context, "Unable to show search meal result", Toast.LENGTH_SHORT).show()
        }
    }
}