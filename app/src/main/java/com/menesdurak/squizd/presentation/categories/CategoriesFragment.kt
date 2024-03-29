package com.menesdurak.squizd.presentation.categories

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.databinding.FragmentCategoriesBinding
import com.menesdurak.squizd.presentation.words.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val wordsViewModel: WordsViewModel by viewModels()
    private val categoryAdapter by lazy {
        CategoryAdapter(
            ::onItemClick,
            ::onEditClick,
            ::onDeleteClick
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesViewModel.getAllCategories()

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = categoryAdapter
        }

        binding.fabAddCategory.setOnClickListener {
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToAddOrEditCategoryFragment()
            findNavController().navigate(action)
        }

        categoriesViewModel.categoriesList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    categoryAdapter.updateCategoryList(it.data)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(categoryId: Int) {
        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToWordsFragment(categoryId)
        findNavController().navigate(action)
    }

    private fun onEditClick(categoryName: String, categoryId: Int) {
        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToAddOrEditCategoryFragment(
                categoryId = categoryId,
                categoryName = categoryName
            )
        findNavController().navigate(action)
    }

    private fun onDeleteClick(position: Int, category: Category) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you want to delete ${category.categoryName}?")
            .setPositiveButton("Yes") { _, _ ->
                categoryAdapter.deleteItem(position, category)
                wordsViewModel.deleteAllWordsFromCategory(category.categoryId)
                categoriesViewModel.deleteCategory(category)
            }
            .setNegativeButton("No") { _, _ ->

            }
        builder.create()
        builder.show()
    }

}