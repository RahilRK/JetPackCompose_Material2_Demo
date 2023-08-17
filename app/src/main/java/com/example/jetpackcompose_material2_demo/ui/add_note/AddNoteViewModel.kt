package com.example.jetpackcompose_material2_demo.ui.add_note

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose_material2_demo.data.model.ColorModel
import com.example.jetpackcompose_material2_demo.data.model.HobbyModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import com.example.jetpackcompose_material2_demo.ui.theme.blue
import com.example.jetpackcompose_material2_demo.ui.theme.green
import com.example.jetpackcompose_material2_demo.ui.theme.red
import com.example.jetpackcompose_material2_demo.ui.theme.yellow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val hobbyCheckBoxList = mutableStateListOf(
        HobbyModel(title = "Cricket", isChecked = false),
        HobbyModel(title = "Volleyball", isChecked = false),
        HobbyModel(title = "Video game", isChecked = false)
    )

    private val _selectedHobbyList = mutableStateListOf<String>()
    val selectedHobbyList
        get() = _selectedHobbyList

    private val _markAsImp = MutableStateFlow(false)
    val markAsImp = _markAsImp.asStateFlow()

    var colorList = mutableStateListOf(
        ColorModel("green", green.value, isSelected = false),
        ColorModel("blue", blue.value, isSelected = false),
        ColorModel("red", red.value, isSelected = false),
        ColorModel("yellow", yellow.value, isSelected = false),
    )

    private val _selectedColor = mutableStateOf(ColorModel())
    val selectedColor
        get() = _selectedColor

    init {
        autoSelectColor()
    }
    fun saveNote(noteModel: NoteModel): Flow<Long> =
        flow {
            //do long work
            val id = repository.saveNews(noteModel)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun addHobbyToList(hobby: String) {

        if (!_selectedHobbyList.contains(hobby)) {
            _selectedHobbyList.add(hobby)
        }
    }

    fun removeHobbyToList(hobby: String) {

        if (_selectedHobbyList.contains(hobby)) {
            _selectedHobbyList.remove(hobby)
        }
    }

    fun switchEvent(value: Boolean) {
        _markAsImp.value = value
    }

    fun changeColor(position: Int, model: ColorModel) {
        for((i, getModel) in colorList.withIndex()) {
            if(getModel.isSelected) {
                colorList[i].isSelected = false
            }

        }
        colorList[position] = model
        _selectedColor.value = model
    }

    private fun autoSelectColor(bgColor: String = "green") {
        for((i, getModel) in colorList.withIndex()) {
            if(bgColor == getModel.colorName) {
                colorList[i].isSelected = true
                _selectedColor.value = getModel
            }
        }
    }
}