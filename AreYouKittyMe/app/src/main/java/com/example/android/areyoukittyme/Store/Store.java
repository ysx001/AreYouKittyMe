package com.example.android.areyoukittyme.Store;

import com.example.android.areyoukittyme.Item.Asparagus;
import com.example.android.areyoukittyme.Item.Avocado;
import com.example.android.areyoukittyme.Item.Bacon;
import com.example.android.areyoukittyme.Item.Candy;
import com.example.android.areyoukittyme.Item.Corndog;
import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.Item.Hamburger;
import com.example.android.areyoukittyme.Item.Item;

import java.util.ArrayList;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Store {

    public static ArrayList<Item> itemList;

    public Store() {
        itemList = new ArrayList<>();
        this.itemList.add(new Fish());
        this.itemList.add(new Asparagus());
        this.itemList.add(new Avocado());
        this.itemList.add(new Bacon());
        this.itemList.add(new Hamburger());
        this.itemList.add(new Corndog());
        this.itemList.add(new Candy());
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    private void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

}
