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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TimerActivityTest {

    @Rule
    public ActivityTestRule<TimerActivity> mActivityTestRule = new ActivityTestRule<>(TimerActivity.class);

    @Test
    public void enterTimeTest() {

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.hourEditText), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.hourEditText), isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.hourEditText), isDisplayed()));
        appCompatEditText6.perform(replaceText("20"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.hourEditText), withText("20"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.timerStartBtn), withText("START"), isDisplayed()));
        appCompatButton6.perform(click());

    }

    @Test
    public void pauseTest() {
    ViewInteraction appCompatButton7 = onView(
            allOf(withId(R.id.timerPauseBtn), withText("PAUSE"), isDisplayed()));
        appCompatButton7.perform(click());

    ViewInteraction appCompatButton8 = onView(
            allOf(withId(android.R.id.button3), withText("Got it!")));
        appCompatButton8.perform(scrollTo(), click());

    }

    @Test
    public void resumeTest() {


    ViewInteraction appCompatButton9 = onView(
            allOf(withId(R.id.timerPauseBtn), withText("RESUME"), isDisplayed()));
        appCompatButton9.perform(click());

    }

    @Test
    public void setMinuteTest() {

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.minSpinner), isDisplayed()));
        appCompatSpinner.perform(click());

    ViewInteraction appCompatCheckedTextView = onView(
            allOf(withId(android.R.id.text1), withText("30"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

    ViewInteraction appCompatButton13 = onView(
            allOf(withId(R.id.timerStartBtn), withText("START"), isDisplayed()));
        appCompatButton13.perform(click());

    }
}
