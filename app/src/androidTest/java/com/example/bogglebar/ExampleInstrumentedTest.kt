package com.example.bogglebar

import android.content.Context
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.bogglebar", appContext.packageName)
    }
    @Test
    fun testNewGameButtonResetsScoreAndClearsSelectedWord() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.new_game_button)).perform(click())
        onView(withId(R.id.score_text_view)).check(matches(withText("0")))
        onView(withId(R.id.current_word_view)).check(matches(withText("")))

        activityScenario.close()
    }
    class BoggleFragmentTest {
        @Test
        fun letterButtonTest() {
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)
            onView(withId(R.id.button00)).check(matches(isDisplayed()))
            onView(withId(R.id.button01)).check(matches(isDisplayed()))
            onView(withId(R.id.button02)).check(matches(isDisplayed()))
            onView(withId(R.id.button03)).check(matches(isDisplayed()))
            onView(withId(R.id.button10)).check(matches(isDisplayed()))
            onView(withId(R.id.button11)).check(matches(isDisplayed()))
            onView(withId(R.id.button12)).check(matches(isDisplayed()))
            onView(withId(R.id.button13)).check(matches(isDisplayed()))

            activityScenario.close()
        }
    }
}
