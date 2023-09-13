package com.example.jetpackcompose_material2_demo.mealAppUi.home

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.CategoryListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state.SearchMealListState
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "HomeScreenViewModel"

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _loadingDialogueState = MutableStateFlow(false)
    val loadingDialogueState: StateFlow<Boolean>
        get() = _loadingDialogueState.asStateFlow()

    private var _categoryList = MutableStateFlow<CategoryListState>(CategoryListState.Loading)
    val categoryList: StateFlow<CategoryListState>
        get() = _categoryList

    private var _mealList = MutableStateFlow<MealListState>(MealListState.Loading)
    val mealList: StateFlow<MealListState>
        get() = _mealList

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
        } else {
            updateLoadingDialogueState(true)
        }
        val response = repository.getCategoryList()

        if (response.isSuccessful) {
//            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
            response.body()?.let { result ->
                if (result.categories.isNotEmpty()) {

                    val list = result.categories.toMutableList()

                    selectCategory(0, list[0].copy(isSelected = true), list)
                    _categoryList.value = CategoryListState.Success(list.toMutableStateList())
                } else {

                    _categoryList.value = CategoryListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _categoryList.value = CategoryListState.Error(error)
            }
        } else {
            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
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
        getMealList(model.strCategory, _isRefreshing.value)
    }

    private fun getMealList(strCategory: String, fromIsRefreshing: Boolean = true) =
        viewModelScope.launch(Dispatchers.IO) {

            if (!fromIsRefreshing) {
                updateLoadingDialogueState(true)
            }

            val response = repository.getMealList(strCategory)

            if (response.isSuccessful) {
                updateIsRefreshValue(false)
                updateLoadingDialogueState(false)
                response.body()?.let { result ->

                    result.meals?.let { mealList ->

                        if (mealList.isNotEmpty()) {

                            val list = mealList.toMutableList()

                            _mealList.value = MealListState.Success(list.toMutableStateList())
                        } else {

                            _mealList.value = MealListState.Empty
                        }
                    } ?: kotlin.run {
                        _mealList.value = MealListState.Empty
                    }


                } ?: kotlin.run {

                    val error = response.errorBody()?.charStream().toString()
                    _mealList.value = MealListState.Error(error)
                }
            } else {
                updateIsRefreshValue(false)
                updateLoadingDialogueState(false)
                val error = response.errorBody()?.charStream().toString()
                _mealList.value = MealListState.Error(error)
            }
        }

}