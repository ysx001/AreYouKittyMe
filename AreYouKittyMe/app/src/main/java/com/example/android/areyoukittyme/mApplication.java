package com.example.android.areyoukittyme;

import android.app.Application;
import android.content.Context;

import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Database;
import org.polaric.colorful.Colorful;

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Quicksand/Quicksand-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.RED)
                .accentColor(Colorful.ThemeColor.BLUE)
                .translucent(false)
                .dark(true);

        Colorful.init(this);
    }

    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}