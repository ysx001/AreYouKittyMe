package com.example.android.areyoukittyme;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdoptActivityTest {

    @Rule
    public ActivityTestRule<AdoptActivity> mActivityTestRule = new ActivityTestRule<>(AdoptActivity.class);

    @Test
    public void emptyNameTest() {

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.cat_name_btn), withText("ADOPT"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button3), withText("Got it!")));
        appCompatButton2.perform(scrollTo(), click());

    }

    @Test
    public void enterNameText() {

        onView(allOf(withId(R.id.cat_name_txt), isDisplayed())).perform(typeText("Kitty"), closeSoftKeyboard());

        onView(allOf(withId(R.id.cat_name_txt), withText("Kitty"), isDisplayed())).perform(pressImeActionButton());

    }


    }

