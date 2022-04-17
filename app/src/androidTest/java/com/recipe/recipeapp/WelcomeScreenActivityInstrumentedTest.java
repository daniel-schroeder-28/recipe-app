package com.recipe.recipeapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WelcomeScreenActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.recipe.recipeapp", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<WelcomeScreenActivity> activityScenarioRule = new ActivityScenarioRule<>(WelcomeScreenActivity.class);

    @Test
    public void test_WelcomeScreenActivity() {
        RecipeAppGlobals.setFavoriteRecipes(new ArrayList<String>() {{
            add("Burger");
            add("Lasagna");
        }});

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

        onView(withId(R.id.recyclerViewFavoriteRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, buttonClickViewAction.clickChildViewWithId(R.id.buttonView)));
        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.welcome)).perform(click());
        onView(withId(R.id.recyclerViewFavoriteRecipes)).check(matches(isDisplayed()));
    }

    public static class buttonClickViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }
    }
}