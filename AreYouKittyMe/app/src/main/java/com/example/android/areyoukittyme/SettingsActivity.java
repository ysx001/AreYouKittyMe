package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.areyoukittyme.User.User;
import com.google.gson.Gson;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Jingya on 4/13/17.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private User mUser;

    private EditText nameSetting;
    private EditText ageSetting;

    private EditText vocabSetting;
    private EditText stepsSetting;
    private EditText focusSetting;

    private Spinner vocabBookSetting;

    private Button settingDiscardBtn;
    private Button settingApplyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

        nameSetting = (EditText) findViewById(R.id.nameSetting);
        ageSetting = (EditText) findViewById(R.id.ageSetting);

        vocabSetting = (EditText) findViewById(R.id.vocabSetting);
        stepsSetting = (EditText) findViewById(R.id.stepsSetting);
        focusSetting = (EditText) findViewById(R.id.focusSetting);

        vocabBookSetting = (Spinner) findViewById(R.id.vocabBookSetting);

        settingDiscardBtn = (Button) findViewById(R.id.settingDiscardBtn);
        settingDiscardBtn.setOnClickListener(this);
        settingApplyBtn = (Button) findViewById(R.id.settingApplyBtn);
        settingApplyBtn.setOnClickListener(this);

        ageSetting.setEnabled(false);

        String[] items = new String[]{"SAT 6000", "French", "German", "Spanish"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        vocabBookSetting.setAdapter(adapter);

        loadCurrentSettings();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", SettingsActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    /**
     * Loads the stages of the cat.
     */
    private void loadCurrentSettings() {
        nameSetting.setText(mUser.getName());

        int age = mUser.getAge();
        String level = "";
        switch (age) {
            case 1: {
                level = "Kindergarden";
                break;
            }
            case 2: {
                level = "Primary School";
                break;
            }
            case 3: {
                level = "Middle School";
                break;
            }
            case 4: {
                level = "High School";
                break;
            }
            case 5: {
                level = "College";
                break;
            }
            case 6: {
                level = "Master";
                break;
            }
            case 7: {
                level = "PhD";
                break;
            }
            case 8: {
                level = "Professor";
                break;
            }
        }
        ageSetting.setText(level);

        vocabSetting.setText(String.valueOf(mUser.getVocabGoal()));
        stepsSetting.setText(String.valueOf(mUser.getStepsGoal()));
        focusSetting.setText(String.valueOf(mUser.getFocusGoal()));

        vocabBookSetting.setSelection(mUser.getVocabBookID());
    }

    /**
     * Applies the setting
     */
    private void applySettings() {
        mUser.setName(nameSetting.getText().toString());

        mUser.setVocabGoal(Integer.parseInt(vocabSetting.getText().toString()));
        mUser.setStepsGoal(Integer.parseInt(stepsSetting.getText().toString()));
        mUser.setFocusGoal(Integer.parseInt(focusSetting.getText().toString()));

        mUser.setVocabBookID(vocabBookSetting.getSelectedItemPosition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.settingDiscardBtn: {
                loadCurrentSettings();

                Context context = getApplicationContext();
                CharSequence text = "Changes discarded";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                break;
            }

            case R.id.settingApplyBtn: {


                Context context = getApplicationContext();
                CharSequence text = "Changes applied";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                applySettings();
                System.out.println("User name now is " + mUser.getName());
                break;
            }
        }
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

        System.out.println("Back Pressed User name now is " + mUser.getName());
        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        applySettings();
        intent.putExtra("User", mUser);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
