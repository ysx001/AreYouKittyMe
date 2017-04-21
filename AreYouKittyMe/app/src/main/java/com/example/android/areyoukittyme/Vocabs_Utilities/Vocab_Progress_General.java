package com.example.android.areyoukittyme.Vocabs_Utilities;


/**
 * Created by haoyuxiong on 4/11/17.
 */

public class Vocab_Progress_General {

    private int numOfVocabStudied;
    private int numOfVocabStudying;
    private int numOfVocabHaventStudied;
    private int numOfVocabInTotal;
    private String nameOfTheVocabList;
    private String startDate;
    private int dailyGoal;

    public Vocab_Progress_General(int numOfVocabInTotal, String nameOfTheVocabList, String date, int dailyGoal){
        this.numOfVocabInTotal = numOfVocabInTotal;
        this.numOfVocabHaventStudied = numOfVocabInTotal;
        this.numOfVocabStudied = 0;
        this.numOfVocabStudying = 0;
        this.nameOfTheVocabList = nameOfTheVocabList;
        this.startDate = date;
        this.dailyGoal = dailyGoal;

    }

    public int getNumOfVocabStudied() {
        return numOfVocabStudied;
    }

    public void setNumOfVocabStudied(int numOfVocabStudied) {
        this.numOfVocabStudied = numOfVocabStudied;
    }

    public int getNumOfVocabStudying() {
        return numOfVocabStudying;
    }

    public void setNumOfVocabStudying(int numOfVocabStudying) {
        this.numOfVocabStudying = numOfVocabStudying;
    }

    public int getNumOfVocabHaventStudied() {
        return numOfVocabHaventStudied;
    }

    public void setNumOfVocabHaventStudied(int numOfVocabHaventStudied) {
        this.numOfVocabHaventStudied = numOfVocabHaventStudied;
    }

    public int getNumOfVocabInTotal() {
        return numOfVocabInTotal;
    }

    public void setNumOfVocabInTotal(int numOfVocabInTotal) {
        this.numOfVocabInTotal = numOfVocabInTotal;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void studyOne(){

        this.numOfVocabStudying += 1;
        this.numOfVocabHaventStudied -=1 ;


    }

    public void finishStudyOne(){
        this.numOfVocabStudying -=1;
        this.numOfVocabStudied += 1;

    }

    public void restudyAWord(){
        this.numOfVocabStudied-=1;
        this.numOfVocabStudying+=1;

    }

    public int getDailyGoal() {
        return dailyGoal;
    }

    public void setDailyGoal(int dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    public String getNameOfTheVocabList() {
        return nameOfTheVocabList;
    }

    public void setNameOfTheVocabList(String nameOfTheVocabList) {
        this.nameOfTheVocabList = nameOfTheVocabList;
    }
}
