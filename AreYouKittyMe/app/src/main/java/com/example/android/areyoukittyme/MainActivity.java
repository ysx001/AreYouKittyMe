package com.example.android.areyoukittyme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.areyoukittyme.User.User;
import com.github.pwittchen.swipe.library.Swipe;
import com.github.pwittchen.swipe.library.SwipeListener;
import com.example.android.areyoukittyme.ItemFragments.AsparagusFragment;
import com.example.android.areyoukittyme.ItemFragments.AvocadoFragment;
import com.example.android.areyoukittyme.ItemFragments.BaconFragment;
import com.example.android.areyoukittyme.ItemFragments.CorndogFragment;
import com.example.android.areyoukittyme.ItemFragments.FishFragment;
import com.example.android.areyoukittyme.ItemFragments.HamburgerFragment;
import com.example.android.areyoukittyme.Store.Store;
import com.example.android.areyoukittyme.User.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static int NUM_PAGES = 6;
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;
    private Context context;
    private AccountHeader header;
    private IProfile profile;
    private Drawer drawer;
    private Swipe swipe;
    private TextView displayCatName;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    Point p;

    MediaPlayer mPlayer;



    private ImageView drawerToggler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //TODO: move new Store() to appropriate places
//        new Store();
//        new User();
        // Store the context variable
        context = MainActivity.this;

        mPlayer = MediaPlayer.create(context, R.raw.animal_cat_meow);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        displayCatName = (TextView) findViewById(R.id.cat_name_display);

        drawerToggler = (ImageView) findViewById(R.id.drawerToggler);

//        findViewById(R.id.miaomiaomiao).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.miaomiaomiao).setOnLongClickListener(new MyTouchListener());
        findViewById(R.id.miaomiaomiao).setOnClickListener(new MyClickListener());
        findViewById(R.id.main_content).setOnClickListener(new MyClickListener());

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        String catName = User.getName();
        displayCatName.setText(catName);

        profile = new ProfileDrawerItem().withName(catName).withIcon(GoogleMaterial.Icon.gmd_pets);

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
                                intent = new Intent(MainActivity.this, StatsDayActivity.class);
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

        // Connecting to Google Play Service
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        // Initialize GoogleAPIClient
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
        mPager = (ViewPager) findViewById(R.id.pager_temp);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connecting to google's backend
        // Moved to onCreate() for now
        //mApiClient.connect();

        profile.withName(User.getName());
        displayCatName.setText(User.getName());
        drawer.setSelection(0);
//        mApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Fitness.SensorsApi.remove( mApiClient, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
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
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    /**
     * This helper function is called every time the data source for step counter is found,
     * it then creates a SersorRequest object for requesting data from the step count
     * sensor.
     * @param dataSource
     * @param dataType
     */
    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {

        // SensorRequest attempts to find the step count every 3 sec
        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add(mApiClient, request, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e("GoogleFit", "SensorApi successfully added");
                        } else {
                            Log.e("GoogleFit", "adding status: " + status.getStatusMessage());
                        }
                    }
                });
    }

    /**
     * Triggered when GoogleApiClient is connected to Google.
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE )
                .setDataSourceTypes( DataSource.TYPE_RAW )
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    /**
     * The first time the user runs the application, the connection to the Fitness API will fail
     * because the usesr must authorize your app to access their fitness data.
     * This methods handles the situation
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( MainActivity.this, REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {
                Log.e( "GoogleFit", "sendingIntentException " + e.getMessage() );
            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }

    /**
     * There are two possible results:
     * 1. The user grants our app to their data
     * 2. He/she closes the dialog
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                // When receive RESULT_OK, attemp to connect to the Google API client
                if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                    mApiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void onDataPoint(DataPoint dataPoint) {
        for( final Field field : dataPoint.getDataType().getFields() ) {
            final Value value = dataPoint.getValue( field );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private final class MyTouchListener implements View.OnLongClickListener {
//        public boolean onLongClick(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                mPlayer.start();
//                ViewPager v = (ViewPager) findViewById(R.id.pager_temp);
//                int visibility = v.getVisibility();
//                if (visibility == View.VISIBLE) {
//                    v.setVisibility(View.INVISIBLE);
//                }
//                else {
//                    v.setVisibility(View.VISIBLE);
//                }
//
//                return true;
//            } else {
//                return false;
//            }
//        }

        @Override
        public boolean onLongClick(View v) {
                mPlayer.start();
                ViewPager vp = (ViewPager) findViewById(R.id.pager_temp);
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

    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        ImageView button = (ImageView) findViewById(R.id.miaomiaomiao);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
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
            ViewPager vp = (ViewPager) findViewById(R.id.pager_temp);
            if (vp.getVisibility() == View.VISIBLE) {
                vp.setVisibility(View.INVISIBLE);
            }
        }
    }
}
