package com.menesdurak.squizd.repository

import androidx.lifecycle.LiveData
import com.menesdurak.squizd.data.model.Word
import com.menesdurak.squizd.data.local.WordDao

class WordRepository(private val wordDao: WordDao) {

    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

    suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun updateWordWithId(id: Int, name: String, meaning: String) {
        wordDao.updateWordWithId(id, name, meaning)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun deleteWithId(id: Int) {
        wordDao.deleteWordWithId(id)
    }

    suspend fun deleteAllWords() {
        wordDao.deleteAllWords()
    }

    fun getAllWords(): LiveData<List<Word>> = wordDao.getAllWords()

    suspend fun getWordWithId(id: Int): Word = wordDao.getWordWithId(id)
}