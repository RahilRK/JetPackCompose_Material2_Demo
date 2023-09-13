package com.example.jetpackcompose_material2_demo.mealAppUi.country_meal

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.Area
import com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.state.AreaListState
import com.example.jetpackcompose_material2_demo.mealAppUi.home.state.MealListState
import com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state.SearchMealListState
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryScreenViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val TAG = "CountryScreenViewModel"

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _loadingDialogueState = MutableStateFlow(false)
    val loadingDialogueState: StateFlow<Boolean>
        get() = _loadingDialogueState.asStateFlow()

    private var _areaList = MutableStateFlow<AreaListState>(AreaListState.Loading)
    val areaList: StateFlow<AreaListState>
        get() = _areaList

    private var _areaWiseMealList = MutableStateFlow<MealListState>(MealListState.Loading)
    val areaWiseMealList: StateFlow<MealListState>
        get() = _areaWiseMealList

    init {
        getAreaList()
    }

    fun updateIsRefreshValue(newValue: Boolean) {
        _isRefreshing.value = newValue
    }

    private fun updateLoadingDialogueState(newValue: Boolean) {
        _loadingDialogueState.value = newValue
    }

    fun getAreaList() = viewModelScope.launch(Dispatchers.IO) {

        if (_isRefreshing.value) {
            updateLoadingDialogueState(false)
        } else {
            updateLoadingDialogueState(true)
        }
        val response = repository.getAreaList()

        if (response.isSuccessful) {
            updateLoadingDialogueState(false)
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()

                    selectArea(0, list[0].copy(isSelected = true), list)
                    _areaList.value = AreaListState.Success(list.toMutableStateList())
                } else {

                    _areaList.value = AreaListState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _areaList.value = AreaListState.Error(error)
            }
        } else {
            updateIsRefreshValue(false)
            updateLoadingDialogueState(false)
            val error = response.errorBody()?.charStream().toString()
            _areaList.value = AreaListState.Error(error)
        }
    }

    fun selectArea(pos: Int, model: Area, list: MutableList<Area>) {
        for (mModel in list) {
            if (mModel.isSelected) {
                mModel.isSelected = false
            }
        }

        list[pos] = model
        _areaList.value = AreaListState.Success(list.toMutableStateList())
        getAreaWiseMealList(model.strArea, _isRefreshing.value)
    }

    private fun getAreaWiseMealList(area: String, fromIsRefreshing: Boolean = true) =
        viewModelScope.launch(Dispatchers.IO) {

            if (!fromIsRefreshing) {
                updateLoadingDialogueState(true)
            }

            val response = repository.getAreaWiseMealList(area)

            if (response.isSuccessful) {
                updateIsRefreshValue(false)
                updateLoadingDialogueState(false)
                response.body()?.let { result ->

                    result.meals?.let { mealList ->

                        if (mealList.isNotEmpty()) {

                            val list = mealList.toMutableList()

                            _areaWiseMealList.value =
                                MealListState.Success(list.toMutableStateList())
                        } else {

                            _areaWiseMealList.value = MealListState.Empty
                        }
                    } ?: kotlin.run {
                        _areaWiseMealList.value = MealListState.Empty
                    }


                } ?: kotlin.run {

                    val error = response.errorBody()?.charStream().toString()
                    _areaWiseMealList.value = MealListState.Error(error)
                }
            } else {
                updateIsRefreshValue(false)
                updateLoadingDialogueState(false)
                val error = response.errorBody()?.charStream().toString()
                _areaWiseMealList.value = MealListState.Error(error)
            }
        }

}