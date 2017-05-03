package com.example.android.areyoukittyme;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.example.android.areyoukittyme.User.User;


/**
 * Created by sarah on 4/28/17.
 */


/**
 * JUnit4 unit tests for the calculator logic.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserTest {

    private User mUser;

    @Before
    public void setUp() {
        mUser = new User("Kitty");
    }

    @Test
    public void incrementMoodTest() {
        assertThat(mUser.getMood(), is(equalTo(90)));
        int moodPrev = mUser.getMood();
        mUser.setMood(10);
        assertThat(mUser.getMood(), is(equalTo(moodPrev + 10)));
    }

    @Test
    public void incrementHealthTest() {
        assertThat(mUser.getHealth(), is(equalTo(80)));
        int healthPrev = mUser.getHealth();
        mUser.setHealth(10);
        assertThat(mUser.getHealth(), is(equalTo(healthPrev + 10)));
    }

    @Test
    public void incrementFullMoodTest() {
        int moodPrev = User.MOOD_MAX;
        mUser.setMood(moodPrev);
        assertThat(mUser.getMood(), is(equalTo(User.MOOD_MAX)));
    }

    @Test
    public void incrementFullHealthTest() {
        int healthPrev = User.HEALTH_MAX;
        mUser.setHealth(healthPrev);
        assertThat(mUser.getHealth(), is(equalTo(User.HEALTH_MAX)));
    }

    @Test
    public void newDayHighMoodTest() {
        // Increment the mood and health to full value
        mUser.setMood(User.MOOD_MAX);
        mUser.setHealth(User.HEALTH_MAX);
        mUser.newDay();
        assertThat(mUser.getMood(), is(equalTo(100 - 10)));
        assertThat(mUser.getHealth(), is(equalTo(100 - 20)));
    }

    @Test
    public void newDayLowMoodTest() {

        mUser.setMood(-50);
        mUser.setHealth(-20);
        int moodPrev = mUser.getMood();
        int healthPrev = mUser.getHealth();
        mUser.newDay();
        assertThat(mUser.getMood(), is(equalTo(moodPrev - 10)));
        assertThat(mUser.getHealth(), is(equalTo(healthPrev - 30)));
    }


}