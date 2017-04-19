package com.example.android.areyoukittyme.User;

import android.widget.TextView;

import com.example.android.areyoukittyme.Cat.Cat;
import com.example.android.areyoukittyme.Item.Item;
import com.example.android.areyoukittyme.Store.Store;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PrGxw on 4/10/2017.
 */

public class User {

    public static int cash;
    public static HashMap<Integer, Object[]> inventoryList;
    private Cat myCat;

    public User(int cash, HashMap<Integer, Object[]> inventoryList) {
        this.cash = cash;
        this.inventoryList = inventoryList;
    }

    public void userCheckout(ArrayList<TextView> amountList, ArrayList<Integer> priceList) {
        Object[] array = new Object[3];
        for (int i = 0; i < amountList.size(); i++) {
            array[0] = Integer.parseInt(amountList.get(i).getText().toString());
            array[1] = priceList.get(i);
            array[2] = Store.itemList.get(i);
            this.inventoryList.put(i, array);
        }
    }
    public void initInventoryList(ArrayList<TextView> amountList, ArrayList<Integer> priceList) {
        Object[] array = new Object[3];
        for (int i = 0; i < amountList.size(); i++) {
            array[0] = Integer.parseInt(amountList.get(i).getText().toString());
            array[1] = priceList.get(i);
            array[2] = Store.itemList.get(i);
            this.inventoryList.put(i, array);
        }
    }


    public HashMap<Integer, Object[]> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(HashMap inventoryList) {
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
