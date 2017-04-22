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

public class BaconFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_bacon, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.baconAmount);

        if (User.getInventoryList().containsKey(3)) {
//            text.setText("found");
//            text.setText("");
            text.setText(String.format("x%d", User.getInventoryAmount(3)));
        }
        else {
            text.setText("nokey");
        }

        return rootView;
    }

}