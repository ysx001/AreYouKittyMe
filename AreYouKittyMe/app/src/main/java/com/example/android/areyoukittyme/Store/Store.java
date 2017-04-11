package com.example.android.areyoukittyme.Store;

import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.Item.Item;

import java.util.ArrayList;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Store {

    private ArrayList<Item> itemList;

    public Store() {
        randomizeList();
    }

    public void randomizeList () {
        this.itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.itemList.add(new Fish());
        }
    }
}
