package com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal

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
import androidx.compose.material.icons.outlined.Fastfood
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
import com.example.jetpackcompose_material2_demo.mealAppUi.component.IngredientList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.IngredientWiseMealList
import com.example.jetpackcompose_material2_demo.mealAppUi.component.LoadingDialog
import com.example.jetpackcompose_material2_demo.mealAppUi.component.TopBar
import com.example.jetpackcompose_material2_demo.mealAppUi.home.Header
import com.example.jetpackcompose_material2_demo.mealAppUi.home.HomeScreenViewModel
import com.example.jetpackcompose_material2_demo.mealAppUi.home.LoadCategoryList
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.state.IngredientListState
import com.example.jetpackcompose_material2_demo.ui.theme.bg_color
import com.example.jetpackcompose_material2_demo.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun IngredientsMealScreen(
    navController: NavHostController = rememberNavController(),
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val viewModel: IngredientScreenViewModel = hiltViewModel()
    val context = LocalContext.current

    val mLoadingDialogueState by viewModel.loadingDialogueState.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(bg_color)
            .fillMaxSize(),
        topBar = {
            TopBar(
                navigationIcon = Icons.Outlined.Fastfood,
                title = "Choose ingredients based recipe"
            )
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
                        LoadIngredientList(
                            viewModel,
                            context,
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
fun LoadIngredientList(
    viewModel: IngredientScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val ingredientListState =
        viewModel.ingredientList.collectAsState(initial = IngredientListState.Loading)
    when (val result = ingredientListState.value) {
        IngredientListState.Loading -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientList Loading...")
        }

        is IngredientListState.Success -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientList Success:")

            val listState = rememberLazyListState()
            val mealLazyListState = rememberLazyGridState()

            IngredientList(
                result.list,
                onClickEvent = { pos, model ->

                    viewModel.selectIngredient(pos, model, result.list)
                },
                listState, mealLazyListState,
            )

            LoadIngredientWiseMealList(
                mealLazyListState = mealLazyListState, hideBottomNav = hideBottomNav,
                onScrollEvent = onScrollEvent
            )

            if (viewModel.isRefreshing.collectAsState().value) {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            }
        }

        is IngredientListState.Empty -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadCategoryList Empty: ")
        }

        is IngredientListState.Error -> {
            Log.e(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientList Error: $result")
            Toast.makeText(context, "Unable to load ingredient list", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun LoadIngredientWiseMealList(
    viewModel: IngredientScreenViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    mealLazyListState: LazyGridState = rememberLazyGridState(),
    hideBottomNav: Boolean = false,
    onScrollEvent: (hideBottomNav: Boolean) -> Unit = {},
) {

    val ingredientWiseMealListState =
        viewModel.ingredientWiseMealList.collectAsState(initial = MealListState.Loading)
    when (val result = ingredientWiseMealListState.value) {
        MealListState.Loading -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientWiseMealList Loading...")
        }

        is MealListState.Success -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientWiseMealList Success:")

            IngredientWiseMealList(result.list, onClickEvent = { pos, model ->
                Toast.makeText(context, model.strMeal, Toast.LENGTH_SHORT).show()
            }, mealLazyListState, hideBottomNav = hideBottomNav, onScrollEvent = onScrollEvent)
        }

        is MealListState.Empty -> {
            Log.d(Constants.INGREDIENTS_MEAL_SCREEN_TAG, "LoadIngredientWiseMealList Empty: ")
        }

        is MealListState.Error -> {
            Log.e(
                Constants.INGREDIENTS_MEAL_SCREEN_TAG,
                "LoadIngredientWiseMealList Error: $result"
            )
            Toast.makeText(context, "Unable to load ingredient wise meal list", Toast.LENGTH_SHORT)
                .show()
        }
    }
}