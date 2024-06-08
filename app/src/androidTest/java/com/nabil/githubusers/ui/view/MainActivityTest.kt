package com.nabil.githubusers.ui.view

import android.os.SystemClock
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.nabil.githubusers.R
import com.nabil.githubusers.ui.adapter.FavoriteAdapter
import com.nabil.githubusers.ui.adapter.MainAdapter
import org.junit.Rule
import org.junit.Test


@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchUser() {
        val queryUsername = "Nabil"

        onView(withId(R.id.search_view)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(queryUsername), pressKey(KeyEvent.KEYCODE_ENTER))

        SystemClock.sleep(3000)
        onView(withId(R.id.rv_user_main)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_user_main)).perform(actionOnItemAtPosition<MainAdapter.UserViewHolder>(0, click()))

        SystemClock.sleep(3000)
        onView(withId(R.id.fab_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())

        pressBack()

        onView(withId(R.id.menu_favorite)).perform(click())

        SystemClock.sleep(3000)
        onView(withId(R.id.rv_user_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_user_favorite)).perform(actionOnItemAtPosition<FavoriteAdapter.FavoriteViewHolder>(0, click()))

        SystemClock.sleep(3000)
        onView(withId(R.id.fab_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())

        onView(withText("Delete")).perform(click())

        pressBack()
        SystemClock.sleep(3000)
    }
}