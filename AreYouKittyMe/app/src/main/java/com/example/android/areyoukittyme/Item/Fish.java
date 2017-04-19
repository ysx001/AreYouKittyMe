package com.example.android.areyoukittyme.Item;

import android.graphics.drawable.Drawable;

import com.example.android.areyoukittyme.R;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Fish implements Item{

    private static int price;
    private static int icon;
    private static int id;

    public Fish() {
        setPrice(80);
        this.id = R.drawable.fish;
        setIcon(id);
    }

    @Override
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public static int getIcon() {
        return icon;
    }
    public static void setId(int id) {
        Fish.id = id;
    }
    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
}
