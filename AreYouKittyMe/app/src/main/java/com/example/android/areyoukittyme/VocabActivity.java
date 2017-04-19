package com.example.android.areyoukittyme;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class VocabActivity extends AppCompatActivity {

    private Button studyButton;
    private Button reviewButton;
    private ProgressBar progressBar;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studyButton = (Button)findViewById(R.id.vocab_study_button2);
        reviewButton = (Button)findViewById(R.id.vocab_Review_button);
        progressBar = (ProgressBar)findViewById(R.id.vocab_progressBar);

        Intent vocabIntent = getIntent();

        studyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);

                startActivity(startVocabActivityIntent);

            }

        });

        reviewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
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
}
