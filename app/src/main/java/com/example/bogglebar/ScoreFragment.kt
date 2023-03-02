package com.example.bogglebar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ScoreFragment : Fragment() {

    lateinit var scoreTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var newGameListener: OnNewGameListener

    interface OnNewGameListener {
        fun onNewGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        scoreTextView = view.findViewById(R.id.score_text_view)
        newGameButton = view.findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener { newGameListener.onNewGame() }
        return view
    }

    fun setOnNewGameListener(listener: OnNewGameListener) {
        newGameListener = listener
    }

    fun updateScore(score: Int) {
        scoreTextView.text = "$score"
    }
}
