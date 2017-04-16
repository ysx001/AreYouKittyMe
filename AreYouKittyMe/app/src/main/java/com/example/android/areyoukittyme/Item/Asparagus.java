package com.example.android.areyoukittyme.Item;

import com.example.android.areyoukittyme.R;

/**
 * Created by PrGxw on 4/15/2017.
 */

public class Asparagus implements Item{
    private static int price;
    private int icon;
    public static int id;

    public Asparagus() {
        setPrice(90);
        this.id = R.drawable.asparagus;
        setIcon(this.id);
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

    public int getPrice() {
        return price;
    }
}
