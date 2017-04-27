package com.example.android.areyoukittyme.User;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.areyoukittyme.Cat.Cat;
import com.example.android.areyoukittyme.Item.Item;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.Store.Store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by PrGxw on 4/10/2017.
 */

public class User {

    private static String name;
    private static int age;

    private static ArrayList<ArrayList<Double>> userData;
    private final int year = 365;

    private static int stepsGoal;
    private static int focusGoal;
    private static int vocabGoal;

    private static int steps;
    private static int focus;
    private static int vocab;

    // 0: SAT6000
    // 1: French
    // 2: German
    // 3: Spanish
    private static int vocabBookID;

    private static HashMap<Integer, int[]> inventoryList;

    // Cat attributes
    private static int cash;
    private static int health;
    private static int mood;

    private static int HEALTH_MAX = 100;
    private static int MOOD_MAX = 100;

    public User(String name) {
        User.name = name;
        User.stepsGoal = 8000;
        User.focusGoal = 120;
        User.vocabGoal = 30;
        User.vocabBookID = 0;
        User.steps = 0;
        User.focus = 0;
        User.vocab = 0;
        User.cash = 1000;
        User.inventoryList = null;
        User.health = 80;
        User.mood = 90;
        User.userData = generateData(year, 30.0);
        User.cash = 1000;
        User.inventoryList = new HashMap<Integer, int[]>();
        initInventoryList();
    }

    public static void userCheckout(ArrayList<Integer> amountList, ArrayList<Integer> priceList) {


        int[] array = new int[2];
        for (int i = 0; i < amountList.size(); i++) {
            int prevAmount = (int)inventoryList.get(i)[0];
            array[0] = amountList.get(i) + prevAmount; // the amount of the item
            array[1] = priceList.get(i); // priece of the item
            inventoryList.put(i, array);

        }
    }

    public static void initInventoryList() {
        int[] array = new int[2];
        for (int i = 0; i < 6; i++) {
            array[0] = 1;
            array[1] = 0;
            inventoryList.put(i, array);
        }
    }

    public static int getInventoryAmount(int key) {
        return inventoryList.get(key)[0];
    }


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        User.age = age;
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
        User.focus += focus;
        if (User.focus >= User.focusGoal) {
            User.cash += 500;
        }
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

    public static HashMap<Integer, int[]> getInventoryList() {
        return inventoryList;
    }

    public static void setInventoryList(HashMap<Integer, int[]> inventoryList) {
        User.inventoryList = inventoryList;
    }

    public static int getCash() {
        return cash;
    }

    public static void setCash(int c) {
        User.cash = c;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        User.health += health;
        if (User.health > 100) {
            User.health = 100;
        }
    }

    public static int getMood() {
        return mood;
    }

    public static void setMood(int mood) {
        User.mood += mood;
        if (User.mood > 100) {
            User.mood = 100;
        }
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

    public static ArrayList<ArrayList<Double>> getUserData() {
        return userData;
    }

    public static void newDay() {
        User.steps = 0;
        User.focus = 0;
        User.vocab = 0;

        if (User.mood >= 60) {
            User.health -= 20;
        }
        else {
            User.health -= 30;
        }

        User.mood -= 10;
    }

    public static void incrementHealth(int amount) {
        User.health += amount;
        if (User.health > HEALTH_MAX) {
            User.health = 100;
        }
    }

    public static void incrementMood(int amount) {
        User.mood += amount;
        if (User.mood > MOOD_MAX) {
            User.mood = 100;
        }
    }

    public static int foodToHealthConversion(int index) {
        // Food: food value / 1000 * 5 (+5 for every $1000 food consumed)
        int price = Store.getItemList().get(index).getPrice();

        return (int) (price * 5) / 1000;
    }
    public static int foodToMoodConversion(int index) {
        //TODO: complete this method
        // Food: if value > 1000, then + 3~5, random
        int price = Store.getItemList().get(index).getPrice();
        Random rnd = new Random();
        int r = rnd.nextInt(3);
        if (price > 1000) {
            switch (r) {
                case 0: return 3;
                case 1: return 4;
                default: return 5;
            }
        }
        return 0;

    }
}
