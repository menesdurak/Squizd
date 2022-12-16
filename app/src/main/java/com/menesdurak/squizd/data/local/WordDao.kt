package com.menesdurak.squizd.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.menesdurak.squizd.data.model.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Query("UPDATE words_table SET name = :name, meaning = :meaning WHERE uid = :id")
    suspend fun updateWordWithId(id: Int, name: String, meaning: String)

    @Query("SELECT * FROM words_table ORDER BY uid ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words_table WHERE uid = :id")
    suspend fun getWordWithId(id: Int): Word

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM words_table WHERE uid = :id")
    suspend fun deleteWordWithId(id: Int)

    @Query("DELETE FROM words_table")
    suspend fun deleteAllWords()
}