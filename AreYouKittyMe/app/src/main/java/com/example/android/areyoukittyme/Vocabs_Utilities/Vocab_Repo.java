package com.example.android.areyoukittyme.Vocabs_Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.areyoukittyme.Quiz_Utilities.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by haoyuxiong on 4/13/17.
 */




public class Vocab_Repo {


    /**
     * Created by Tan on 1/26/2016.
     */





    private final static String TAG = Vocab_Repo.class.getName().toString();
    private static VocabList vocabList;
    public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final int[] datePeriods = {2, 3, 5, 8, 16, 26, 41};
    public static final int[] dateIncrement = {1, 1, 2, 3, 8, 10, 15};
    public final static long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
    private static int dailyGoal;
    private static int numOfRow;
    public final static Random random = new Random();
    private static int numOfVocab;
    private static int numOfStudied;
    private static int numOfStudying;



    public Vocab_Repo() {

    }


    public static String createTable() {
        return "CREATE TABLE " + Vocab.TABLE + "("
                + Vocab.KEY_VOCAB_ID + " INTEGER PRIMARY KEY autoincrement ,"
                + Vocab.KEY_WORD + " TEXT,"
                + Vocab.KEY_DEFINITION + " TEXT,"
                + Vocab.KEY_PROGRESS + " INTEGER,"
                + Vocab.KEY_DAY + " TEXT," +
                  Vocab.KEY_DATE+ "TEXT )";
    }


    public static int insert(Vocab vocab) {
        int vocabId;
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Vocab.KEY_VOCAB_ID, vocab.getVocab_Id());
        values.put(Vocab.KEY_WORD, vocab.getWord());
        values.put(Vocab.KEY_DEFINITION, vocab.getDefinition());
        values.put(Vocab.KEY_PROGRESS, vocab.getProgress());
        values.put(Vocab.KEY_DAY, "0");
        values.put(Vocab.KEY_DATE,vocab.getDate());


        // Inserting Row
        vocabId = (int) db.insert(Vocab.TABLE, null, values);
        db.close();

