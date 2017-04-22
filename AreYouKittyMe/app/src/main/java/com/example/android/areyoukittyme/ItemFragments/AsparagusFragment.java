package com.example.android.areyoukittyme.ItemFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.Item.Asparagus;
import com.example.android.areyoukittyme.Item.Avocado;
import com.example.android.areyoukittyme.Item.Bacon;
import com.example.android.areyoukittyme.Item.Corndog;
import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.Item.Hamburger;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.StoreActivity;
import com.example.android.areyoukittyme.User.User;

/**
 * Created by PrGxw on 4/18/2017.
 */

public class AsparagusFragment extends Fragment {
    private static ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_asparagus, container, false);
        updateAmount();
        return rootView;
    }

    public static void updateAmount() {
        TextView text = (TextView)rootView.findViewById(R.id.asparagusAmount);

        if (User.getInventoryList().containsKey(Asparagus.getIndex())) {
            text.setText(String.format("x%d", User.getInventoryAmount(Asparagus.getIndex())));
        }
        else {
            text.setText("nokey");
        }
    }
}
