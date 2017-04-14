package com.example.android.areyoukittyme.User;

import com.example.android.areyoukittyme.Cat.Cat;
import com.example.android.areyoukittyme.Item.Item;

import java.util.ArrayList;

/**
 * Created by PrGxw on 4/10/2017.
 */

public class User {

    public static int cash;
    public static ArrayList<Item> inventoryList;
    private Cat myCat;

    public User(int cash, ArrayList<Item> inventoryList) {
        this.cash = cash;
        this.inventoryList = inventoryList;
    }


    public ArrayList<Item> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(ArrayList<Item> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void incrementCash(int amount) {
        this.cash += amount;
    }

//    public void adoptCat() {
////        this.myCat =
//    }
}
