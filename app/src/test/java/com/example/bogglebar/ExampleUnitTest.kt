package com.example.bogglebar

import org.junit.Assert.*
import org.junit.Test


class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun boggleFragment_initializationTest() {
        val boggleFragment = BoggleFragment()
        assertNotNull(boggleFragment)
    }

    @Test
    fun scoreFragment_initializationTest() {
        val scoreFragment = ScoreFragment()
        assertNotNull(scoreFragment)
    }


}
