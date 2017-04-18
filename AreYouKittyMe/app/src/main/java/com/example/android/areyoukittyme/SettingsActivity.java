package com.example.android.areyoukittyme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by Jingya on 4/13/17.
 */

public class SettingsActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText nameSetting;
    private EditText ageSetting;

    private EditText vocabSetting;
    private EditText stepsSetting;
    private EditText focusSetting;

    private Spinner vocabBookSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImage = (ImageView) findViewById(R.id.profileImage);
        nameSetting = (EditText) findViewById(R.id.nameSetting);
        ageSetting = (EditText) findViewById(R.id.ageSetting);

        vocabSetting = (EditText) findViewById(R.id.vocabSetting);
        stepsSetting = (EditText) findViewById(R.id.stepsSetting);
        focusSetting = (EditText) findViewById(R.id.focusSetting);

        vocabBookSetting = (Spinner) findViewById(R.id.vocabBookSetting);

        ageSetting.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = SettingsActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
