package com.example.android.areyoukittyme;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class AniTestActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    ImageView catAnimation;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani_test);
        // Setting up animation

        catAnimation = (ImageView) findViewById(R.id.miaomiaomiao);
      //  catAnimation.setImageResource(R.drawable.normal_cat_animation);

//        ((AnimationDrawable) catAnimation.getBackground()).start();

        Starter starter = new Starter();
        catAnimation.post(starter);

    }

    class Starter implements Runnable {
        public void run() {

            // Get the background, which has been compiled to an
            // AnimationDrawable
            // object.
            AnimationDrawable frameAnimation = (AnimationDrawable) catAnimation
                    .getBackground();

            frameAnimation.setOneShot(false);

            // Start the animation (looped playback by default).
            frameAnimation.start();
        }


    }
}
