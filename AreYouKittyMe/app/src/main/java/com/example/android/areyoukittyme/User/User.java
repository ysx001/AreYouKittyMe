package com.example.android.areyoukittyme.User;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.Cat.Cat;
import com.example.android.areyoukittyme.Item.Item;
import com.example.android.areyoukittyme.R;
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

    public User() {
        this.cash = 1000;
        this.inventoryList = new HashMap<Integer, Object[]>();
        initInventoryList();
    }

    public static void userCheckout(ArrayList<TextView> amountList, ArrayList<Integer> priceList) {
        Object[] array = new Object[3];
        for (int i = 0; i < amountList.size(); i++) {

            array[0] = Integer.parseInt(amountList.get(i).getText().toString());
            array[1] = priceList.get(i);
            array[2] = Store.getItemList().get(i);
            inventoryList.put(i, array);
        }
    }

    public static void initInventoryList() {
        Object[] array = new Object[3];
        for (int i = 0; i < 6; i++) {
            array[0] = 0;
            array[1] = 0;
            array[2] = null;
            inventoryList.put(i, array);
        }
    }

    public static int getInventoryAmount(int key) {
        return (int)inventoryList.get(key)[0];
    }


    public HashMap<Integer, Object[]> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(HashMap inventoryList) {
        this.inventoryList = inventoryList;
    }

    public static int getCash() {
        return cash;
    }

    public static void setCash(int c) {
        cash = c;
    }

    public void incrementCash(int amount) {
        this.cash += amount;
    }

//    public void adoptCat() {
////        this.myCat =
//    }
}
