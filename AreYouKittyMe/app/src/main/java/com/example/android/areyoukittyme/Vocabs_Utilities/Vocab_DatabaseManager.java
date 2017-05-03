package com.example.android.areyoukittyme.Vocabs_Utilities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haoyuxiong on 4/13/17.
 */

public class Vocab_DatabaseManager {

    private Integer mOpenCounter = 0;

    private static Vocab_DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;


    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new Vocab_DatabaseManager();
            mDatabaseHelper = helper;

        }
    }


    public static synchronized Vocab_DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(Vocab_DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }



    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter+=1;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter-=1;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }




}
