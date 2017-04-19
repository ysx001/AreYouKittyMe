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
import android.widget.TextView;

public class VocabStudyActivity extends AppCompatActivity {

    private Button choiceBtn1;
    private Button choiceBtn2;
    private Button choiceBtn3;
    private Button choiceBtn4;
    private Button knownBtn;
    private Button unknownBtn;
    private TextView questionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewstudy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.choiceBtn1 = (Button)findViewById(R.id.FirstOption_button);
        this.choiceBtn2 = (Button)findViewById(R.id.SecondOption_button2);
        this.choiceBtn3 = (Button)findViewById(R.id.ThirdOption_button3);
        this.choiceBtn4 = (Button)findViewById(R.id.FourthOption_button4);
        this.knownBtn = (Button)findViewById(R.id.Known_button);
        this.unknownBtn = (Button)findViewById(R.id.NotKnown_button);
        this.questionView = (TextView) findViewById(R.id.Question_textView);

    }

    @Override
    protected void onResume(){
        super.onResume();;


    }

}