        return vocabId;
    }


    public static void delete() {
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        db.delete(Vocab.TABLE, null, null);
        db.close();
    }

    public static ArrayList<VocabList> getAllVocabList() {

        ArrayList<VocabList>vocabLists = new ArrayList<VocabList>();
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                vocabList = new VocabList();
                vocabList.setVocab_Id(cursor.getInt(0));
                vocabList.setWord(cursor.getString(1));
                vocabList.setDefinition(cursor.getString(2));
                vocabList.setProgress(cursor.getInt(3));
                vocabLists.add(vocabList);


            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return vocabLists;
    }

    public static ArrayList<VocabList> getVocabsInSpecifiedState(String state ){

        ArrayList<VocabList>vocabLists = new ArrayList<VocabList>();
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE Vocab.progress='"+state +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                vocabList = new VocabList();
                vocabList.setVocab_Id(cursor.getInt(0));
                vocabList.setWord(cursor.getString(1));
                vocabList.setDefinition(cursor.getString(2));
                vocabList.setProgress(cursor.getInt(3));
                vocabLists.add(vocabList);


            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return vocabLists;
    }

    public static int getCurrentVocabCount(){
        return numOfRow;
    }

    public static Vocab getAVocabByWord(String criteria, String word){

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery;
        if (criteria.equals(Vocab.KEY_DAY)||criteria.equals(Vocab.KEY_VOCAB_ID)||criteria.equals(Vocab.KEY_PROGRESS)){
             selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE "+criteria+"="+word;
        }else{
             selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE "+criteria+"='"+word +"'";}
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();}
        else{
            db.close();
            cursor.close();
            return null;
        }

        Vocab vocabWanted = new Vocab(cursor.getInt(cursor.getColumnIndex(Vocab.KEY_VOCAB_ID)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_WORD)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DEFINITION)),
                cursor.getColumnIndex(Vocab.KEY_PROGRESS),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DAY)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DATE)));

        cursor.close();
        db.close();


        return vocabWanted;

    }

    public static void updateAWordOnItsProgress(int newState, int id, int currentState){
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "UPDATE "+ Vocab.TABLE +
                " SET " + Vocab.KEY_PROGRESS + " = " + newState + " WHERE " + Vocab.KEY_VOCAB_ID + "=?";

        try{
            db.rawQuery(selectQuery,new String[]{id+""});
            if(currentState==0 && newState == 1){
                Vocab_DatabaseManager.getInstance().getVocabGeneralProgress().studyOne();
                numOfStudying+=1;
                selectQuery = "UPDATE "+ Vocab.TABLE +
                        " SET " + Vocab.KEY_DAY + " = " + 1 + " WHERE " + Vocab.KEY_VOCAB_ID + "=?";
                db.rawQuery(selectQuery,new String[]{id+""});
            }else if(currentState==1 && newState==2){
                numOfStudied+=1;
                numOfStudying-=1;
                Vocab_DatabaseManager.getInstance().getVocabGeneralProgress().finishStudyOne();
            }

        }catch(Exception whatever){

        }
        db.close();
    }



    public static void updateAWordOnItsReviewPeriod(int id) throws ParseException {
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();

        Vocab vocab = getAVocabByWord(Vocab.KEY_VOCAB_ID,String.format("%d",id));

        int dateDifference = dateIncrement[Integer.parseInt(vocab.getDay())];

        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime()+dateDifference*MILLISECONDS_PER_DAY);
        String newDate = simpleDateFormat.format(currentDate);
        ContentValues values = new ContentValues();

        values.put(Vocab.KEY_VOCAB_ID, vocab.getVocab_Id());
        values.put(Vocab.KEY_WORD, vocab.getWord());
        values.put(Vocab.KEY_DEFINITION, vocab.getDefinition());
        if(Integer.parseInt(vocab.getDate())>=7){
            values.put(Vocab.KEY_PROGRESS, 2);
            numOfStudied += 1;
            numOfStudying-=1;
        }else{
            values.put(Vocab.KEY_PROGRESS, vocab.getProgress());
        }
        values.put(Vocab.KEY_DAY, String.format("%d",Integer.parseInt(vocab.getDate()))+1);
        values.put(Vocab.KEY_DATE,newDate);

        try{
            db.update(Vocab.TABLE,values, Vocab.KEY_VOCAB_ID+"=?",new String[]{String.format("%d",id)});
        }catch(Exception whatever){

        }finally{
        db.close();
        }

    }

    public static void deleteAllRows(){
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        db.execSQL("DELETE FROM "+Vocab.TABLE);
        db.close();

    }

    public static void addAnEntireVocabListToTheDataBase(String filename){

        deleteAllRows();
        String currentDate = simpleDateFormat.format(new Date());
        Vocab vocab;

        ArrayList<ArrayList<String>> vocabArrays = Vocab_Data_Csv_Process.processInData(
                                        Vocab_Data_Csv_Process.ReadInVocabData(filename));
        Vocab_DatabaseManager.setVocabGeneralProgress(new Vocab_Progress_General(vocabArrays.size(),filename, currentDate, dailyGoal));
        int j = 0;
        for(ArrayList<String> i: vocabArrays){

            vocab = new Vocab(j, i.get(0), i.get(1),0, "0", currentDate );
            insert(vocab);
            j+= 1;

        }
        numOfRow = vocabArrays.size();
    }

    public static ArrayList<VocabList> getVocabsReviewableVocab() throws ParseException {

        ArrayList<VocabList>vocabLists = new ArrayList<VocabList>();
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE Vocab.progress=1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Date currentDate = new Date();
        if(cursor.moveToFirst()){
            do{
                if (( (simpleDateFormat.parse(cursor.getString(5)).getTime() - currentDate.getTime())
                        / (1000 * 60 * 60 * 24)  )<=1)
                {
                vocabList = new VocabList();
                    vocabList.setVocab_Id(cursor.getInt(0));
                    vocabList.setWord(cursor.getString(1));
                    vocabList.setDefinition(cursor.getString(2));
                    vocabList.setProgress(cursor.getInt(3));
                vocabLists.add(vocabList);
                }
                if(vocabLists.size()>= getDailyGoal()){
                    break;
                }

            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return vocabLists;

    }

    public static ArrayList<VocabList> getVocabsToStudy() throws ParseException {

        ArrayList<VocabList>vocabLists = new ArrayList<VocabList>();
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE Vocab.progress=0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Date currentDate = new Date();
        int num = cursor.getCount();
        if(num>= getDailyGoal()){
            int increment = num/getDailyGoal();
            int prevNum = 0;

            if(cursor.moveToFirst()){
                do{
                        vocabList = new VocabList();
                        vocabList.setVocab_Id(cursor.getInt(0));
                        vocabList.setWord(cursor.getString(1));
                        vocabList.setDefinition(cursor.getString(2));
                        vocabList.setProgress(cursor.getInt(3));
                        vocabLists.add(vocabList);
                        prevNum += increment;
                }while(cursor.moveToPosition(prevNum));
            }
        }else{
            if(cursor.moveToFirst()){
                do{
                    vocabList = new VocabList();
                    vocabList.setVocab_Id(cursor.getInt(0));
                    vocabList.setWord(cursor.getString(1));
                    vocabList.setDefinition(cursor.getString(2));
                    vocabList.setProgress(cursor.getInt(3));
                    vocabLists.add(vocabList);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return vocabLists;

    }

    public static int getDailyGoal() {
        return dailyGoal;
    }

    public static void setDailyGoal(int dailyGoal) {
        Vocab_Repo.dailyGoal = dailyGoal;
    }

    public static ArrayList<String> getRandomThreeDefinitions(int notThisOneID){

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> randomDefinitionsList = new ArrayList<String>();
        int num = cursor.getCount();
        int prevNum = notThisOneID;


        if(cursor.moveToFirst()){
            do{

                if(cursor.getInt(0)!= notThisOneID &&prevNum!= cursor.getPosition()){
                    randomDefinitionsList.add(cursor.getString(2));
                }
                prevNum = cursor.getPosition();
            }while((cursor.moveToNext())&&(randomDefinitionsList.size()<3));
        }

        db.close();
        cursor.close();
        return randomDefinitionsList;

    }

    public static ArrayList<Question> convertVocabListsToQuestions(ArrayList<VocabList> vocabs){

        ArrayList<Question> questions = new ArrayList<Question>();
        for(VocabList vocab: vocabs){
            questions.add(new Question(vocab));
        }
        return questions;
    }

}