package com.example.android.areyoukittyme.Vocabs_Utilities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.areyoukittyme.mApplication;

/**
 * Created by haoyuxiong on 4/13/17.
 */

public class Vocab_Database extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Vocabs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = Vocab_Database.class.getSimpleName().toString();



    public Vocab_Database() {
        super(mApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Vocab_Repo.createTable());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Vocab");
        onCreate(db);
    }


}
