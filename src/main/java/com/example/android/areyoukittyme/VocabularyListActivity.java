package com.example.android.areyoukittyme;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.android.areyoukittyme.NotStarted_Activity;
import com.example.android.areyoukittyme.Studid_Activity;
import com.example.android.areyoukittyme.Studying_Activity;

public class VocabularyListActivity extends TabActivity {

    TabHost tabHost;
    /**
     * Called when the activity is first created.
     *
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        spec = tabHost.newTabSpec("Studying"); // Create a new TabSpec using tab host
        spec.setIndicator("Studying"); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, Studying_Activity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        spec = tabHost.newTabSpec("Not Started"); // Create a new TabSpec using tab host
        spec.setIndicator("Not Started"); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, NotStarted_Activity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Studied"); // Create a new TabSpec using tab host
        spec.setIndicator("Studied"); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, Studid_Activity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);
        //set tab which one you want to open first time 0 or 1 or 2

        tabHost.setCurrentTab(1);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
                Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}