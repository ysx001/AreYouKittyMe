package com.example.android.areyoukittyme;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;
import com.example.android.areyoukittyme.logger.Log;
import com.google.gson.Gson;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VocabActivity extends AppCompatActivity {
    private User mUser;

    private Button studyButton;
    private Button reviewButton;
    private Button vocabButton;
    private Button input;
    private Dialog dialog;
    private ProgressBar progressBar;
    private int mProgressStatus = 0;
    public final static String[] books = new String[]{"French", "Spanish", "German"};
    public static int chosenBook = 0;

    //private static Vocab_Database vocab_database;
    private Handler mHandler = new Handler();
    public static boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

        MediaPlayer mPlayer = MediaPlayer.create(VocabActivity.this, R.raw.book);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();

        studyButton = (Button)findViewById(R.id.vocab_study_button2);
        reviewButton = (Button)findViewById(R.id.vocab_Review_button);
        progressBar = (ProgressBar)findViewById(R.id.vocab_progressBar);
        vocabButton = (Button)findViewById(R.id.vocabulary_button);

        //For presentation
        //progressBar.setProgress(30);

        try{
            Vocab_Repo.getAVocabWord();
        }catch(Exception e){
            LayoutInflater li = LayoutInflater.from(this);

            View promptsView = li.inflate(R.layout.dialog_spinner, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setView(promptsView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, books);

            //alertDialogBuilder.setTitle("");

            final AlertDialog alertDialog = alertDialogBuilder.create();

            final Spinner mSpinner= (Spinner) promptsView
                    .findViewById(R.id.spinner2);

            mSpinner.setAdapter(adapter);
            final Button mButton = (Button) promptsView
                    .findViewById(R.id.confirmButton);
            mButton.setText("Confirm");
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Vocab_Repo.addAnEntireVocabListToTheDataBase(getAssets().open(getFilename(mSpinner.getSelectedItemPosition())));
                        alertDialog.dismiss();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            });

            // reference UI elements from my_dialog_layout in similar fashion
            mSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());

            // show it
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }

        Intent vocabIntent = getIntent();

        studyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);
                startVocabActivityIntent.putExtra("User", mUser);
                startActivity(startVocabActivityIntent);
                mode = false;

            }

        });

        reviewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Context context = VocabActivity.this;
                Class destActivity = VocabStudyActivity.class;
                Intent startVocabActivityIntent = new Intent(context, destActivity);
                startVocabActivityIntent.putExtra("User", mUser);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e("newIntent", "new");
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent startingIntent = getIntent();
            mUser = startingIntent.getExtras().getParcelable("User");
            Log.e("user", "got");
        } catch (Exception e){
            Log.e("user", "ex");
        }

        int percent = 0;

        try {
            Vocab_Repo.getAVocabWord();
            Log.e("try", "try");
            int total = Vocab_Repo.getCurrentVocabCount();
            Log.e("k", String.valueOf(mUser.getVocabTotal()));
            Log.e("try", "try1");
            //percent = Vocab_Repo.getProgressPercent();
            percent = 100 * mUser.getVocabTotal() / total;
            Log.e("percent", String.valueOf(percent));
        }catch (Exception e) {
            Log.e("ex", "ex");
        }

        progressBar.setProgress(percent);
    }

    /**
     * Gets the filenames for the library that the user will be using.
     *
     * @param index The index of the files.
     * @return The string of the filename according to the index.
     */
    public String getFilename(int index){
        switch(index){
            case 0:
                return "French.txt";
            case 1:
                return "Spanish.txt";
            case 2:
                return "German.txt";
            //case 3:
                //return "SAT6000.txt";
        }

        return "French.txt";
    }

    /**
     * Called when menu items are selected
     * @param item The item selected
     * @return true when pressed.
     */
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
        intent.putExtra("User", mUser);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", VocabActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
