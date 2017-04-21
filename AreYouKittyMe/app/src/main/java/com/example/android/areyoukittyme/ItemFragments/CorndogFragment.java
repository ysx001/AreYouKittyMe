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

public class CorndogFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_corndog, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.corndogAmount);

        if (User.getInventoryList().containsKey(5)) {
//            text.setText("found");
//            text.setText("");
            text.setText(String.format("x%d", User.getInventoryAmount(5)));
        }
        else {
            text.setText("nokey");
        }
        rootView.findViewById(R.id.corndogLeft).setOnClickListener(new MyClickListener());
//        rootView.findViewById(R.id.asparagusRight).setOnClickListener(new MyClickListener());
        return rootView;
    }

    private final class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ViewPager viewPager = (ViewPager) v.getRootView().findViewById(R.id.pager_temp);
            if (v.getId() == R.id.corndogLeft) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        }
    }
}