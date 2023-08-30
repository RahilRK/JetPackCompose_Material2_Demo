package com.example.jetpackcompose_material2_demo.mealAppUi.home

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.CategoryListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "HomeScreenViewModel"

    private var _categoryList = MutableStateFlow<CategoryListState>(CategoryListState.Loading)
    val categoryList: StateFlow<CategoryListState>
        get() = _categoryList

    private var _mealList = MutableStateFlow<MealListState>(MealListState.Loading)
    val mealList: StateFlow<MealListState>
        get() = _mealList

    init {
        getCategoryList()
    }

    private fun getCategoryList() = viewModelScope.launch(Dispatchers.IO) {

        val response = repository.getCategoryList()

        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.categories.isNotEmpty()) {

                    val list = result.categories.toMutableList()

                    selectCategory(0, list[0].copy(isSelected = true), list)
                    getMealList(list[0].strCategory)
                    _categoryList.value = CategoryListState.Success(list.toMutableStateList())
                } else {

                    _categoryList.value = CategoryListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _categoryList.value = CategoryListState.Error(error)
            }
        }
        else {
            val error = response.errorBody()?.charStream().toString()
            _categoryList.value = CategoryListState.Error(error)
        }
    }

    fun selectCategory(pos: Int, model: Category, list: MutableList<Category>) {
        for (mModel in list) {
            if (mModel.isSelected) {
                mModel.isSelected = false
            }
        }

        list[pos] = model
        _categoryList.value = CategoryListState.Success(list.toMutableStateList())
        getMealList(model.strCategory)
    }

    private fun getMealList(strCategory: String) = viewModelScope.launch(Dispatchers.IO) {

        val response = repository.getMealList(strCategory)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()

                    _mealList.value = MealListState.Success(list.toMutableStateList())
                } else {

                    _mealList.value = MealListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _mealList.value = MealListState.Error(error)
            }
        }
        else {
            val error = response.errorBody()?.charStream().toString()
            _mealList.value = MealListState.Error(error)
        }
    }

}