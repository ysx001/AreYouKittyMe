package com.example.android.areyoukittyme.User;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.example.android.areyoukittyme.Cat.Cat;
import com.example.android.areyoukittyme.Item.Item;
import com.example.android.areyoukittyme.R;
import com.example.android.areyoukittyme.Store.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by PrGxw on 4/10/2017.
 * <p>
 * Modifed by ysx001 on 4/25/2017. Added Parcelable
 */

public class User implements Parcelable {

    private  String name;
    private  int age;
    private  int totalDays;

    private ArrayList<UserData> userData = new ArrayList<>();
    private final int year = 365;

    private int stepsGoal;
    private int focusGoal;
    private int vocabGoal;

    private int steps;
    private int focus;
    private int vocab;

    // 0: SAT6000
    // 1: French
    // 2: German
    // 3: Spanish
    private int vocabBookID;

    private static HashMap<Integer, Integer> inventoryList;

    // Cat attributes
    private int cash;
    private int health;
    private int mood;

    private static int HEALTH_MAX = 100;
    private static int MOOD_MAX = 100;

    public User(String name) {
        this.name = name;
        this.age = 1;
        this.totalDays = 0;
        this.stepsGoal = 8000;
        this.focusGoal = 120;
        this.vocabGoal = 30;
        this.vocabBookID = 0;
        this.steps = 0;
        this.focus = 0;
        this.vocab = 0;
        this.cash = 1000;
        this.inventoryList = null;
        this.health = 80;
        this.mood = 50;
        this.health = 80;
        this.mood = 90;
        this.userData = generateData(year, 30.0);
        this.inventoryList = new HashMap<Integer, Integer>();
        initInventoryList();
    }

    public void userCheckout(ArrayList<Integer> amountList, ArrayList<Integer> priceList) {
        for (int i = 0; i < amountList.size(); i++) {
            int[] array = new int[2];
            int prevAmount = this.inventoryList.get(i);
//            array[0] = amountList.get(i) + prevAmount; // the amount of the item
//            array[1] = priceList.get(i); // price of the item
            int temp = amountList.get(i) + prevAmount; // the amount of the item
            this.inventoryList.put(i, temp);

        }
    }

    public void initInventoryList() {

        for (int i = 0; i < 6; i++) {
            int[] array = new int[2];
            int temp = 1; // amount
//            array[1] = 0; // price
            this.inventoryList.put(i, temp);
        }
        System.out.println("Initialized, the inventory list now is" + this.inventoryList);
    }

    public int getInventoryAmount(int key) {
        return inventoryList.get(key);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserData(ArrayList<UserData> userData) {
        this.userData = userData;
    }

    public ArrayList<UserData> getUserData() {
        return userData;
    }

    public int getYear() {
        return year;
    }

    public int getStepsGoal() {
        return stepsGoal;
    }

    public void setStepsGoal(int stepsGoal) {
        this.stepsGoal = stepsGoal;
    }

    public int getFocusGoal() {
        return focusGoal;
    }

    public void setFocusGoal(int focusGoal) {
        this.focusGoal = focusGoal;
    }

    public int getVocabGoal() {
        return vocabGoal;
    }

    public void setVocabGoal(int vocabGoal) {
        this.vocabGoal = vocabGoal;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus += focus;
        if (this.focus >= this.focusGoal) {
            this.cash += 500;
        }
    }

    public int getVocab() {
        return vocab;
    }

    public void setVocab(int vocab) {
        this.vocab = vocab;
    }

    public int getVocabBookID() {
        return vocabBookID;
    }

    public void setVocabBookID(int vocabBookID) {
        this.vocabBookID = vocabBookID;
    }

    public HashMap<Integer, Integer> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(HashMap<Integer, Integer> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood += mood;
        if (this.mood > 100) {
            this.mood = 100;
        }
    }

    public void incrementHealth(int amount) {
        this.health += amount;
        if (this.health > HEALTH_MAX) {
            this.health = 100;
        }
    }

    public void incrementMood(int amount) {
        this.mood += amount;
        if (this.mood > MOOD_MAX) {
            this.mood = 100;
        }
    }

    public int foodToHealthConversion(int index) {
        // Food: food value / 1000 * 5 (+5 for every $1000 food consumed)
        int price = Store.getItemList().get(index).getPrice();

        return (int) (price * 5) / 1000;
//        return 5000;
    }

    public int foodToMoodConversion(int index) {
        // Food: if value > 1000, then + 3~5, random
        int price = Store.getItemList().get(index).getPrice();
        Random rnd = new Random();
        int r = rnd.nextInt(3);
        if (price > 1000) {
            switch (r) {
                case 0:
                    return 3;
                case 1:
                    return 4;
                default:
                    return 5;
            }
        }
        return 0;
    }

    private ArrayList<UserData> generateData(int count, Double range) {

        ArrayList<UserData> data = new ArrayList<>();


        ArrayList<Double> stepCountslist = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range;
            Double val = (Math.random() * mult) + 1000;
            stepCountslist.add(val);
        }

        ArrayList<Double> focusTimelist = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 2.0;
            Double val = (Math.random() * mult) + 60;
            focusTimelist.add(val);
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Double> vocabTimelist = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 5.0;
            Double val = (Math.random() * mult) + 20;
            vocabTimelist.add(val);
        }

        UserData stepCounts = new UserData(stepCountslist);
        UserData focusTime = new UserData(focusTimelist);
        UserData vocabTime = new UserData(vocabTimelist);

        data.add(stepCounts);
        data.add(focusTime);
        data.add(vocabTime);

        return data;
    }


    public void newDay() {
        this.totalDays ++;

        if (this.age < 8 && this.totalDays >= Math.pow(this.age, 2)) {
            this.totalDays = 0;
            this.age ++;
        }

        this.steps = 0;
        this.focus = 0;
        this.vocab = 0;

        if (this.mood >= 60) {
            this.health -= 20;
        } else {
            this.health -= 30;
        }

        this.mood -= 10;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeTypedList(this.userData);
        dest.writeInt(this.stepsGoal);
        dest.writeInt(this.focusGoal);
        dest.writeInt(this.vocabGoal);
        dest.writeInt(this.steps);
        dest.writeInt(this.focus);
        dest.writeInt(this.vocab);
        dest.writeInt(this.vocabBookID);
        //dest.writeSerializable(this.inventoryList);
        dest.writeInt(this.cash);
        dest.writeInt(this.health);
        dest.writeInt(this.mood);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.userData = in.createTypedArrayList(UserData.CREATOR);
        this.stepsGoal = in.readInt();
        this.focusGoal = in.readInt();
        this.vocabGoal = in.readInt();
        this.steps = in.readInt();
        this.focus = in.readInt();
        this.vocab = in.readInt();
        this.vocabBookID = in.readInt();
//        this.inventoryList = (HashMap<Integer, Object[]>) in.readSerializable();
        this.cash = in.readInt();
        this.health = in.readInt();
        this.mood = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };




}
