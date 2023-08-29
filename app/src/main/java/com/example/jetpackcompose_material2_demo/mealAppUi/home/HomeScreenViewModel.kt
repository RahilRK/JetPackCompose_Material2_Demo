package com.example.jetpackcompose_material2_demo.mealAppUi.home

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
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

    private var _categoryList = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val categoryList: StateFlow<HomeScreenState>
        get() = _categoryList

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {

        val response = repository.getCategoryList()

        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.categories.isNotEmpty()) {

                    val list = result.categories.toMutableList()

                    update(0, list[0].copy(isSelected = true), list)
                    _categoryList.value = HomeScreenState.Success(list.toMutableStateList())
                } else {

                    _categoryList.value = HomeScreenState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _categoryList.value = HomeScreenState.Error(error)
            }
        }
    }

    fun update(pos: Int, model: Category, list: MutableList<Category>) {
        for (mModel in list) {
            if (mModel.isSelected) {
                mModel.isSelected = false
            }
        }

        list[pos] = model
        _categoryList.value = HomeScreenState.Success(list.toMutableStateList())
    }
}