package com.example.android.areyoukittyme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_CursorAdapter_Studying;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;

/**
 * Created by haoyuxiong on 4/27/17.
 */

public class Studying_Activity extends AppCompatActivity{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database);

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM " + Vocab.TABLE + " WHERE Vocab.progress=1",null);

        ListView listView = (ListView) findViewById(R.id.list);
        Vocab_CursorAdapter_Studying adapter = new Vocab_CursorAdapter_Studying(this,todoCursor);
        listView.setAdapter(adapter);



    }



}
