package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.areyoukittyme.Item.Asparagus;
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
    private User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUser = ((MainActivity) getActivity()).getmUser();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_avocado, container, false);
        TextView text = (TextView)rootView.findViewById(R.id.avocadoAmount);
        rootView.findViewById(R.id.avocadoImage).setOnClickListener(new MyClickListener());

        System.out.println("Avocado index now is " + Avocado.getIndex());
        System.out.println("Avocado amount now is " + mUser.getInventoryAmount(Avocado.getIndex()));



        if (mUser.getInventoryList().containsKey(Avocado.getIndex())) {
            text.setText(String.format("x%d", mUser.getInventoryAmount(Avocado.getIndex())));
        }
        else {
            text.setText("nokey");
        }

        return rootView;
    }

    private final class MyClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (mUser.getInventoryAmount(Avocado.getIndex()) > 0) {
                RelativeLayout p = (RelativeLayout) rootView.getParent().getParent();
                ViewPager vp = (ViewPager) rootView.getParent();

                TextView text = (TextView) rootView.findViewById(R.id.avocadoAmount);
                text.setText(String.format("x%d", mUser.getInventoryAmount(Avocado.getIndex()) - 1));

                String Toasttext = String.format("Health increased by %d \n Mood increased by %d", mUser.foodToHealthConversion(vp.getCurrentItem()), mUser.foodToMoodConversion(vp.getCurrentItem()));

                Toast.makeText(getActivity(), Toasttext ,Toast.LENGTH_SHORT).show();

                CircularProgressBar healthProgress = ((MainActivity) getActivity()).getHealthProgress();
                CircularProgressBar moodProgress = ((MainActivity) getActivity()).getMoodProgress();

                int prevAmount = mUser.getInventoryAmount(Avocado.getIndex());
                mUser.getInventoryList().put(Avocado.getIndex(), prevAmount - 1);

                mUser.setMood(mUser.foodToMoodConversion(vp.getCurrentItem()));
                System.out.println("now is: " + mUser.getHealth());
                mUser.setHealth(mUser.foodToHealthConversion(vp.getCurrentItem()));
                healthProgress.setProgressWithAnimation(mUser.getHealth());
                moodProgress.setProgressWithAnimation(mUser.getMood());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView text = (TextView)rootView.findViewById(R.id.avocadoAmount);
        text.setText(String.format("x%d", mUser.getInventoryAmount(Avocado.getIndex())));
    }
}