package com.example.android.areyoukittyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.areyoukittyme.User.User;

public class DeadActivity extends AppCompatActivity implements View.OnClickListener{

    private Button revive;
    private Button readopt;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

        revive = (Button) findViewById(R.id.revive_cat);
        revive.setOnClickListener(this);
        readopt = (Button) findViewById(R.id.readopt_cat);
        readopt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.revive_cat: {
                intent = new Intent(DeadActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("User", mUser);
                break;
            }
            case R.id.readopt_cat: {
                intent = new Intent(DeadActivity.this, AdoptActivity.class);
                break;
            }
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
