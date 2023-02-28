package com.example.bogglebar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.abs
import java.util.*

class BoggleFragment : Fragment() {

    private lateinit var letterButtons: Array<Array<Button>>
    private lateinit var usedLetters: MutableList<Pair<Int, Int>>
    private lateinit var currentWord: StringBuilder
    private lateinit var grid: Array<Array<String>>
    private var listener: OnWordSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_boggle, container, false)

        // Initialize the letter buttons
        letterButtons = Array(4) { i ->
            Array(4) { j ->
                //val button = view.findViewById<Button>(resources.getIdentifier("button${i}${j}", "id", activity?.packageName))
                val button = when (i * 4 + j) {
                    0 -> view.findViewById<Button>(R.id.button00)
                    1 -> view.findViewById<Button>(R.id.button01)
                    2 -> view.findViewById<Button>(R.id.button02)
                    3 -> view.findViewById<Button>(R.id.button03)
                    4 -> view.findViewById<Button>(R.id.button04)
                    5 -> view.findViewById<Button>(R.id.button05)
                    6 -> view.findViewById<Button>(R.id.button06)
                    7 -> view.findViewById<Button>(R.id.button07)
                    8 -> view.findViewById<Button>(R.id.button08)
                    9 -> view.findViewById<Button>(R.id.button09)
                    10 -> view.findViewById<Button>(R.id.button10)
                    11 -> view.findViewById<Button>(R.id.button11)
                    12 -> view.findViewById<Button>(R.id.button12)
                    13 -> view.findViewById<Button>(R.id.button13)
                    14 -> view.findViewById<Button>(R.id.button14)
                    15 -> view.findViewById<Button>(R.id.button15)
                    else -> view.findViewById<Button>(R.id.button15)
                }

                button.setOnClickListener { onLetterSelected(i, j) }
                button
            }
        }

        // Generate a random set of letters for the game grid
        val letters = generateLetters()
        val rows = 4
        val cols = 4
        grid = Array(rows) { Array(cols) { "" } }

        usedLetters = mutableListOf()
        currentWord = StringBuilder()

        for (i in 0 until 4) {
            for (j in 0 until 4) {
                letterButtons[i][j].text = letters[i * 4 + j].toString()
                grid[i][j] = letters[i * 4 + j].toString()
            }
        }

        // Configure the clear and submit buttons
        val clearButton = view.findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener { clearSelection() }

        val submitButton = view.findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener { submitSelection() }

        return view
    }

    private fun clearSelection() {
        // Clear the current word
        currentWord.clear()
        usedLetters.clear()

        // Enable all letter buttons and reset their backgrounds
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                letterButtons[i][j].isEnabled = true
                letterButtons[i][j].background = requireContext().getDrawable(R.drawable.letter_button_default)
            }
        }

        // Update the current word display in the UI
        updateCurrentWordDisplay()
    }

    private fun submitSelection() {
        // Check if the current word is valid
        if (currentWord.length >= 4 && currentWord.count { it in "aeiouAEIOU" } >= 2) {
            listener?.onWordSelected(currentWord.toString())
        }

        // Clear the current selection
        clearSelection()
    }

    private fun generateLetters(): List<Char> {
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        val consonants = "BCDFGHJKLMNPQRSTVWXYZ".toList()
        val random = Random()

        // Generate a list of 9 consonants and 7 vowels
        val letters = mutableListOf<Char>()
        repeat(9) { letters.add(consonants[random.nextInt(consonants.size)]) }
        repeat(7) { letters.add(vowels[random.nextInt(vowels.size)]) }

        // Shuffle the letters and return the list
        return letters.shuffled()
    }
    interface OnWordSelectedListener {
        fun onWordSelected(word: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWordSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnWordSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onLetterSelected(row: Int, col: Int) {
        // Check if the letter has already been used in the current word
        if (usedLetters.contains(Pair(row, col))) {
            // Letter has already been used, show a toast message to the user
            Toast.makeText(requireContext(), "You already selected this letter", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if this is the first letter in the word
        if (currentWord.isEmpty()) {
            // This is the first letter, add it to the current word and mark it as used
            currentWord.append(grid[row][col])
            usedLetters.add(Pair(row, col))

            // Update UI to show the selected letter as active
            letterButtons[row][col].isEnabled = false
            letterButtons[row][col].background = requireContext().getDrawable(R.drawable.letter_button_selected)

            // Update the current word display in the UI
            updateCurrentWordDisplay()
            return
        }

        // Check if the selected letter is adjacent to the previous letter in the word
        val prevLetterRow = usedLetters.last().first
        val prevLetterCol = usedLetters.last().second
        val isAdjacent = abs(prevLetterRow - row) <= 1 && abs(prevLetterCol - col) <= 1

        if (!isAdjacent) {
            // Selected letter is not adjacent, show a toast message to the user
            Toast.makeText(requireContext(), "Selected letter must be adjacent to the previous letter", Toast.LENGTH_SHORT).show()
            return
        }

        // This is a valid selection, add the letter to the current word and mark it as used
        currentWord.append(grid[row][col])
        usedLetters.add(Pair(row, col))

        // Update UI to show the selected letter as active
        letterButtons[row][col].isEnabled = false
        letterButtons[row][col].background = requireContext().getDrawable(R.drawable.letter_button_selected)

        if (currentWord.length >= 4 && currentWord.count { it in "aeiouAEIOU" } >= 2) {
            listener?.onWordSelected(currentWord.toString())
        }

        // Update the current word display in the UI
        updateCurrentWordDisplay()
    }

    private fun updateCurrentWordDisplay() {
        val currentWordView = requireView().findViewById<TextView>(R.id.current_word_view)
        currentWordView.text = currentWord
    }

    fun resetSelectedWord() {
        // Clear the current word
        currentWord.clear()
        usedLetters.clear()

        // Enable all letter buttons and reset their backgrounds
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                letterButtons[i][j].isEnabled = true
                letterButtons[i][j].background = requireContext().getDrawable(R.drawable.letter_button_default)
            }
        }

        // Update the current word display in the UI
        updateCurrentWordDisplay()
    }


}
