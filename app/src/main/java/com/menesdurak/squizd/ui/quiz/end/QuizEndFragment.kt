package com.menesdurak.squizd.ui.quiz.end

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.R
import com.menesdurak.squizd.databinding.FragmentQuizEndBinding

class QuizEndFragment : Fragment() {

    private var _binding: FragmentQuizEndBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizEndBinding.inflate(inflater, container, false)
        val view = binding.root

        val args: QuizEndFragmentArgs by navArgs()
        val score = args.totalScore.toString()
        val questionCount = args.questionCount.toString()

        binding.textQuizFinishTotalScore.text = getString(R.string.total_score_quiz_end, score, questionCount)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonQuizFinishRestart.setOnClickListener {
            findNavController().navigate(R.id.quizQuestionsFragment)
        }
    }
}