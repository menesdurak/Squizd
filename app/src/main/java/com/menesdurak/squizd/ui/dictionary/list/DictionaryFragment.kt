package com.menesdurak.squizd.ui.dictionary.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.model.Word
import com.menesdurak.squizd.data.local.WordDatabase
import com.menesdurak.squizd.databinding.FragmentDictionaryBinding
import com.menesdurak.squizd.repository.WordRepository
import com.menesdurak.squizd.ui.viewmodel.WordViewModel
import com.menesdurak.squizd.ui.viewmodel.WordViewModelFactory

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        val view = binding.root

        val wordDao = WordDatabase.getDatabase(requireContext()).getWordDao()
        val repository = WordRepository(wordDao)
        val viewModel: WordViewModel by viewModels {
            WordViewModelFactory(repository)
        }

        viewModel.wordlist.observe(viewLifecycleOwner) {
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            val wordAdapter = WordAdapter(it)
            binding.recyclerView.adapter = wordAdapter

            wordAdapter.setOnItemClickListener(object : WordAdapter.WordClickListener{
                override fun onItemClicked(position: Int) {
                    val currentWord = Word(it[position].name, it[position].meaning)
                    val argId = it[position].uid
                    val action = DictionaryFragmentDirections
                        .actionDictionaryFragmentToUpdateWordFragment(argId, currentWord)
                    findNavController().navigate(action)
                }
            })
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoToAdd.setOnClickListener {
            findNavController().navigate(R.id.addWordFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

