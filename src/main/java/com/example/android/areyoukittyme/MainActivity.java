package com.example.android.areyoukittyme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.areyoukittyme.Service.DayAlarmReceiver;
import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.logger.Log;
import com.example.android.areyoukittyme.logger.LogWrapper;
import com.example.android.areyoukittyme.logger.MessageOnlyLogFilter;
import com.example.android.areyoukittyme.ItemFragments.AsparagusFragment;
import com.example.android.areyoukittyme.ItemFragments.AvocadoFragment;
import com.example.android.areyoukittyme.ItemFragments.BaconFragment;
import com.example.android.areyoukittyme.ItemFragments.CorndogFragment;
import com.example.android.areyoukittyme.ItemFragments.FishFragment;
import com.example.android.areyoukittyme.ItemFragments.HamburgerFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    // key for parcable
    private User mUser;

    private static int NUM_PAGES = 6;

    private Context context;

    private AccountHeader header;
    private IProfile profile;
    private Drawer drawer;

    private TextView moneyDisplay;
    private CircularProgressBar healthProgress;
    private CircularProgressBar moodProgress;

    private TextView displayCatName;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    Point p;

    MediaPlayer mPlayer;

    private ImageView drawerToggler;

    private Button testDead;

    public User getmUser() {
        return mUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

        // This method sets up our custom logger, which will print all log messages to the device
        // screen, as well as to adb logcat.
        initializeLogging();

        buildFitnessClient();

        readData();


        // Store the context variable
        context = MainActivity.this;

        mPlayer = MediaPlayer.create(context, R.raw.animal_cat_meow);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        moneyDisplay = (TextView) findViewById(R.id.moneyDisplay);
        healthProgress = (CircularProgressBar) findViewById(R.id.healthProgress);
        moodProgress = (CircularProgressBar) findViewById(R.id.moodProgress);

        displayCatName = (TextView) findViewById(R.id.cat_name_display);

        drawerToggler = (ImageView) findViewById(R.id.drawerToggler);

        testDead = (Button) findViewById(R.id.test_dead);

        // Setting an OnClickLister for the testing dead activity
        testDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.newDay();
                moneyDisplay.setText(String.valueOf(mUser.getCash()));
                healthProgress.setProgressWithAnimation(mUser.getHealth());
                moodProgress.setProgressWithAnimation(mUser.getMood());
                if (mUser.getHealth() <= 0) {
                    switchDie();
                }
            }
        });

        findViewById(R.id.click_assist).setOnLongClickListener(new MyLongClickListener());
        findViewById(R.id.click_assist).setOnClickListener(new MyClickListener());

        findViewById(R.id.main_content).setOnClickListener(new MyClickListener());
        findViewById(R.id.leftArrow).setOnClickListener(new MyClickListener());
        findViewById(R.id.rightArrow).setOnClickListener(new MyClickListener());
        // Setting up animation
        ImageView catAnimation = (ImageView) findViewById(R.id.miaomiaomiao);
        ((AnimationDrawable) catAnimation.getBackground()).start();
        catAnimation.setOnClickListener(new MyClickListener());
        findViewById(R.id.main_content).setOnClickListener(new MyClickListener());
        findViewById(R.id.leftArrow).setOnClickListener(new MyClickListener());
        findViewById(R.id.rightArrow).setOnClickListener(new MyClickListener());

        catAnimation.setOnTouchListener(new MyTouchListener());


        String catName = mUser.getName();
        displayCatName.setText(catName);

        // If health is zero, the cat dies.
        if (mUser.getHealth() == 0) {
            switchDie();
        }

        profile = new ProfileDrawerItem().withName(catName).withIcon(GoogleMaterial.Icon.gmd_pets);
        profile = new ProfileDrawerItem().withName(catName).withIcon(R.drawable.pawprint);

        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.profile_background)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

                        Class destActivity = SettingsActivity.class;
                        Intent settingsIntent = new Intent(context, destActivity);
                        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        settingsIntent.putExtra("User", mUser);
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
                            boolean isSetting = false;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainActivity.this, StatsDayActivity.class);
                                readData();
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                intent.putExtra("User", mUser);

                                if (drawerItem.getIdentifier() == 2) {
                                    startActivityForResult(intent, 1);
                                }
                                else {
                                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(intent);
                                }
                            }
                        }
                        return false;
                    }
                })
                .build();

        drawerToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                }
            }
        });

        scheduleAlarm();

        mPager = (ViewPager) findViewById(R.id.pager_temp);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    /**
     * When cat dies, the app goes into another activity.
     */
    private void switchDie() {
        Intent intent = new Intent(this, DeadActivity.class);
        intent.putExtra("User", mUser);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent startingIntent = getIntent();
            mUser = startingIntent.getExtras().getParcelable("User");
        } catch (Exception e){
        }

        moneyDisplay.setText(String.valueOf(mUser.getCash()));
        healthProgress.setProgressWithAnimation(mUser.getHealth());
        moodProgress.setProgressWithAnimation(mUser.getMood());

        profile.withName(mUser.getName());
        displayCatName.setText(mUser.getName());
        header.updateProfile(profile);
        drawer.setSelection(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "stop");
        SharedPreferences mPrefs = getSharedPreferences("userPref", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                mUser = data.getExtras().getParcelable("User");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            startActivity(intent);
        }
    }

    /**
     * Schedules the alarm at a specific time
     */
    private void scheduleAlarm() {

        Intent intent = new Intent(getApplicationContext(), DayAlarmReceiver.class);
        intent.putExtra("User", mUser);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, DayAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int date = calendar.get(Calendar.DAY_OF_YEAR) + 1;
        calendar.set(Calendar.DAY_OF_YEAR, date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long firstMillis = calendar.getTimeInMillis();

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_DAY, pIntent);
    }


    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Item price", "80");
                return true;
            } else {
                return false;
            }
        }
    }




    // Google Fit API
    public static final String TAG = "GoogleFit";
    private GoogleApiClient mClient = null;

    public static long stepCount = 0;


    /**
     * Build a {@link GoogleApiClient} to authenticate the user and allow the application
     * to connect to the Fitness APIs. The included scopes should match the scopes needed
     * by your app (see the documentation for details).
     * Use the {@link GoogleApiClient.OnConnectionFailedListener}
     * to resolve authentication failures (for example, the user has not signed in
     * before, or has multiple accounts and must specify which account to use).
     */
    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                com.example.android.areyoukittyme.logger.Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.  What to do?
                                // Subscribe to some data sources!
                                subscribe();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    com.example.android.areyoukittyme.logger.Log.w(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    com.example.android.areyoukittyme.logger.Log.w(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        com.example.android.areyoukittyme.logger.Log.w(TAG, "Google Play services connection failed. Cause: " +
                                result.toString());
                        Snackbar.make(
                                MainActivity.this.findViewById(R.id.main_content),
                                "Exception while connecting to Google Play services: " +
                                        result.getErrorMessage(),
                                Snackbar.LENGTH_INDEFINITE).show();
                    }
                })
                .build();
    }

    /**
     * Record step data by requesting a subscription to background step data.
     */
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            if (status.getStatusCode()
                                    == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
                                com.example.android.areyoukittyme.logger.Log.i(TAG, "Existing subscription for activity detected.");
                            } else {
                                com.example.android.areyoukittyme.logger.Log.i(TAG, "Successfully subscribed!");
                            }
                        } else {
                            com.example.android.areyoukittyme.logger.Log.w(TAG, "There was a problem subscribing.");
                        }
                    }
                });
    }

    private void readData() {
        new VerifyDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_read_data) {
            readData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  Initialize a custom log class that outputs both to in-app targets and logcat.
     */
    private void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        com.example.android.areyoukittyme.logger.Log.setLogNode(logWrapper);
        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        com.example.android.areyoukittyme.logger.Log.i(TAG, "Ready");
    }

    /**
     * Read the current daily step total, computed from midnight of the current day
     * on the device's current timezone.
     */
    private class VerifyDataTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {

            long total = 0;

            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
            DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                total = totalSet.isEmpty()
                        ? 0
                        : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                stepCount = total;
                mUser.setSteps((int) total);
            } else {
                com.example.android.areyoukittyme.logger.Log.w(TAG, "There was a problem getting the step count.");
            }

            com.example.android.areyoukittyme.logger.Log.i(TAG, "Total steps: " + total);

            return null;
        }
    }
    private final class MyLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
                mPlayer.start();
                RelativeLayout vp = (RelativeLayout) findViewById(R.id.popup_container);
                int visibility = vp.getVisibility();
                if (visibility == View.VISIBLE) {
                    vp.setVisibility(View.INVISIBLE);
                }
                else {
                    vp.setVisibility(View.VISIBLE);
                }

                return true;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new FishFragment();
                case 1: return new AsparagusFragment();
                case 2: return new AvocadoFragment();
                case 3: return new BaconFragment();
                case 4: return new HamburgerFragment();
                case 5: return new CorndogFragment();
                default: return new MainPopupFragment();
            }
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private final class MyClickListener implements View.OnClickListener {
        public void onClick(View v) {
            RelativeLayout popup = (RelativeLayout) findViewById(R.id.popup_container);
            ViewPager vp = (ViewPager) findViewById(R.id.pager_temp);
            if (v.getId() == R.id.leftArrow) {

                vp.setCurrentItem(vp.getCurrentItem()-1, true);
            }
            else if (v.getId() == R.id.rightArrow) {
                vp.setCurrentItem(vp.getCurrentItem()+1, true);
            }
            else if (v.getId() == R.id.asparagusImage) {

            }
            else {
                if (popup.getVisibility() == View.VISIBLE) {
                    popup.setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public CircularProgressBar getMoodProgress() {
        return moodProgress;
    }

    public void setMoodProgress(CircularProgressBar moodProgress) {
        this.moodProgress = moodProgress;
    }

    public CircularProgressBar getHealthProgress() {
        return healthProgress;
    }

    public void setHealthProgress(CircularProgressBar healthProgress) {
        this.healthProgress = healthProgress;
    }
}
