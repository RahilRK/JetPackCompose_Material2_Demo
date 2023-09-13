package com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail

import android.util.Log
import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealX
import com.example.jetpackcompose_material2_demo.mealAppUi.component.MealDetailScreenContent
import com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail.state.MealDetailState
import com.example.jetpackcompose_material2_demo.util.Constants

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MealDetailScreen(onBackPress: () -> Unit = {}) {

    val context = LocalContext.current
    val viewModel: MealDetailScreenViewModel = hiltViewModel()
    val mLoadingDialogueState by viewModel.loadingDialogueState.collectAsState()
    val mMealDetailState =
        viewModel.mealDetail.collectAsState(initial = MealDetailState.Loading)

    when (val result = mMealDetailState.value) {
        MealDetailState.Loading -> {
            Log.d(Constants.MEAL_DETAIL_SCREEN_TAG, "LoadMealDetail Loading...")
            MealDetailScreenContent(onBackPress = onBackPress, mLoadingDialogueState, MealX())
        }

        is MealDetailState.Success -> {
            val getMealDetail : MealX = result.list[0]
            Log.d(Constants.MEAL_DETAIL_SCREEN_TAG, "LoadMealDetail Success: $getMealDetail")
            MealDetailScreenContent(onBackPress = onBackPress, mLoadingDialogueState, getMealDetail)
        }

        is MealDetailState.Empty -> {
            Log.d(Constants.MEAL_DETAIL_SCREEN_TAG, "LoadMealDetail Empty: ")
            Toast.makeText(context, "No meal detail found", Toast.LENGTH_SHORT).show()
        }

        is MealDetailState.Error -> {
            Log.e(Constants.MEAL_DETAIL_SCREEN_TAG, "LoadMealDetail Error: $result")
            Toast.makeText(context, "Unable to load category", Toast.LENGTH_SHORT).show()
        }
    }
}