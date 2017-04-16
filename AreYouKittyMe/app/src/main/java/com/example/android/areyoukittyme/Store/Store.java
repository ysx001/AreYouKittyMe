package com.example.android.areyoukittyme.Store;

import com.example.android.areyoukittyme.Item.Asparagus;
import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.Item.Item;

import java.util.ArrayList;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Store {

    private ArrayList<Item> itemList;

    public Store() {
        itemList.add(new Fish());
        itemList.add(new Asparagus());
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    private void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

}
