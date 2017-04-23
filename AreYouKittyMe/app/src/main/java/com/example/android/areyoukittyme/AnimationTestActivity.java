package com.example.android.areyoukittyme;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class AnimationTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        // Setting up animation
        ImageView catAnimation = (ImageView) findViewById(R.id.normal_cat_animation);
        ((AnimationDrawable) catAnimation.getBackground()).start();
    }

}
