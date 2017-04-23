package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class FishFragment extends Fragment {
    private static ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_fish, container, false);
        TextView text = (TextView)rootView.findViewById(R.id.fishAmount);
        rootView.findViewById(R.id.fishImage).setOnClickListener(new MyClickListener());



        if (User.getInventoryList().containsKey(Fish.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Fish.getIndex())));
        }
        else {
            text.setText("nokey");
        }

        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        TextView t = (TextView)rootView.findViewById(R.id.fishAmount);
//        t.setText(String.format("x%d", User.getInventoryAmount(Fish.getIndex())));
//    }
    private final class MyClickListener implements View.OnClickListener {
        public void onClick(View v) {
            RelativeLayout p = (RelativeLayout) rootView.getParent().getParent();
            ViewPager vp = (ViewPager) rootView.getParent();
            p.setVisibility(View.INVISIBLE);
            // TODO: decrease in amount;
            // TODO: animation:
            // increase health and mood
            User.incrementHealth(User.foodToHealthConversion(vp.getCurrentItem()));
            User.incrementMood(User.foodToMoodConversion(vp.getCurrentItem()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView text = (TextView)rootView.findViewById(R.id.fishAmount);
        if (User.getInventoryList().containsKey(Fish.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Fish.getIndex())));
//            text.setText("Resume");
        }
        else {
            text.setText("nokey");
        }
    }
}