package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Hamburger;
import com.example.android.areyoukittyme.MainActivity;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class HamburgerFragment extends Fragment {
    private static ViewGroup rootView;
    private User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUser = ((MainActivity) getActivity()).getmUser();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_hamburger, container, false);
        TextView text = (TextView)rootView.findViewById(R.id.hamburgerAmount);
        rootView.findViewById(R.id.hamburgerImage).setOnClickListener(new MyClickListener());



        if (mUser.getInventoryList().containsKey(Hamburger.getIndex())) {
            text.setText(String.format("x%d", mUser.getInventoryAmount(Hamburger.getIndex())));
        }
        else {
            text.setText("nokey");
        }

        return rootView;
    }

    private final class MyClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (mUser.getInventoryAmount(Hamburger.getIndex()) > 0) {
                RelativeLayout p = (RelativeLayout) rootView.getParent().getParent();
                ViewPager vp = (ViewPager) rootView.getParent();

                TextView text = (TextView) rootView.findViewById(R.id.hamburgerAmount);
                text.setText(String.format("x%d", mUser.getInventoryAmount(Hamburger.getIndex()) - 1));

                User mUser = ((MainActivity) getActivity()).getmUser();
                CircularProgressBar healthProgress = ((MainActivity) getActivity()).getHealthProgress();
                CircularProgressBar moodProgress = ((MainActivity) getActivity()).getMoodProgress();

                int prevAmount = mUser.getInventoryAmount(Hamburger.getIndex());
                mUser.getInventoryList().put(Hamburger.getIndex(), prevAmount - 1);

                mUser.incrementMood(mUser.foodToMoodConversion(vp.getCurrentItem()));
                System.out.println("now is: " + mUser.getHealth());
                mUser.incrementHealth(mUser.foodToHealthConversion(vp.getCurrentItem()));
                healthProgress.setProgressWithAnimation(mUser.getHealth());
                moodProgress.setProgressWithAnimation(mUser.getMood());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView text = (TextView)rootView.findViewById(R.id.hamburgerAmount);
        text.setText(String.format("x%d", mUser.getInventoryAmount(Hamburger.getIndex())));
    }
}