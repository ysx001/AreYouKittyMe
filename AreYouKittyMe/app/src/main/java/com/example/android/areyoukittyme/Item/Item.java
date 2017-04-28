package com.example.android.areyoukittyme.Item;

import android.graphics.drawable.Drawable;

/**
 * Created by PrGxw on 4/10/2017.
 */

public interface Item {

    abstract void setPrice(int price);

    abstract void setIcon(int icon);

    abstract int getId();

    abstract int getPrice();


}
