package com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealDetailIngredientList
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealX
import com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail.state.MealDetailState
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
class MealDetailScreenViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val TAG = "MealDetailScreenViewModel"

    private val _loadingDialogueState = MutableStateFlow(false)
    val loadingDialogueState: StateFlow<Boolean>
        get() = _loadingDialogueState.asStateFlow()

    private var _mealDetail = MutableStateFlow<MealDetailState>(MealDetailState.Loading)
    val mealDetail: StateFlow<MealDetailState>
        get() = _mealDetail

    private var _mealIngredientList = mutableStateListOf<MealDetailIngredientList>()
    val mealIngredientList
        get() = _mealIngredientList

    init {
        getMealDetail(
            savedStateHandle.get<String>("id") ?: "-1"
        )
    }

    private fun updateLoadingDialogueState(newValue: Boolean) {
        _loadingDialogueState.value = newValue
    }

    private fun getMealDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {

        updateLoadingDialogueState(true)
        delay(3000L)
        val response = repository.getMealDetail(id)

        if (response.isSuccessful) {
            updateLoadingDialogueState(false)
            response.body()?.let { result ->
                if (result.meals.isNotEmpty()) {

                    val list = result.meals.toMutableList()
                    list[0].mealIngredientList = addDataToIngredientList(list[0])

                    _mealDetail.value = MealDetailState.Success(list.toMutableStateList())
                } else {

                    _mealDetail.value = MealDetailState.Empty
                }

            } ?: kotlin.run {

                val error = response.errorBody()?.charStream().toString()
                _mealDetail.value = MealDetailState.Error(error)
            }
        } else {
            updateLoadingDialogueState(false)
            val error = response.errorBody()?.charStream().toString()
            _mealDetail.value = MealDetailState.Error(error)
        }
    }

    private fun addDataToIngredientList(model: MealX): List<MealDetailIngredientList> {

        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient1?:"", strMeasure = model.strMeasure1?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient2?:"", strMeasure = model.strMeasure2?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient3?:"", strMeasure = model.strMeasure3?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient4?:"", strMeasure = model.strMeasure4?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient5?:"", strMeasure = model.strMeasure5?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient6?:"", strMeasure = model.strMeasure6?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient7?:"", strMeasure = model.strMeasure7?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient8?:"", strMeasure = model.strMeasure8?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient9?:"", strMeasure = model.strMeasure9?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient10?:"", strMeasure = model.strMeasure10?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient11?:"", strMeasure = model.strMeasure11?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient12?:"", strMeasure = model.strMeasure12?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient13?:"", strMeasure = model.strMeasure13?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient14?:"", strMeasure = model.strMeasure14?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient15?:"", strMeasure = model.strMeasure15?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient16?:"", strMeasure = model.strMeasure16?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient17?:"", strMeasure = model.strMeasure17?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient18?:"", strMeasure = model.strMeasure18?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient19?:"", strMeasure = model.strMeasure19?:""))
        mealIngredientList.add(MealDetailIngredientList(strIngredient = model.strIngredient20?:"", strMeasure = model.strMeasure20?:""))
        return mealIngredientList
    }
}