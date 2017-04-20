package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class HamburgerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_hamburger, container, false);


        TextView text = (TextView)rootView.findViewById(R.id.hamburgerAmount);

        if (User.inventoryList.containsKey(4)) {
//            text.setText("found");
//            text.setText("");
            text.setText(String.format("x%d", User.getInventoryAmount(4)));
        }
        else {
            text.setText("nokey");
        }

        rootView.findViewById(R.id.hamburgerLeft).setOnClickListener(new MyClickListener());
        rootView.findViewById(R.id.hamburgerRight).setOnClickListener(new MyClickListener());
        return rootView;
    }
    private final class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ViewPager viewPager = (ViewPager) v.getRootView().findViewById(R.id.pager_temp);
            if (v.getId() == R.id.hamburgerLeft) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
            }
            else if (v.getId() == R.id.hamburgerRight) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
            }
        }
    }
}