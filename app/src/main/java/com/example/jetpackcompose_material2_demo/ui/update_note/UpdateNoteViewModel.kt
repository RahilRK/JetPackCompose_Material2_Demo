package com.example.jetpackcompose_material2_demo.ui.update_note

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.ColorModel
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _noteModel = MutableStateFlow<UpdateNoteState>(UpdateNoteState.Loading)
    val noteModel: StateFlow<UpdateNoteState>
        get() = _noteModel

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

    val dropDownCategoryList = mutableStateListOf(
        DropDownCategoryModel(imageVector = Icons.Outlined.Person, title = "Miscellaneous"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Timelapse, title = "To-do"),
        DropDownCategoryModel(imageVector = Icons.Outlined.StarOutline, title = "Important"),
        DropDownCategoryModel(imageVector = Icons.Outlined.WorkOutline, title = "Work")
    )

    private val _isDropDownExpanded = MutableStateFlow(false)
    val isDropDownExpanded = _isDropDownExpanded.asStateFlow()

    private val _selectedDropDownItem = MutableStateFlow(DropDownCategoryModel(imageVector = Icons.Outlined.Person,"Miscellaneous"))
    val selectedDropDownItem = _selectedDropDownItem.asStateFlow()

    init {
        getNotesDetail()
    }

    private fun getNotesDetail() = viewModelScope.launch(Dispatchers.IO) {
        val id = savedStateHandle.get<String>("id") ?: "-1"
        repository.getNotesDetail(id.toInt()).distinctUntilChanged().collect { result ->
            try {
                result?.let {
                    _noteModel.value = UpdateNoteState.Success(it)
                    checkedSelectedHobbies(it)
                    switchEvent(it.isImp)
                    autoSelectColor(it.color)
                    autoSelectDropDownItem(it.tag)
                }
            } catch (e: Exception) {
                Log.e("Error", Log.getStackTraceString(e))
                _noteModel.value = UpdateNoteState.Error(e)
            }
        }
    }

    fun updateNote(noteModel: NoteModel): Flow<Long> =
        flow {
            //do long work
            val id = repository.updateNote(noteModel)
            emit(id)
        }.flowOn(Dispatchers.IO)

    private fun checkedSelectedHobbies(model: NoteModel) {
        val mList = model.hobbies
        val replace = mList.replace("[", "")
//        println(replace)
        val replace1 = replace.replace("]", "")
//        println(replace1)
        val myList: List<String> =
            ArrayList(listOf(*replace1.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()))
//        println(myList.toString())

        hobbyCheckBoxList.forEachIndexed { i, hobbyModel ->
            val allHobby = hobbyModel.title
            myList.forEachIndexed { j, hobby ->

                if (allHobby == hobby.trim()) {
                    hobbyCheckBoxList[i].isChecked = true
                }
            }
        }

        myList.forEach { hobby ->
            addHobbyToList(hobby.trim())
        }
    }

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

    private fun autoSelectColor(bgColor: String) {
        for((i, getModel) in colorList.withIndex()) {
            if(bgColor == getModel.colorName) {
                colorList[i].isSelected = true
                _selectedColor.value = getModel
            }
        }
    }

    fun deleteNote(id: Int): Flow<Int> =
        flow {
            //do long work
            val id = repository.deleteNews(id)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun changeIsDropDownExpanded(value: Boolean) {
        _isDropDownExpanded.value = value
    }
    fun changeSelectedDropDownItem(value: DropDownCategoryModel) {
        _selectedDropDownItem.value = value
    }

    private fun autoSelectDropDownItem(tag: String) {
        for((i, getModel) in dropDownCategoryList.withIndex()) {
            if(tag == getModel.title) {
                dropDownCategoryList[i].isSelected = true
                _selectedDropDownItem.value = getModel
            }
        }
    }

}