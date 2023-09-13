package com.example.jetpackcompose_material2_demo.mealAppUi.country_meal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose_material2_demo.mealAppUi.component.AreaList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.AreaWiseMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.LoadingDialog
import com.example.jetpackcompose_material2_demo.mealAppUi.component.TopBar
import com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.state.AreaListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.Header
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreenViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.home.LoadCategoryList
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CountryMealScreen(
    navController: NavHostController = rememberNavController(),
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val viewModel: CountryScreenViewModel = hiltViewModel()
    val mLoadingDialogueState by viewModel.loadingDialogueState.collectAsState()

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
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(12.dp)
                    ) {
                        LoadAreaList(
                            hideBottomNav = hideBottomNav,
                            onScrollEvent = onScrollEvent
                        )
                    }

                    if(mLoadingDialogueState) {
                        LoadingDialog()
                    }
                }
            }
        }
    )
}

@Composable
fun LoadAreaList(
    viewModel: CountryScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val areaListState =
        viewModel.areaList.collectAsState(initial = AreaListState.Loading)
    when (val result = areaListState.value) {
        AreaListState.Loading -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Loading...")
        }

        is AreaListState.Success -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Success:")

            val countryLazyListState = rememberLazyListState()
            val mealLazyListState = rememberLazyGridState()

            AreaList(result.list, onClickEvent = { pos, model ->

                viewModel.selectArea(pos, model, result.list)
                onScrollEvent(true)

            }, countryLazyListState, mealLazyListState)

            LoadAreaWiseMealList(
                viewModel, context, mealLazyListState, hideBottomNav = hideBottomNav,
                onScrollEvent = onScrollEvent
            )
            if (viewModel.isRefreshing.collectAsState().value) {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        countryLazyListState.animateScrollToItem(0)
                    }
                }
            }
        }

        is AreaListState.Empty -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadCategoryList Empty: ")
            Toast.makeText(context, "No area found", Toast.LENGTH_SHORT).show()
        }

        is AreaListState.Error -> {
            Log.e(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaList Error: $result")
            Toast.makeText(context, "Unable to load area list", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun LoadAreaWiseMealList(
    viewModel: CountryScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    mealLazyListState: LazyGridState = rememberLazyGridState(),
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
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
            }, mealLazyListState, hideBottomNav = hideBottomNav, onScrollEvent = onScrollEvent)
        }

        is MealListState.Empty -> {
            Log.d(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Empty: ")
            Toast.makeText(context, "No country meal found", Toast.LENGTH_SHORT).show()
        }

        is MealListState.Error -> {
            Log.e(Constants.COUNTRY_MEAL_SCREEN_TAG, "LoadAreaWiseMealList Error: $result")
            Toast.makeText(context, "Unable to load area wise meal list", Toast.LENGTH_SHORT).show()
        }
    }
}