package com.atm.android.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.atm.android.data.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNotes(notes: List<Note>)

    @Insert
    suspend fun insertNotes(notes: List<Note>)

    @Query("SELECT * FROM  Note")
    fun getAllNotesFlow(): Flow<List<Note>>
}
