package com.example.android.areyoukittyme;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class VocabStudyActivity extends AppCompatActivity {

    private Button studyButton;
    private Button reviewButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewstudy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //progressBar.set
    }
}
