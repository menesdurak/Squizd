package com.menesdurak.squizd.ui.dictionary.update

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.local.WordDatabase
import com.menesdurak.squizd.databinding.FragmentUpdateWordBinding
import com.menesdurak.squizd.repository.WordRepository
import com.menesdurak.squizd.ui.viewmodel.WordViewModel
import com.menesdurak.squizd.ui.viewmodel.WordViewModelFactory

class UpdateWordFragment : Fragment() {

    private var _binding: FragmentUpdateWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateWordBinding.inflate(inflater, container, false)
        val view = binding.root

        val wordDao = WordDatabase.getDatabase(requireContext()).getWordDao()
        val repository = WordRepository(wordDao)
        val viewModel: WordViewModel by viewModels {
            WordViewModelFactory(repository)
        }

        //Recieving clicked word's elements
        val args: UpdateWordFragmentArgs by navArgs()
        val id = args.uid
        val currentWordName = args.currentWord.name
        val currentWordMeaning = args.currentWord.meaning

        binding.etUpdateName.setText(currentWordName)
        binding.etUpdateMeaning.setText(currentWordMeaning)

        binding.btnUpdate.setOnClickListener {
            val newName = binding.etUpdateName.text.toString()
            val newMeaning = binding.etUpdateMeaning.text.toString()
            viewModel.updateWordWithId(id, newName, newMeaning)
            findNavController().navigate(R.id.dictionaryFragment)
        }

        viewModel.wordlist.observe(viewLifecycleOwner) { wordListV ->
            binding.btnDelete.setOnClickListener {
                if (wordListV.size > 5) {
                    val alertBuilder = AlertDialog.Builder(it.context)
                    alertBuilder.setMessage("Are you sure you want to delete the ${currentWordName}?")
                        .setPositiveButton("Yes"
                        ) { _, i ->
                            viewModel.deleteWordWithId(id)
                            findNavController().navigate(R.id.dictionaryFragment)
                        }
                        .setNegativeButton("No"
                        ) { dialogInterface, i ->
                            dialogInterface.cancel()
                        }
                    alertBuilder.show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You must have at least 6 words to delete word!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return view
    }
}