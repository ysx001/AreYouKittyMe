package com.example.android.areyoukittyme.Vocabs_Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.areyoukittyme.Quiz_Utilities.Question;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
    private static int dailyGoal= 50;
    public final static Random random = new Random();



    public static String createTable() {
        return "CREATE TABLE " + Vocab.TABLE + "("
                + Vocab._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Vocab.KEY_WORD + " TEXT,"
                + Vocab.KEY_DEFINITION + " TEXT,"
                + Vocab.KEY_PROGRESS + " INT,"
                + Vocab.KEY_DAY + " TEXT," +
                  Vocab.KEY_DATE+ " TEXT)";
    }


    public static int insert(Vocab vocab) {
        int vocabId;
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Vocab._ID, vocab.getVocab_Id());
        values.put(Vocab.KEY_WORD, vocab.getWord());
        values.put(Vocab.KEY_DEFINITION, vocab.getDefinition());
        values.put(Vocab.KEY_PROGRESS, vocab.getProgress());
        values.put(Vocab.KEY_DAY, vocab.getDay());
        values.put(Vocab.KEY_DATE,vocab.getDate());

        // Inserting Row
        db.insert(Vocab.TABLE, null, values);
        Vocab_DatabaseManager.getInstance().closeDatabase();

        return 0;
    }


    public static void delete() {
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        db.delete(Vocab.TABLE, null, null);
        Vocab_DatabaseManager.getInstance().closeDatabase();
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
        Vocab_DatabaseManager.getInstance().closeDatabase();
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
        Vocab_DatabaseManager.getInstance().closeDatabase();
        return vocabLists;
    }

    public static int getCurrentVocabCount(){
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i = cursor.getCount();
        cursor.close();
        Vocab_DatabaseManager.getInstance().closeDatabase();


        return i;

    }

    public static int getProgressPercent(){
        int i = getCurrentVocabCount();
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + Vocab.TABLE + "WHERE Vocab.progress = 0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int j =cursor.getCount();
        cursor.close();
        Vocab_DatabaseManager.getInstance().closeDatabase();
        return (j/i)*100;

    }

    public static Vocab getAVocabByWord(String criteria, String word){

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery;
        if (criteria.equals(Vocab.KEY_DAY)||criteria.equals(Vocab._ID)||criteria.equals(Vocab.KEY_PROGRESS)){
             selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE "+criteria+"="+word;
        }else{
             selectQuery = "SELECT  * FROM " + Vocab.TABLE + " WHERE "+criteria+"='"+word +"'";}
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();}
        else{
            Vocab_DatabaseManager.getInstance().closeDatabase();
            cursor.close();
            return null;
        }

        Vocab vocabWanted = new Vocab(cursor.getInt(cursor.getColumnIndex(Vocab._ID)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_WORD)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DEFINITION)),
                cursor.getColumnIndex(Vocab.KEY_PROGRESS),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DAY)),
                cursor.getString(cursor.getColumnIndex(Vocab.KEY_DATE)));

        cursor.close();
        Vocab_DatabaseManager.getInstance().closeDatabase();


        return vocabWanted;

    }

    public static void  getAVocabWord(){

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        String selectQuery;

            selectQuery = "SELECT  * FROM " + Vocab.TABLE ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        cursor.getString(1);


        cursor.close();
        Vocab_DatabaseManager.getInstance().closeDatabase();


    }

    public static void updateAWordOnItsProgress(int newState, int id, int currentState){
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();

        System.out.printf("Current %d Next %d\n", currentState,newState);
        String selectQuery = "SELECT * FROM "+ Vocab.TABLE +
                " WHERE  Vocab._ID =" + id;
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();

        ContentValues newValues = new ContentValues();

        newValues.put(Vocab._ID,id);
        newValues.put(Vocab.KEY_WORD, cursor.getString(1));
        newValues.put(Vocab.KEY_DEFINITION, cursor.getString(2));
        newValues.put(Vocab.KEY_PROGRESS, newState);
        if(currentState==0&&newState==1){
            newValues.put(Vocab.KEY_DAY, "1");
            }
        else if (currentState==1&&newState==2){
            newValues.put(Vocab.KEY_DAY,cursor.getString(4));
        }
        newValues.put(Vocab.KEY_DATE,cursor.getString(5));
        cursor.close();

        db.update(Vocab.TABLE,newValues,"_id="+id,null);

         selectQuery = "SELECT * FROM "+ Vocab.TABLE +
                " WHERE  Vocab._ID =" + id;
         cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        System.out.println("After Update we have state "+ cursor.getInt(3));


        /*
        System.out.println("Update yo to studying done");
        if(currentState==0 && newState == 1){
            //System.out.println("Update to studying done");
            System.out.println("Gone through");
            //Vocab_DatabaseManager.getInstance().getVocabGeneralProgress().studyOne();
            System.out.println("Not working ");

            selectQuery = "UPDATE "+ Vocab.TABLE +
                    " SET " + Vocab.KEY_DAY + " = " + "1" + " WHERE " + Vocab._ID + "=?";

            db.rawQuery(selectQuery,new String[]{id+""});

            selectQuery = "SELECT * FROM "+ Vocab.TABLE +
                    " WHERE  Vocab._ID =" + id;
            System.out.println("Update to studying done");
            //,new String[]{Integer.toString(id)});

            System.out.println("After Update we have state "+ cursor.getInt(3));

        }else if(currentState==1 && newState==2){


            Vocab_DatabaseManager.getInstance().getVocabGeneralProgress().finishStudyOne();
        }*/



        Vocab_DatabaseManager.getInstance().closeDatabase();
    }



    public static void updateAWordOnItsReviewPeriod(int id) throws ParseException {
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();

        Vocab vocab = getAVocabByWord(Vocab._ID,String.format("%d",id));

        int dateDifference = dateIncrement[Integer.parseInt(vocab.getDay())];

        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime()+dateDifference*MILLISECONDS_PER_DAY);
        String newDate = simpleDateFormat.format(currentDate);
        ContentValues values = new ContentValues();

        values.put(Vocab._ID, vocab.getVocab_Id());
        values.put(Vocab.KEY_WORD, vocab.getWord());
        values.put(Vocab.KEY_DEFINITION, vocab.getDefinition());
        if(Integer.parseInt(vocab.getDay())>=7){
            values.put(Vocab.KEY_PROGRESS, 2);
        }else{
            values.put(Vocab.KEY_PROGRESS, vocab.getProgress());
        }
        values.put(Vocab.KEY_DAY, String.format("%d",Integer.parseInt(vocab.getDay()))+1);
        values.put(Vocab.KEY_DATE,newDate);

        try{
            db.update(Vocab.TABLE,values, Vocab._ID+"=?",new String[]{String.format("%d",id)});
        }catch(Exception whatever){

        }finally{
        Vocab_DatabaseManager.getInstance().closeDatabase();
        }

    }

    public static void deleteAllRows(){
        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        db.execSQL("DELETE FROM "+Vocab.TABLE);
        Vocab_DatabaseManager.getInstance().closeDatabase();

    }

    public static void addAnEntireVocabListToTheDataBase(InputStream in) throws UnsupportedEncodingException {

        deleteAllRows();
        String currentDate = simpleDateFormat.format(new Date());
        Vocab vocab;

        ArrayList<ArrayList<String>> vocabArrays = Vocab_Data_Csv_Process.processInData(
                                        Vocab_Data_Csv_Process.ReadInVocabData(in));


        int j = 0;
        for(ArrayList<String> i: vocabArrays){
            //System.out.println(i.size());
            vocab = new Vocab(j, i.get(0), i.get(1),0, "0", currentDate );
            insert(vocab);
            //System.out.println(j);
            j+= 1;


        }
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
        Vocab_DatabaseManager.getInstance().closeDatabase();
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
        //System.out.println("We have "+vocabLists.size());
        Vocab_DatabaseManager.getInstance().closeDatabase();
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
        //System.out.println("We have yo  " + num);
        int prevNum = notThisOneID;


        //if(cursor.moveToFirst()){
            //do{
        while((cursor.moveToPosition(random.nextInt(num)))&&(randomDefinitionsList.size()<3)){
                //System.out.println("Prev  "+prevNum);
                //System.out.println("current  "+ cursor.getPosition());
                //System.out.println("id get" + cursor.getInt(0));
                //System.out.println("Not this one" + notThisOneID);

                if(cursor.getInt(0)!= notThisOneID &&prevNum!= cursor.getPosition()){
                    randomDefinitionsList.add(cursor.getString(2));
                }
                prevNum = cursor.getPosition();
            };
        //}

        for(String i: randomDefinitionsList){
            System.out.println(i+" ");
        }
        System.out.print("\n");
        //System.out.println("We have " + randomDefinitionsList.size());
        Vocab_DatabaseManager.getInstance().closeDatabase();
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