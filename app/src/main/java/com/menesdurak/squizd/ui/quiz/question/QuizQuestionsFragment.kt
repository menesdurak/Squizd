package com.menesdurak.squizd.ui.quiz.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.menesdurak.squizd.data.model.Word
import com.menesdurak.squizd.data.local.WordDatabase
import com.menesdurak.squizd.databinding.FragmentQuizQuestionsBinding
import com.menesdurak.squizd.repository.WordRepository
import com.menesdurak.squizd.ui.viewmodel.WordViewModel
import com.menesdurak.squizd.ui.viewmodel.WordViewModelFactory
import kotlin.random.Random

class QuizQuestionsFragment : Fragment() {

    private var _binding: FragmentQuizQuestionsBinding? = null
    private val binding get() = _binding!!
    private var passedQuestionCount = 0
    private val QUESTION_COUNT = 5
    private var totalScore = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizQuestionsBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().onBackPressedDispatcher.addCallback(this) {

        }

        val wordDao = WordDatabase.getDatabase(requireContext()).getWordDao()
        val repository = WordRepository(wordDao)
        val viewModel: WordViewModel by viewModels {
            WordViewModelFactory(repository)
        }

        passedQuestionCount = 0
        totalScore = 0

        viewModel.wordlist.observe(viewLifecycleOwner) {
            val randomIntArray = setRandomIntArray(it)
            val word1: List<String> = listOf(it[randomIntArray[0]].name, it[randomIntArray[0]].meaning)
            val word2: List<String> = listOf(it[randomIntArray[1]].name, it[randomIntArray[1]].meaning)
            val word3: List<String> = listOf(it[randomIntArray[2]].name, it[randomIntArray[2]].meaning)
            val word4: List<String> = listOf(it[randomIntArray[3]].name, it[randomIntArray[3]].meaning)
            val word5: List<String> = listOf(it[randomIntArray[4]].name, it[randomIntArray[4]].meaning)

            val questionList: List<String> = listOf(word1[0], word2[0], word3[0], word4[0], word5[0])
            val rightAnswerList: List<String> = listOf(word1[1], word2[1], word3[1], word4[1], word5[1])

            var answer1: List<String> = listOf(word1[1], word2[1], word3[1], word4[1])
            answer1 = answer1.shuffled()
            var answer2: List<String> = listOf(word2[1], word3[1], word1[1], word5[1])
            answer2 = answer2.shuffled()
            var answer3: List<String> = listOf(word3[1], word2[1], word1[1], word4[1])
            answer3 = answer3.shuffled()
            var answer4: List<String> = listOf(word4[1], word1[1], word5[1], word2[1])
            answer4 = answer4.shuffled()
            var answer5: List<String> = listOf(word5[1], word4[1], word3[1], word1[1])
            answer5 = answer5.shuffled()
            val answersList: List<List<String>> = listOf(answer1, answer2, answer3, answer4, answer5)

            //Setted up first question
            setQuestion(questionList[passedQuestionCount],
                answersList[passedQuestionCount])

            binding.textAnswer1.setOnClickListener {
                if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer1.text.toString()) {
                    totalScore++
                }
                if (passedQuestionCount < QUESTION_COUNT) {
                    setQuestion(questionList[passedQuestionCount],
                        answersList[passedQuestionCount])
                } else {
                    passedQuestionCount--
                    if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer1.text.toString()) {
                        totalScore++
                    }
                    val action = QuizQuestionsFragmentDirections
                        .actionQuizQuestionsFragmentToQuizEndFragment(totalScore, QUESTION_COUNT)
                    findNavController().navigate(action)
                }
            }
            binding.textAnswer2.setOnClickListener {
                if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer2.text.toString()) {
                    totalScore++
                }
                if (passedQuestionCount < QUESTION_COUNT) {
                    setQuestion(questionList[passedQuestionCount],
                        answersList[passedQuestionCount])
                } else {
                    passedQuestionCount--
                    if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer2.text.toString()) {
                        totalScore++
                    }
                    val action = QuizQuestionsFragmentDirections
                        .actionQuizQuestionsFragmentToQuizEndFragment(totalScore, QUESTION_COUNT)
                    findNavController().navigate(action)
                }
            }
            binding.textAnswer3.setOnClickListener {
                if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer3.text.toString()) {
                    totalScore++
                }
                if (passedQuestionCount < QUESTION_COUNT) {
                    setQuestion(questionList[passedQuestionCount],
                        answersList[passedQuestionCount])
                } else {
                    passedQuestionCount--
                    if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer3.text.toString()) {
                        totalScore++
                    }
                    val action = QuizQuestionsFragmentDirections
                        .actionQuizQuestionsFragmentToQuizEndFragment(totalScore, QUESTION_COUNT)
                    findNavController().navigate(action)
                }
            }
            binding.textAnswer4.setOnClickListener {
                if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer4.text.toString()) {
                    totalScore++
                }
                if (passedQuestionCount < QUESTION_COUNT) {
                    setQuestion(questionList[passedQuestionCount],
                        answersList[passedQuestionCount])
                } else {
                    passedQuestionCount--
                    if (rightAnswerList[passedQuestionCount-1] == binding.textAnswer4.text.toString()) {
                        totalScore++
                    }
                    val action = QuizQuestionsFragmentDirections
                        .actionQuizQuestionsFragmentToQuizEndFragment(totalScore, QUESTION_COUNT)
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }

    private fun setQuestion(question: String, answersList: List<String>) {
        binding.textQuestion.text = question
        binding.textAnswer1.text = answersList[0]
        binding.textAnswer2.text = answersList[1]
        binding.textAnswer3.text = answersList[2]
        binding.textAnswer4.text = answersList[3]
        passedQuestionCount++
    }

    private fun setRandomIntArray(list: List<Word>): List<Int> {
        val intList = mutableListOf<Int>()

        while (intList.size < QUESTION_COUNT) {
            val randomInt = Random.nextInt(0, list.size)
            if (!intList.contains(randomInt))
                intList.add(randomInt)
        }
        return intList
    }
}