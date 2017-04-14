package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private AccountHeader header;
    private Drawer drawer;

    private TextView displayCatName;
    private Button statsButton;
    private Button vocabButton;
    private Button storeButton;
    private Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Store the context variable
        context = MainActivity.this;

        displayCatName = (TextView) findViewById(R.id.cat_name_display);
        statsButton = (Button) findViewById(R.id.stats_button);
        vocabButton = (Button) findViewById(R.id.vocab_button);
        storeButton = (Button) findViewById(R.id.store_button);
        timerButton = (Button) findViewById(R.id.timer_button);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        String catName = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
        displayCatName.setText(catName);


        // Setting an OnClickLister for the statsButton
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StatsActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the vocabButton
        vocabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = VocabActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the storeButton
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StoreActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        // Setting an OnClickLister for the timerButton
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = TimerActivity.class;

                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);

                startActivity(startMainActivityIntent);

            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StoreActivity.class;
                // create Intent that will start the activity
                Intent startMainActivityIntent = new Intent(context, destActivity);
                startActivity(startMainActivityIntent);
            }
        });

        final IProfile profile = new ProfileDrawerItem().withName(catName).withIcon(GoogleMaterial.Icon.gmd_pets);

        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.profile_background)
                .addProfiles(profile)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

                        Class destActivity = SettingsActivity.class;
                        Intent settingsIntent = new Intent(context, destActivity);
                        startActivity(settingsIntent);

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_dashboard).withIcon(GoogleMaterial.Icon.gmd_dashboard).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_stats).withIcon(GoogleMaterial.Icon.gmd_timeline).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_store).withIcon(GoogleMaterial.Icon.gmd_store).withIdentifier(2),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_vocab).withIcon(GoogleMaterial.Icon.gmd_assignment).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_timer).withIcon(GoogleMaterial.Icon.gmd_hourglass_empty).withIdentifier(4),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_feedback).withIcon(GoogleMaterial.Icon.gmd_feedback).withIdentifier(6)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainActivity.this, StatsActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(MainActivity.this, StoreActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(MainActivity.this, VocabActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(MainActivity.this, TimerActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 6) {
                            }

                            if (intent != null) {
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

}
