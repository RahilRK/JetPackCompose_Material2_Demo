package com.example.jetpackcompose_material2_demo.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import com.example.jetpackcompose_material2_demo.ui.home.searchView.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private var _list = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val list: StateFlow<HomeViewState>
        get() = _list

    private val _searchViewState: MutableState<SearchViewState> =
        mutableStateOf(value = SearchViewState.CLOSED)
    val searchViewState: State<SearchViewState> = _searchViewState

    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    val tagChipList = mutableStateListOf(
        DropDownCategoryModel(imageVector = Icons.Outlined.List, title = "All"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Person, title = "Miscellaneous"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Timelapse, title = "To-do"),
        DropDownCategoryModel(imageVector = Icons.Outlined.StarOutline, title = "Important"),
        DropDownCategoryModel(imageVector = Icons.Outlined.WorkOutline, title = "Work")
    )

    private val _selectedTagChip = mutableStateOf("Other")
    val selectedTagChip
        get() = _selectedTagChip

    private val _totalNoteCount = mutableStateOf(0)
    val totalNoteCount
        get() = _totalNoteCount

    private val _isAnyNoteSelected = mutableStateOf(false)
    val isAnyNoteSelected
        get() = _isAnyNoteSelected

    private val _isAllNoteSelected = mutableStateOf(false)
    val isAllNoteSelected
        get() = _isAllNoteSelected

    private val _selectedNoteCounter = mutableStateOf(0)
    val selectedNoteCounter
        get() = _selectedNoteCounter

    private val _deleteDone = mutableStateOf(false)
    val deleteDone
        get() = _deleteDone

    init {
        selectTagChip(position = 0, model = tagChipList[0].copy(isSelected = true))
    }

    private fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllNotes().distinctUntilChanged().collect { result ->
            Log.d("HomeViewModel", "getAllNotes:")
            withContext(Dispatchers.Main) {
                try {
                    if (result.isEmpty()) {

                        _list.value = HomeViewState.Empty
                        _totalNoteCount.value = 0

                        if(_deleteDone.value) {
                            cc()
                        }
                    } else {

                        _list.value = HomeViewState.Success(result.toMutableList(), "getAllNotes")
                        _totalNoteCount.value = result.toMutableList().size
                        if(_deleteDone.value) {
                            cc()
                        }
                    }
                } catch (e: Exception) {

                    _list.value = HomeViewState.Error(e)
                    _totalNoteCount.value = 0
                }
            }
        }
    }

    private fun getSearchNotes(keyWord: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("HomeViewModel", "getSearchNotes:")
        repository.getSearchNotes(keyWord).distinctUntilChanged().collect { result ->
            withContext(Dispatchers.Main) {
                try {
                    if (result.isEmpty()) {
                        _list.value = HomeViewState.Empty
                    } else {
                        _list.value =
                            HomeViewState.Success(result.toMutableList(), "getSearchNotes")
                    }
                } catch (e: Exception) {
                    _list.value = HomeViewState.Error(e)
                }
            }

        }
    }

    private fun getNotesByTag(mTag: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getNotesByTag(mTag).distinctUntilChanged().collect { result ->
//            if(_selectedTagChip.value != "") {
                Log.d("HomeViewModel", "getNotesByTag: $mTag")
                withContext(Dispatchers.Main) {
                    try {
                        if (result.isEmpty()) {
                            _list.value = HomeViewState.Empty
                        } else {
                            withContext(Dispatchers.Main) {
                                // Display the data...
                                _list.value =
                                    HomeViewState.Success(result.toMutableList(), "getNotesByTag")
                            }
                        }
                    } catch (e: Exception) {
                        _list.value = HomeViewState.Error(e)
                    }
                }
//            }
        }
    }

    private fun getFilteredNotes(keyWord: String, mTag: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilteredNotes(keyWord, mTag).distinctUntilChanged().collect { result ->
                Log.d("HomeViewModel", "getFilteredNotes:")
                withContext(Dispatchers.Main) {
                    try {
                        if (result.isEmpty()) {
                            _list.value = HomeViewState.Empty
                        } else {
                            withContext(Dispatchers.Main) {
                                // Display the data...
                                _list.value = HomeViewState.Success(
                                    result.toMutableList(),
                                    "getFilteredNotes"
                                )
                            }
                        }
                    } catch (e: Exception) {
                        _list.value = HomeViewState.Error(e)
                    }
                }
            }
        }

    fun deleteNote(id: Int): Flow<Int> =
        flow {
            //do long work
            val id = repository.deleteNews(id)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun updateSearchViewState(newValue: SearchViewState) {
        _searchViewState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
        if (_selectedTagChip.value == "") {
            if (newValue.isNotEmpty()) {
                getSearchNotes(_searchTextState.value)
            } else {
                getAllNotes()
            }
        } else {
            //todo handle both search + filter tag
            getFilteredNotes(_searchTextState.value, _selectedTagChip.value)
        }
    }

    fun selectTagChip(position: Int, model: DropDownCategoryModel) {
        for ((i, getModel) in tagChipList.withIndex()) {
            if (getModel.isSelected) {
                tagChipList[i].isSelected = false
            }
        }
        tagChipList[position] = model
        selectedTagChip.value = if (position > 0) {
            model.title
        } else {
            ""
        }

        if (_searchTextState.value.isEmpty()) {
            if (position == 0) {
                getAllNotes()
            } else {
                getNotesByTag(_selectedTagChip.value)
            }
        } else {
            //todo handle both search + filter tag
            if (position == 0) {
                getSearchNotes(_searchTextState.value)
            } else {
                getFilteredNotes(_searchTextState.value, _selectedTagChip.value)
            }
        }
    }

    fun updateNote(noteModel: NoteModel): Flow<Long> =
        flow {
            //do long work
            val id = repository.updateNote(noteModel)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun updateIsAnyNoteSelectedValue(newValue: Boolean) {
        _isAnyNoteSelected.value = newValue
    }

    fun updateSelectedNoteCounterValue(newValue: Int) {
        _selectedNoteCounter.value = newValue
    }

    fun updateIsAllNoteSelectedValue(newValue: Boolean) {
        _isAllNoteSelected.value = newValue
    }

    fun deleteMultipleNotes(value: Boolean): Flow<Int> =
        flow {
            //do long work
            val id = repository.deleteMultipleNotes(value)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun updateDeleteDoneValue(newValue: Boolean) {
        _deleteDone.value = newValue
    }

    private fun cc() {
        updateSelectedNoteCounterValue(0)
        updateIsAnyNoteSelectedValue(false)
        updateDeleteDoneValue(false)
    }
}