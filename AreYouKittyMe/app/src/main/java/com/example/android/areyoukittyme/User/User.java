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

    public static String name;

    public static int stepsGoal;
    public static int focusGoal;
    public static int vocabGoal;

    public static int steps;
    public static int focus;
    public static int vocab;

    // 0: SAT6000
    // 1: French
    // 2: German
    // 3: Spanish
    public static int vocabBookID;

    public static int cash;
    public static HashMap<Integer, Object[]> inventoryList;

    // Cat attributes
    public static int health;
    public static int mood;

    public User(String name) {
        User.name = name;
        User.stepsGoal = 8000;
        User.focusGoal = 120;
        User.vocabGoal = 30;
        User.vocabBookID = 0;
        User.steps = 0;
        User.focus = 0;
        User.vocab = 0;
        User.cash = 0;
        User.inventoryList = null;
        User.health = 100;
        User.mood = 100;

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

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static int getStepsGoal() {
        return stepsGoal;
    }

    public static void setStepsGoal(int stepsGoal) {
        User.stepsGoal = stepsGoal;
    }

    public static int getFocusGoal() {
        return focusGoal;
    }

    public static void setFocusGoal(int focusGoal) {
        User.focusGoal = focusGoal;
    }

    public static int getVocabGoal() {
        return vocabGoal;
    }

    public static void setVocabGoal(int vocabGoal) {
        User.vocabGoal = vocabGoal;
    }

    public static int getSteps() {
        return steps;
    }

    public static void setSteps(int steps) {
        User.steps = steps;
    }

    public static int getFocus() {
        return focus;
    }

    public static void setFocus(int focus) {
        User.focus = focus;
    }

    public static int getVocab() {
        return vocab;
    }

    public static void setVocab(int vocab) {
        User.vocab = vocab;
    }

    public static int getVocabBookID() {
        return vocabBookID;
    }

    public static void setVocabBookID(int vocabBookID) {
        User.vocabBookID = vocabBookID;
    }

    public static int getCash() {
        return cash;
    }

    public static void setCash(int cash) {
        User.cash = cash;
    }

    public static HashMap<Integer, Object[]> getInventoryList() {
        return inventoryList;
    }

    public static void setInventoryList(HashMap<Integer, Object[]> inventoryList) {
        User.inventoryList = inventoryList;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        User.health = health;
    }

    public static int getMood() {
        return mood;
    }

    public static void setMood(int mood) {
        User.mood = mood;
    }
}
