package com.example.jetpackcompose_material2_demo.repository

import com.example.jetpackcompose_material2_demo.data.local.NoteDao
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun saveNews(noteModel: NoteModel): Long {

        return noteDao.saveNote(noteModel)
    }

    fun getAllNotes(): Flow<List<NoteModel>> =
        noteDao.getAllNotes().flowOn(Dispatchers.IO).conflate()

    fun getSearchNotes(keyWord: String): Flow<List<NoteModel>> =
        noteDao.getSearchNotes(keyWord).flowOn(Dispatchers.IO).conflate()
    fun getNotesByTag(mTag: String): Flow<List<NoteModel>> =
        noteDao.getNotesByTag(mTag).flowOn(Dispatchers.IO).conflate()
    fun getFilteredNotes(keyWord: String, mTag: String): Flow<List<NoteModel>> =
        noteDao.getFilteredNotes(keyWord, mTag).flowOn(Dispatchers.IO).conflate()

    suspend fun deleteNews(id: Int): Int {

        return noteDao.deleteNote(id)
    }

    fun getNotesDetail(id: Int): Flow<NoteModel?> =
        noteDao.getNoteDetail(id).flowOn(Dispatchers.IO).conflate()

    suspend fun updateNote(noteModel: NoteModel): Long {

        return noteDao.updateNote(noteModel)
    }
}