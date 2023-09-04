package com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.Ingredient
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.state.IngredientListState
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientScreenViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "IngredientScreenViewModel"

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _loadingDialogueState = MutableStateFlow(false)
    val loadingDialogueState: StateFlow<Boolean>
        get() = _loadingDialogueState.asStateFlow()

    private var _ingredientList = MutableStateFlow<IngredientListState>(IngredientListState.Loading)
    val ingredientList: StateFlow<IngredientListState>
        get() = _ingredientList

    private var _ingredientWiseMealList = MutableStateFlow<MealListState>(MealListState.Loading)
    val ingredientWiseMealList: StateFlow<MealListState>
        get() = _ingredientWiseMealList

    init {
        getCategoryList()
    }

    fun updateIsRefreshValue(newValue: Boolean) {
        _isRefreshing.value = newValue
    }

    private fun updateLoadingDialogueState(newValue: Boolean) {
        _loadingDialogueState.value = newValue
    }
    fun getCategoryList() = viewModelScope.launch(Dispatchers.IO) {

        if (_isRefreshing.value) {
            updateLoadingDialogueState(false)
        }
        else {
            updateLoadingDialogueState(true)
        }
        val response = repository.getIngredientList()

        if (response.isSuccessful) {
            updateLoadingDialogueState(false)
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()

                    selectIngredient(0, list[0].copy(isSelected = true), list)
                    _ingredientList.value = IngredientListState.Success(list.toMutableStateList())
                } else {

                    _ingredientList.value = IngredientListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _ingredientList.value = IngredientListState.Error(error)
            }
        } else {
            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
            val error = response.errorBody()?.charStream().toString()
            _ingredientList.value = IngredientListState.Error(error)
        }
    }

    fun selectIngredient(pos: Int, model: Ingredient, list: MutableList<Ingredient>) {
        for (mModel in list) {
            if (mModel.isSelected) {
                mModel.isSelected = false
            }
        }

        list[pos] = model
        _ingredientList.value = IngredientListState.Success(list.toMutableStateList())
        getIngredientWiseMealList(model.strIngredient, _isRefreshing.value)
    }

    private fun getIngredientWiseMealList(i: String, fromIsRefreshing: Boolean = true) = viewModelScope.launch(Dispatchers.IO) {

        if(!fromIsRefreshing) {
            updateLoadingDialogueState(true)
        }

        val response = repository.getIngredientWiseMealList(i)

        if (response.isSuccessful) {
            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()

                    _ingredientWiseMealList.value = MealListState.Success(list.toMutableStateList())
                } else {

                    _ingredientWiseMealList.value = MealListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _ingredientWiseMealList.value = MealListState.Error(error)
            }
        } else {
            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
            val error = response.errorBody()?.charStream().toString()
            _ingredientWiseMealList.value = MealListState.Error(error)
        }
    }

}