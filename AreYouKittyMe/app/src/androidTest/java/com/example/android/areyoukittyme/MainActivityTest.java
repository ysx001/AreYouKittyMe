package com.example.android.areyoukittyme;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<AdoptActivity> mActivityTestRule = new ActivityTestRule<>(AdoptActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.cat_name_txt), isDisplayed()));
        appCompatEditText.perform(replaceText("K"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.cat_name_txt), withText("K"), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.cat_name_txt), withText("K"), isDisplayed()));
        appCompatEditText3.perform(replaceText("Kitty"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.cat_name_txt), withText("Kitty"), isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.cat_name_btn), withText("ADOPT"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.drawerToggler),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.material_drawer_account_header_name), withText("Kitty"),
                        childAtPosition(
                                allOf(withId(R.id.material_drawer_account_header_text_section),
                                        childAtPosition(
                                                withId(R.id.material_drawer_account_header),
                                                2)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Kitty")));


        ViewInteraction frameLayout = onView(
                allOf(withClassName(is("android.widget.FrameLayout")), isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.monthWeekButton), withText("Month/Week"), isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.drawerToggler),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.confirmButton), withText("Confirm"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.vocab_study_button2), withText("Study"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.FirstOption_button), withText("\tnothing"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.Next_button), withText("Next"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.FourthOption_button4), withText("\tto  worry"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.ThirdOption_button3), withText("\tto help"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.Known_button), withText("Know"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.NotKnown_button), withText("Do not know"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton10.perform(click());

        pressBack();

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.vocab_Review_button), withText("Review"), isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.FirstOption_button), withText("\tD\u008Efinition"), isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.ThirdOption_button3), withText("\tdriver"), isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.Known_button), withText("Know"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.NotKnown_button), withText("Do not know"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.Next_button), withText("Next"),
                        withParent(withId(R.id.linearLayout)),
                        isDisplayed()));
        appCompatButton16.perform(click());

        pressBack();

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.vocabulary_button), withText("Vocabulary"), isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.title), withText("Studied"), isDisplayed()));
        textView3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.title), withText("Studying"), isDisplayed()));
        textView4.perform(click());

        pressBack();

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.drawerToggler),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(6, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.timerStartBtn), withText("START"), isDisplayed()));
        appCompatButton18.perform(click());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.timerPauseBtn), withText("PAUSE"), isDisplayed()));
        appCompatButton19.perform(click());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(android.R.id.button3), withText("Got it!")));
        appCompatButton20.perform(scrollTo(), click());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.timerPauseBtn), withText("RESUME"), isDisplayed()));
        appCompatButton21.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.timerCancelBtn), withText("CANCEL"), isDisplayed()));
        appCompatButton22.perform(click());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(android.R.id.button2), withText("NO")));
        appCompatButton23.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.timerCancelBtn), withText("CANCEL"), isDisplayed()));
        appCompatButton24.perform(click());

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(android.R.id.button1), withText("YES")));
        appCompatButton25.perform(scrollTo(), click());

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(android.R.id.button3), withText("Dismiss")));
        appCompatButton26.perform(scrollTo(), click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
