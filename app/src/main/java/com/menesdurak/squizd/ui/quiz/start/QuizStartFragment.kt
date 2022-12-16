package com.menesdurak.squizd.ui.quiz.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.local.WordDatabase
import com.menesdurak.squizd.databinding.FragmentQuizStartBinding
import com.menesdurak.squizd.repository.WordRepository
import com.menesdurak.squizd.ui.viewmodel.WordViewModel
import com.menesdurak.squizd.ui.viewmodel.WordViewModelFactory

class QuizStartFragment : Fragment() {

    private var _binding: FragmentQuizStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizStartBinding.inflate(inflater, container, false)

        val wordDao = WordDatabase.getDatabase(requireContext()).getWordDao()
        val repository = WordRepository(wordDao)
        val viewModel: WordViewModel by viewModels {
            WordViewModelFactory(repository)
        }

        viewModel.wordlist.observe(viewLifecycleOwner) { wordListV ->
            binding.buttonQuizStart.setOnClickListener {
                if (wordListV.size < 5) {
                    Toast.makeText(requireContext(),
                        "You must have 5 or more words to start quiz!",
                        Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.quizQuestionsFragment)
                }
            }
        }
        return binding.root
    }
}