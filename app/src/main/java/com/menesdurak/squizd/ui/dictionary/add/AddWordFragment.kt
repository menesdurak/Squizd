package com.menesdurak.squizd.ui.dictionary.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.model.Word
import com.menesdurak.squizd.data.local.WordDatabase
import com.menesdurak.squizd.databinding.FragmentAddWordBinding
import com.menesdurak.squizd.repository.WordRepository
import com.menesdurak.squizd.ui.viewmodel.WordViewModel
import com.menesdurak.squizd.ui.viewmodel.WordViewModelFactory

class AddWordFragment : Fragment() {

    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddWordBinding.inflate(inflater, container, false)
        val view = binding.root

        val wordDao = WordDatabase.getDatabase(requireContext()).getWordDao()
        val repository = WordRepository(wordDao)
        val viewModel: WordViewModel by viewModels {
            WordViewModelFactory(repository)
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.etAddName.text.toString()
            val meaning = binding.etAddMeaning.text.toString()
            val word = Word(name, meaning)
            viewModel.addWord(word)
            findNavController().navigate(R.id.dictionaryFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}