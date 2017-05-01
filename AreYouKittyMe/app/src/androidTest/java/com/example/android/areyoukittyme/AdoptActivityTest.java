package com.example.android.areyoukittyme;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.example.android.areyoukittyme.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdoptActivityTest {

    @Rule
    public ActivityTestRule<AdoptActivity> mActivityTestRule = new ActivityTestRule<>(AdoptActivity.class);

    @Test
    public void adoptActivityTest() {
        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.cat_name_txt), isDisplayed()));
        appCompatEditText.perform(replaceText("Kitty"), closeSoftKeyboard());
        
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
allOf(withId(R.id.material_drawer_account_header_name), withText("Kitty"),
childAtPosition(
allOf(withId(R.id.material_drawer_account_header_text_section),
childAtPosition(
withId(R.id.material_drawer_account_header),
2)),
0),
isDisplayed()));
        textView.check(matches(withText("Kitty")));
        
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
