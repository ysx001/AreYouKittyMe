package com.example.android.areyoukittyme;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.areyoukittyme.Quiz_Utilities.Quiz;

import java.text.ParseException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.android.areyoukittyme.R.color.colorPrimaryLight;

public class VocabStudyActivity extends AppCompatActivity implements View.OnClickListener {

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.choiceBtn1 = (Button)findViewById(R.id.FirstOption_button);
        this.choiceBtn1.setOnClickListener(this);
        this.choiceBtn2 = (Button)findViewById(R.id.SecondOption_button2);
        this.choiceBtn2.setOnClickListener(this);
        this.choiceBtn3 = (Button)findViewById(R.id.ThirdOption_button3);
        this.choiceBtn3.setOnClickListener(this);
        this.choiceBtn4 = (Button)findViewById(R.id.FourthOption_button4);
        this.choiceBtn4.setOnClickListener(this);
        this.knownBtn = (Button)findViewById(R.id.Known_button);
        this.knownBtn.setOnClickListener(this);
        btns = new Button[]{this.choiceBtn1,this.choiceBtn2,this.choiceBtn3,this.choiceBtn4};
        this.unknownBtn = (Button)findViewById(R.id.NotKnown_button);
        this.unknownBtn.setOnClickListener(this);
        this.questionView = (TextView) findViewById(R.id.Question_textView);
        this.nextBtn = (Button)findViewById(R.id.Next_button);
        this.nextBtn.setOnClickListener(this);


        System.out.println("Yo");
        /*
        choiceBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionView.setText("Processed");
                if (answered == false) {

                    if (quiz.checkCurrentAnswer(0)) {
                        try {
                            quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        quiz.toNextQuestion();
                        updateQuizInterface();
                    } else {
                        highlightTheCorrectAnswer();
                    }
                }
                answered = true;
            }
        });
        */


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
        }else{
            goBackToVocabPage();
        }
        answered = false;
    }


    public void updateQuizInterface(){
        this.questionView.setText(quiz.getCurrentQuestion().getVocab().getWord());
        System.out.println(quiz.getCurrentQuestion().getVocab().getWord());
        this.choiceBtn1.setText(quiz.getCurrentQuestion().getAnswers()[0]);
        this.choiceBtn1.setBackgroundColor(getColor(R.color.primary_light));
        this.choiceBtn2.setText(quiz.getCurrentQuestion().getAnswers()[1]);
        this.choiceBtn2.setBackgroundColor(getColor(R.color.primary_light));
        this.choiceBtn3.setText(quiz.getCurrentQuestion().getAnswers()[2]);
        this.choiceBtn3.setBackgroundColor(getColor(R.color.primary_light));
        this.choiceBtn4.setText(quiz.getCurrentQuestion().getAnswers()[3]);
        this.choiceBtn4.setBackgroundColor(getColor(R.color.primary_light));
    }

    public void highlightTheCorrectAnswer(){
        Button[] btns = new Button[]{choiceBtn1,choiceBtn2,choiceBtn3,choiceBtn4};
        btns[quiz.getCurrentQuestion().getIndexOfRightAnswer()].setBackgroundColor(getColor(R.color.colorAccentLight));

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.FirstOption_button: {
                choiceBtnClicked(0);
                System.out.println("adfkladj");
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
        if (answered == true) {
            if (quiz.getIfThereIsAnyVocab()) {

                quiz.toNextQuestion();
                updateQuizInterface();

            } else {
                goBackToVocabPage();
            }
            answered = false;
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
        if (answered == false){
            quiz.checkCurrentAnswer(0);
            highlightTheCorrectAnswer();
            answered = true;
        }
    }

    private void knownBtnClicked() {
        if (answered == false){
        System.out.println("I know");
        quiz.processKnownVocab(quiz.getCurrentQuestion());
        if(quiz.getIfThereIsAnyVocab()){
            quiz.toNextQuestion();
            updateQuizInterface();
        }else{
            goBackToVocabPage();
        }}

    }

    public void choiceBtnClicked(int index){
        if (answered==false) {

            answered = true;
            if (quiz.checkCurrentAnswer(index)) {
                try {
                    quiz.processCorrectAnswer(quiz.getCurrentQuestion());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                quiz.toNextQuestion();
                updateQuizInterface();
                answered = false;
            } else {
                try {
                    quiz.processYoAnswer(quiz.getCurrentQuestion());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                highlightTheCorrectAnswer();
            }
        }

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
