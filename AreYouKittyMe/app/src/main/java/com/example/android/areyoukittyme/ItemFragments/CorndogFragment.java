package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Corndog;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class CorndogFragment extends Fragment {

    private static ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_corndog, container, false);
        updateAmount();
        return rootView;
    }

    public static void updateAmount() {
        TextView text = (TextView)rootView.findViewById(R.id.corndogAmount);

        if (User.getInventoryList().containsKey(Corndog.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Corndog.getIndex())));
        }
        else {
            text.setText("nokey");
        }
    }
}