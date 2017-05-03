package com.example.android.areyoukittyme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;

/**
 * Created by haoyuxiong on 4/27/17.
 */

public class Studying_Activity extends AppCompatActivity{

    private SimpleCursorAdapter adapter;

    /*Called when first created*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database);

        SQLiteDatabase db = Vocab_DatabaseManager.getInstance().openDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM " + Vocab.TABLE+ " WHERE Vocab.progress=1",null);
        String[] columns = new String[] {
                Vocab.KEY_WORD,Vocab.KEY_DEFINITION
        };

        int[] TO = new int[] {
                R.id.word_yo,R.id.def_yo
        };

        adapter = new SimpleCursorAdapter(this,R.layout.activity_row_template, todoCursor,  columns, TO,0 );
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
