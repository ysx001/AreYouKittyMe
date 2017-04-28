package com.example.android.areyoukittyme.Vocabs_Utilities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.android.areyoukittyme.R;

import org.w3c.dom.Text;

/**
 * Created by haoyuxiong on 4/26/17.
 */

public class Vocab_CursorAdapter_Studying extends ResourceCursorAdapter {

    public Vocab_CursorAdapter_Studying(Context context, Cursor c) {
        super(context,R.layout.activity_display_database, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_display_database, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView word = (TextView)view.findViewById(R.id.word_yo);
        TextView def = (TextView)view.findViewById(R.id.def_yo);
        word.setText(cursor.getString(1));
        def.setText(cursor.getString(2));
    }
}
