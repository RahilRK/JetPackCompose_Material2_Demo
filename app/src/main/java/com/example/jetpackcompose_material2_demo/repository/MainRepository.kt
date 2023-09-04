package com.example.jetpackcompose_material2_demo.repository

import com.example.jetpackcompose_material2_demo.data.local.NoteDao
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.data.remote.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val apiInterface: ApiInterface,
) {
    /*todo roomDB*/
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

    suspend fun deleteMultipleNotes(value: Boolean): Int {

        return noteDao.deleteMultipleNotes(value)
    }

    /*todo api calls*/
    suspend fun getCategoryList() = apiInterface.getCategoryList()

    suspend fun getMealList(strCategory: String) = apiInterface.getMealList(strCategory)

    suspend fun getSearchMealList(keyWord: String) = apiInterface.getSearchMealList(keyWord)

    suspend fun getAreaList() = apiInterface.getAreaList()

    suspend fun getAreaWiseMealList(area: String) = apiInterface.getAreaWiseMealList(area)

    suspend fun getIngredientList() = apiInterface.getIngredientList()

    suspend fun getIngredientWiseMealList(i: String) = apiInterface.getIngredientWiseMealList(i)

}