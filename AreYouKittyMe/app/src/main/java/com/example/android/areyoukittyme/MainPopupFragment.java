package com.example.android.areyoukittyme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.android.areyoukittyme.R;


/**
 * Created by PrGxw on 4/16/2017.
 */

public class MainPopupFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.popup_layout, container, false);

        return rootView;
    }
}
