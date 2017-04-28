package com.example.android.areyoukittyme;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Database;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VocabActivity extends AppCompatActivity {

    private Button studyButton;
    private Button reviewButton;
    private Button vocabButton;
    private Button input;
    private Dialog dialog;
    private ProgressBar progressBar;
    private int mProgressStatus = 0;
    public final static String[] books = new String[]{"French", "Spanish", "German", "SAT6000"};

    //private static Vocab_Database vocab_database;

    private Handler mHandler = new Handler();
    public static boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MediaPlayer mPlayer = MediaPlayer.create(VocabActivity.this, R.raw.book);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();

        studyButton = (Button)findViewById(R.id.vocab_study_button2);
        reviewButton = (Button)findViewById(R.id.vocab_Review_button);
        progressBar = (ProgressBar)findViewById(R.id.vocab_progressBar);
        vocabButton = (Button)findViewById(R.id.vocabulary_button);

        try{Vocab_Repo.getCurrentVocabCount();}
        catch(Exception e){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.simple_spinner);
            //Spinner spinner =  new Spinner(this, books, 0);
            //Button dialogButton = (Button) dialog.findViewById(R.id.btncross)


        }
        input = (Button)findViewById(R.id.input);

        Intent vocabIntent = getIntent();

        studyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);

                startActivity(startVocabActivityIntent);
                mode = false;

            }

        });

        input.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    Vocab_Repo.addAnEntireVocabListToTheDataBase(getAssets().open("French.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);

                startActivity(startVocabActivityIntent);
                mode = true;

            }

        });

        vocabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Vocab_Repo.addAnEntireVocabListToTheDataBase(getAssets().open("French.txt"));
                Context context = VocabActivity.this;
                Class destActivity = VocabularyListActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);

                startActivity(startVocabActivityIntent);
            }
        });

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
        Context context = VocabActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
