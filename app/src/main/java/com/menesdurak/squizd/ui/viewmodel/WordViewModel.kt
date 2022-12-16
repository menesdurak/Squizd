package com.menesdurak.squizd.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menesdurak.squizd.data.model.Word
import com.menesdurak.squizd.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val wordlist: LiveData<List<Word>> = repository.getAllWords()

//    fun getWordWithId(id: Int): Word {
//        viewModelScope.launch {
//            val wordWithId = repository.getWordWithId(id)
//        }
//    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            repository.addWord(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.updateWord(word)
        }
    }

    fun updateWordWithId(id: Int, name: String, meaning: String) {
        viewModelScope.launch {
            repository.updateWordWithId(id, name, meaning)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }

    fun deleteWordWithId(id: Int) {
        viewModelScope.launch {
            repository.deleteWithId(id)
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            repository.deleteAllWords()
        }
    }

}