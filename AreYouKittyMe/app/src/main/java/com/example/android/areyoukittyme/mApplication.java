package com.example.android.areyoukittyme;


import android.app.Application;
import android.content.Context;

import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Database;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by haoyuxiong on 4/16/17.
 */

public class mApplication extends Application {
    private static Context context;
    private static Vocab_Database vocab_database;

    @Override
    public void onCreate(){
        System.out.println("Called1");
        super.onCreate();

        System.out.println("Called");
        this.context = this.getApplicationContext();
        vocab_database = new Vocab_Database();
        Vocab_DatabaseManager.initializeInstance(vocab_database);
        /*
        vocab_database = new Vocab_Database();
        Vocab_DatabaseManager.initializeInstance(vocab_database);
        try {
            Vocab_Repo.addAnEntireVocabListToTheDataBase(getAssets().open("French.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}