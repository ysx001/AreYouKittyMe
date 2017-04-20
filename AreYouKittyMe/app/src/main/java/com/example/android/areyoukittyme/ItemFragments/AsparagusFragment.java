package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.StoreActivity;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class AsparagusFragment extends Fragment {
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_asparagus, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.asparagusAmount);

        if (User.inventoryList.containsKey(1)) {
//            text.setText("found");
//            text.setText("");
            text.setText(String.format("x%d", User.getInventoryAmount(1)));
        }
        else {
            text.setText("nokey");
        }

        rootView.findViewById(R.id.asparagusLeft).setOnClickListener(new MyClickListener());
        rootView.findViewById(R.id.asparagusRight).setOnClickListener(new MyClickListener());
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView != null) {
            TextView text = (TextView) rootView.findViewById(R.id.asparagusAmount);
            if (User.inventoryList.containsKey(1)) {
//            text.setText("found");
//            text.setText("");
                text.setText(String.format("x%d", User.getInventoryAmount(1)));
                text.invalidate();
            } else {
                text.setText("nokey");
            }
        }
    }
    private final class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ViewPager viewPager = (ViewPager) v.getRootView().findViewById(R.id.pager_temp);
            if (v.getId() == R.id.asparagusLeft) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
            }
            else if (v.getId() == R.id.asparagusRight) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
            }
        }
    }
}
