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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdoptActivityTest {

    public static final String catName = "Kitty";

//    @ClassRule
//    public static DisableAnimationsRule disableAnimationsRule = new DisableAnimationsRule();

    @Rule
    public ActivityTestRule<AdoptActivity> mActivityTestRule = new ActivityTestRule<>(AdoptActivity.class);

    /**
     * Adopt a cat and test if the name in nav drawer matches the cat name entered.
     *
     * @author Sarah Xu
     */
    @Test
    public void adoptActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.cat_name_txt), isDisplayed()));
        appCompatEditText.perform(replaceText(catName), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.cat_name_btn), withText("ADOPT"), isDisplayed()));


       appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.drawerToggler),
                        withParent(allOf(withId(R.id.main_content),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.material_drawer_account_header_name), withText(catName),
                        childAtPosition(
                                allOf(withId(R.id.material_drawer_account_header_text_section),
                                        childAtPosition(
                                                withId(R.id.material_drawer_account_header),
                                                2)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText(catName)));

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
