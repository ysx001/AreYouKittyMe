package com.example.android.areyoukittyme;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.sip.SipAudioCall;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.areyoukittyme.Quiz_Utilities.Quiz;

import java.text.ParseException;

public class VocabStudyActivity extends AppCompatActivity {

    private Button choiceBtn1;
    private Button choiceBtn2;
    private Button choiceBtn3;
    private Button choiceBtn4;
    private Button knownBtn;
    private Button unknownBtn;
    private TextView questionView;
    private Quiz quiz;
    public static final int Khaiki_Color = Color.rgb(240,230,140);
    public static final int Light_Goldenrod_Color = Color.rgb(238,221,130);


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



        try {
            quiz = new Quiz(VocabActivity.mode);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(quiz.getIfThereIsAnyVocab()){
            updateQuizInterface();
        }


        this.choiceBtn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(quiz.checkCurrentAnswer(0)){
                    try {
                        quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    quiz.toNextQuestion();
                    updateQuizInterface();
                }else{
                    hightlightTheCorrectAnswer();
                }
            }
        });

        this.choiceBtn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(quiz.checkCurrentAnswer(1)){
                    try {
                        quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    quiz.toNextQuestion();
                    updateQuizInterface();
                }else{
                    hightlightTheCorrectAnswer();
                }
            }
        });

        this.choiceBtn3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(quiz.checkCurrentAnswer(2)){
                    try {
                        quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    quiz.toNextQuestion();
                    updateQuizInterface();
                }else{
                    hightlightTheCorrectAnswer();
                }
            }
        });

        this.choiceBtn4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(quiz.checkCurrentAnswer(3)){
                    try {
                        quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    quiz.toNextQuestion();
                    updateQuizInterface();
                }else{
                    hightlightTheCorrectAnswer();
                }
            }
        });


    }

    public void updateQuizInterface(){
        this.questionView.setText(quiz.getCurrentQuestion().getVocab().getWord());
        this.choiceBtn1.setText(quiz.getCurrentQuestion().getAnswers()[0]);
        this.choiceBtn2.setText(quiz.getCurrentQuestion().getAnswers()[1]);
        this.choiceBtn3.setText(quiz.getCurrentQuestion().getAnswers()[2]);
        this.choiceBtn4.setText(quiz.getCurrentQuestion().getAnswers()[3]);
    }

    public void hightlightTheCorrectAnswer(){
        Button[] btns = new Button[]{choiceBtn1,choiceBtn2,choiceBtn3,choiceBtn4};
        btns[quiz.getCurrentQuestion().getIndexOfRightAnswer()].setBackgroundColor(Light_Goldenrod_Color);

    }

    /*
    @Override
    protected void onResume(){
        super.onResume();;


    }*/



}
