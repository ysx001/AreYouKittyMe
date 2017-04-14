package com.example.android.areyoukittyme.Item;

import android.graphics.drawable.Drawable;

import com.example.android.areyoukittyme.R;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Fish implements Item{

    private static int price;
    private int icon;
    private int id;

    public Fish() {
        setPrice(80);
        setIcon(R.drawable.fish);
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public static int getPrice() {
        return price;
    }
}
