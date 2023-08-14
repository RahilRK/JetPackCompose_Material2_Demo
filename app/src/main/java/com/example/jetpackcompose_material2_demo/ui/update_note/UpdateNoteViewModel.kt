package com.example.jetpackcompose_material2_demo.ui.update_note

import android.os.Debug
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.HobbyModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.repository.MainRepository
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
import java.util.Arrays
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

    init {
        getNotesDetail()
    }

    private fun getNotesDetail() = viewModelScope.launch(Dispatchers.IO) {
        val id = savedStateHandle.get<String>("id") ?: "-1"
        repository.getNotesDetail(id.toInt()).distinctUntilChanged().collect { result ->
            try {
                _noteModel.value = UpdateNoteState.Success(result)
                checkedSelectedHobbies(result)
                switchEvent(result.isImp)
            } catch (e: Exception) {
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
}