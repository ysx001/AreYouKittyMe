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

public class VocabStudyActivity extends AppCompatActivity implements View.OnClickListener/*, View.OnTouchListener*/ {

    private Button choiceBtn1;
    private Button choiceBtn2;
    private Button choiceBtn3;
    private Button choiceBtn4;
    private Button knownBtn;
    private Button unknownBtn;
    private Button nextBtn;
    private TextView questionView;
    private Quiz quiz;
    public static final int Khaiki_Color = Color.rgb(240,230,140);
    public static final int Light_Goldenrod_Color = Color.rgb(238,221,130);
    private Button[] btns;
    private boolean answered;

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
        btns = new Button[]{this.choiceBtn1,this.choiceBtn2,this.choiceBtn3,this.choiceBtn4};
        this.unknownBtn = (Button)findViewById(R.id.NotKnown_button);
        this.questionView = (TextView) findViewById(R.id.Question_textView);
        this.nextBtn = (Button)findViewById(R.id.Next_button);






    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            quiz = new Quiz(VocabActivity.mode);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(quiz.getIfThereIsAnyVocab()){
            updateQuizInterface();
        }
    }



    public void updateQuizInterface(){
        this.questionView.setText(quiz.getCurrentQuestion().getVocab().getWord());
        this.choiceBtn1.setText(quiz.getCurrentQuestion().getAnswers()[0]);
        this.choiceBtn1.setBackgroundColor(Khaiki_Color);
        this.choiceBtn2.setText(quiz.getCurrentQuestion().getAnswers()[1]);
        this.choiceBtn2.setBackgroundColor(Khaiki_Color);
        this.choiceBtn3.setText(quiz.getCurrentQuestion().getAnswers()[2]);
        this.choiceBtn3.setBackgroundColor(Khaiki_Color);
        this.choiceBtn4.setText(quiz.getCurrentQuestion().getAnswers()[3]);
        this.choiceBtn4.setBackgroundColor(Khaiki_Color);
    }

    public void hightlightTheCorrectAnswer(){
        Button[] btns = new Button[]{choiceBtn1,choiceBtn2,choiceBtn3,choiceBtn4};
        btns[quiz.getCurrentQuestion().getIndexOfRightAnswer()].setBackgroundColor(Light_Goldenrod_Color);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.FirstOption_button: {
                choiceBtnClicked(0);
                break;
            }
            case R.id.SecondOption_button2: {
                choiceBtnClicked(1);
                break;
            }

            case R.id.ThirdOption_button3: {
                choiceBtnClicked(2);
                break;
            }
            case R.id.FourthOption_button4:{
                choiceBtnClicked(3);
                break;
            }
            case R.id.Known_button:{
                knownBtnClicked();
                break;
            }
            case R.id.NotKnown_button:{

                notKnownClicked();
                break;
            }
            case R.id.Next_button:{
                nextBtnClicked();
                break;
            }
        }
    }

    private void nextBtnClicked() {
        if(quiz.getIfThereIsAnyVocab()) {
            if (answered == true) {
                quiz.toNextQuestion();
                updateQuizInterface();
            }
        }else{
            goBackToVocabPage();
        }
    }

    private void goBackToVocabPage() {
        Class destActivity = VocabActivity.class;
        Context context = VocabStudyActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }



    private void notKnownClicked() {
        answered = true;
        hightlightTheCorrectAnswer();
    }

    private void knownBtnClicked() {
        quiz.processKnownVocab(quiz.getCurrentQuestion());
        if(quiz.getIfThereIsAnyVocab()){
            quiz.toNextQuestion();
            updateQuizInterface();
        }else{
            goBackToVocabPage();
        }

    }

    public void choiceBtnClicked(int index){
        if (this.answered == false) {

            if (quiz.checkCurrentAnswer(index)) {
                try {
                    quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                quiz.toNextQuestion();
                updateQuizInterface();
            } else {
                hightlightTheCorrectAnswer();
            }
        }
        answered = true;
    }



    /*

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {

            case R.id.FirstOption_button: {
                choiceBtnTouched(0);
                break;
            }
            case R.id.SecondOption_button2: {
                choiceBtnTouched(1);
                break;
            }

            case R.id.ThirdOption_button3: {
                choiceBtnTouched(2);
                break;
            }
            case R.id.FourthOption_button4:{
                choiceBtnTouched(3);
                break;
            }
            case R.id.Known_button:{
                choiceBtnTouched(4);
                break;
            }
            case R.id.NotKnown_button:{
                choiceBtnTouched(5);
                break;
            }
        }
    }
    */

}
