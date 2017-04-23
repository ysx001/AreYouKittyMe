package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Bacon;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class BaconFragment extends Fragment {
    private static ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_bacon, container, false);
        updateAmount();
        return rootView;
    }

    public static void updateAmount() {
        TextView text = (TextView)rootView.findViewById(R.id.baconAmount);

        if (User.getInventoryList().containsKey(Bacon.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Bacon.getIndex())));
        }
        else {
            text.setText("nokey");
        }
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        TextView t = (TextView)rootView.findViewById(R.id.fishAmount);
//        t.setText(String.format("x%d", User.getInventoryAmount(Bacon.getIndex())));
//    }

}