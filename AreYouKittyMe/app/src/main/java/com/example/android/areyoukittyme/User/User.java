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

    private ArrayList<ArrayList<Double>> userData;
    private final int year = 365;

    public User() {
        this.userData = generateData(year, 30.0);
    }

    public User(int cash, HashMap<Integer, Object[]> inventoryList) {
        this.cash = cash;
        this.inventoryList = inventoryList;
        this.userData = generateData(year, 30.0);
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

    private ArrayList<ArrayList<Double>> generateData(int count, Double range) {

        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<Double> stepCounts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range ;
            Double val = (Math.random() * mult) + 50;
            stepCounts.add(val);
        }

        ArrayList<Double> focusTime = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 2.0;
            Double val = (Math.random() * mult) + 60;
            focusTime.add(val);
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Double> vocabTime = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 5.0;
            Double val = (Math.random() * mult) + 100;
            vocabTime.add(val);
        }

        data.add(stepCounts);
        data.add(focusTime);
        data.add(vocabTime);

        return data;
    }

    public ArrayList<ArrayList<Double>> getUserData() {
        return userData;
    }
//    public void adoptCat() {
////        this.myCat =
//    }
}
