package com.example.android.areyoukittyme.Store;

import com.example.android.areyoukittyme.Item.Asparagus;
import com.example.android.areyoukittyme.Item.Avocado;
import com.example.android.areyoukittyme.Item.Bacon;
import com.example.android.areyoukittyme.Item.Corndog;
import com.example.android.areyoukittyme.Item.Fish;
import com.example.android.areyoukittyme.Item.Hamburger;
import com.example.android.areyoukittyme.Item.Item;

import java.util.ArrayList;

/**
 * Created by PrGxw on 4/11/2017.
 */

public class Store {

    private static ArrayList<Item> itemList;

    public Store() {
        itemList = new ArrayList<>();
        this.itemList.add(new Fish(0));
        this.itemList.add(new Asparagus(1));
        this.itemList.add(new Avocado(2));
        this.itemList.add(new Bacon(3));
        this.itemList.add(new Hamburger(4));
        this.itemList.add(new Corndog(5));
            System.out.println("ALL IDs now is : " + Fish.getIndex());
            System.out.println("ALL IDs now is : " + Asparagus.getIndex());
            System.out.println("ALL IDs now is : " + Avocado.getIndex());
            System.out.println("ALL IDs now is : " + Bacon.getIndex());
            System.out.println("ALL IDs now is : " + Hamburger.getIndex());
            System.out.println("ALL IDs now is : " + Corndog.getIndex());
    }

    public static ArrayList<Item> getItemList() {
        return itemList;
    }

    public static void setItemList(ArrayList<Item> list) {
        itemList = list;
    }

}
