package com.example.bogglebar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), BoggleFragment.OnWordSelectedListener, ScoreFragment.OnNewGameListener {

    private lateinit var boggleFragment: BoggleFragment
    private lateinit var scoreFragment: ScoreFragment
    private lateinit var dictionary: Set<String>
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load dictionary from file or network
        dictionary = loadDictionary()

        boggleFragment = BoggleFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.boggle_container, boggleFragment)
            .commit()

        scoreFragment = ScoreFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.score_container, scoreFragment)
            .commit()

        // Set listeners on fragments
        scoreFragment.setOnNewGameListener(this)

    }

    override fun onWordSelected(word: String) {
        // Check if word is in dictionary
        if (word.length < 4 || !dictionary.contains(word.toLowerCase())) {
            // Incorrect response, deduct 10 points
            score -= 10
            scoreFragment.updateScore(score)
            Toast.makeText(this, "That's incorrect, -10", Toast.LENGTH_SHORT).show()
        } else {
            // Calculate word score and update score fragment
            var wordScore = 0
            word.forEach { c ->
                when (c.toLowerCase()) {
                    'a', 'e', 'i', 'o', 'u' -> wordScore += 5
                    's', 'z', 'p', 'x', 'q' -> wordScore++
                    else -> wordScore++
                }
            }
            if (wordScore > 0) {
                if (word.contains('s', true) || word.contains('z', true) || word.contains('p', true) || word.contains('x', true) || word.contains('q', true)) {
                    wordScore *= 2
                }
            }
            score += wordScore
            scoreFragment.updateScore(score)
            Toast.makeText(this, "That's correct, +$wordScore", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadDictionary(): Set<String> {
        val inputStream = assets.open("words.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val words = mutableSetOf<String>()
        reader.useLines { lines ->
            lines.forEach { word ->
                words.add(word.trim().toLowerCase())
            }
        }
        return words
    }

    override fun onNewGame() {
        score = 0
        scoreFragment.updateScore(0)
        boggleFragment.resetSelectedWord()
    }
}
