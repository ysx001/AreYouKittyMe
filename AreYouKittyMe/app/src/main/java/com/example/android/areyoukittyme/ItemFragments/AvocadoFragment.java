package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Avocado;
import com.example.android.areyoukittyme.MainActivity;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class AvocadoFragment extends Fragment {
    private static ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_avocado, container, false);
        TextView text = (TextView)rootView.findViewById(R.id.avocadoAmount);
        rootView.findViewById(R.id.avocadoImage).setOnClickListener(new MyClickListener());



        if (User.getInventoryList().containsKey(Avocado.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Avocado.getIndex())));
        }
        else {
            text.setText("nokey");
        }

        return rootView;
    }

    private final class MyClickListener implements View.OnClickListener {
        public void onClick(View v) {
            RelativeLayout p = (RelativeLayout) rootView.getParent().getParent();
            ViewPager vp = (ViewPager) rootView.getParent();

            TextView text = (TextView) rootView.findViewById(R.id.avocadoAmount);
            text.setText(String.format("x%d", 0));

            User mUser = ((MainActivity) getActivity()).getmUser();
            CircularProgressBar healthProgress = ((MainActivity) getActivity()).getHealthProgress();
            CircularProgressBar moodProgress = ((MainActivity) getActivity()).getMoodProgress();

            mUser.incrementMood(mUser.foodToMoodConversion(vp.getCurrentItem()));
            System.out.println("now is: " + mUser.getHealth());
            mUser.incrementHealth(mUser.foodToHealthConversion(vp.getCurrentItem()));
            healthProgress.setProgressWithAnimation(mUser.getHealth());
            moodProgress.setProgressWithAnimation(mUser.getMood());
        }
    }
}