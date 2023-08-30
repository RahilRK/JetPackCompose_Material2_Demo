package com.example.jetpackcompose_material2_demo.mealAppUi.search_meal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state.SearchMealListState
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMealScreenViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "SearchMealScreenViewModel"

    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    private var _mealList = MutableStateFlow<SearchMealListState>(SearchMealListState.Loading)
    val mealList: StateFlow<SearchMealListState>
        get() = _mealList

    init {
        getSearchMealList()
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getSearchMealList() = viewModelScope.launch(Dispatchers.IO) {

        delay(700L)
        val response = repository.getSearchMealList(_searchTextState.value)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()

                    _mealList.value = SearchMealListState.Success(list.toMutableStateList())
                } else {

                    _mealList.value = SearchMealListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _mealList.value = SearchMealListState.Error(error)
            }
        }
        else {
            val error = response.errorBody()?.charStream().toString()
            _mealList.value = SearchMealListState.Error(error)
        }
    }

}