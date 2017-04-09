package com.example.android.areyoukittyme.utilities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.areyoukittyme.DayFragment;
import com.example.android.areyoukittyme.MonthFragment;
import com.example.android.areyoukittyme.WeekFragment;

/**
 * Created by sarah on 4/9/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Day", "Week", "Month" };
    private Context context;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                // Top Rated fragment activity
                return DayFragment.newInstance(index);
            case 1:
                // Games fragment activity
                return WeekFragment.newInstance(index);
            case 2:
                // Movies fragment activity
                return MonthFragment.newInstance(index);
        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return PAGE_COUNT;
    }
}
