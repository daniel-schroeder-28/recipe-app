package com.recipe.recipeapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FilterActivityInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.recipe.recipeapp", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<FilterActivity> activityScenarioRule = new ActivityScenarioRule<>(FilterActivity.class);

    @Test
    public void test_FilterActivity() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));

        onView(withId(R.id.checkBoxMilk)).perform(click());
        onView(withId(R.id.checkBoxMilk)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxEggs)).perform(click());
        onView(withId(R.id.checkBoxEggs)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxFish)).perform(click());
        onView(withId(R.id.checkBoxFish)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxShellfish)).perform(click());
        onView(withId(R.id.checkBoxShellfish)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTreeNuts)).perform(click());
        onView(withId(R.id.checkBoxTreeNuts)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxPeanuts)).perform(click());
        onView(withId(R.id.checkBoxPeanuts)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxWheat)).perform(click());
        onView(withId(R.id.checkBoxWheat)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxSoy)).perform(click());
        onView(withId(R.id.checkBoxSoy)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTomato)).perform(click());
        onView(withId(R.id.checkBoxTomato)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxMeat)).perform(click());
        onView(withId(R.id.checkBoxMeat)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxPoultry)).perform(click());
        onView(withId(R.id.checkBoxPoultry)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxMushroom)).perform(click());
        onView(withId(R.id.checkBoxMushroom)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxOnion)).perform(click());
        onView(withId(R.id.checkBoxOnion)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTofu)).perform(click());
        onView(withId(R.id.checkBoxTofu)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxCilantro)).perform(click());
        onView(withId(R.id.checkBoxCilantro)).check(matches(isChecked()));

        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));

        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));

        onView(withId(R.id.checkBoxMilk)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxEggs)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxFish)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxShellfish)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTreeNuts)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxPeanuts)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxWheat)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxSoy)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTomato)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxMeat)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxPoultry)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxMushroom)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxOnion)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxTofu)).check(matches(isChecked()));
        onView(withId(R.id.checkBoxCilantro)).check(matches(isChecked()));

        onView(withId(R.id.checkBoxMilk)).perform(click());
        onView(withId(R.id.checkBoxMilk)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxEggs)).perform(click());
        onView(withId(R.id.checkBoxEggs)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxFish)).perform(click());
        onView(withId(R.id.checkBoxFish)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxShellfish)).perform(click());
        onView(withId(R.id.checkBoxShellfish)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTreeNuts)).perform(click());
        onView(withId(R.id.checkBoxTreeNuts)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxPeanuts)).perform(click());
        onView(withId(R.id.checkBoxPeanuts)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxWheat)).perform(click());
        onView(withId(R.id.checkBoxWheat)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxSoy)).perform(click());
        onView(withId(R.id.checkBoxSoy)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTomato)).perform(click());
        onView(withId(R.id.checkBoxTomato)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxMeat)).perform(click());
        onView(withId(R.id.checkBoxMeat)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxPoultry)).perform(click());
        onView(withId(R.id.checkBoxPoultry)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxMushroom)).perform(click());
        onView(withId(R.id.checkBoxMushroom)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxOnion)).perform(click());
        onView(withId(R.id.checkBoxOnion)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTofu)).perform(click());
        onView(withId(R.id.checkBoxTofu)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxCilantro)).perform(click());
        onView(withId(R.id.checkBoxCilantro)).check(matches(isNotChecked()));

        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));

        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.textViewFilters)).check(matches(isDisplayed()));

        onView(withId(R.id.checkBoxMilk)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxEggs)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxFish)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxShellfish)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTreeNuts)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxPeanuts)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxWheat)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxSoy)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTomato)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxMeat)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxPoultry)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxMushroom)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxOnion)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxTofu)).check(matches(isNotChecked()));
        onView(withId(R.id.checkBoxCilantro)).check(matches(isNotChecked()));
    }
}