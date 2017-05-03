package com.example.android.areyoukittyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.areyoukittyme.Item.Item;
import com.example.android.areyoukittyme.Store.Store;
import com.example.android.areyoukittyme.User.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StoreActivity extends AppCompatActivity {

    private User mUser;

    public static ArrayList<Integer> priceList;
    public static ArrayList<TextView> amountListTextView;
    public static ArrayList<Integer> amountListInt;
    public static int total = 0;
    private Context context;
    public static Snackbar mySnackbar;
    public static TextView catCash;
    public static TextView totalText;
    MediaPlayer mPlayer;
    Animation shake;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get parcelable user
        Intent startingIntent = getIntent();
        mUser = startingIntent.getExtras().getParcelable("User");

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        setContentView(R.layout.activity_store);
        priceList = new ArrayList<>();
        amountListTextView = new ArrayList<>();
        amountListInt = new ArrayList<>();
        total = 0;
        context = StoreActivity.this;

        mPlayer = MediaPlayer.create(context, R.raw.bell_small_001);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();

        // setting up button clicks and snackbars
        findViewById(R.id.checkoutBtn).setOnClickListener(new MyClickListener());
        View coordLayout = findViewById(R.id.coordinatorLayout);
        mySnackbar = Snackbar.make(coordLayout, "Not Enough CatCash", BaseTransientBottomBar.LENGTH_SHORT);

        populateStore();

        catCash = (TextView) findViewById(R.id.catCash);
        catCash.setText(String.format("CatCash: %d", mUser.getCash()));
        totalText = (TextView) findViewById(R.id.totalAmount);
    }

    /**
     * When item is selected from the menu bar
     *
     * @param item Item selected
     * @return
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
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    /**
     * When back is pressed, go back to MainActivity
     */
    @Override
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = StoreActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("User", mUser);
        startActivity(intent);
    }

    /**
     * Checks out the items from store.
     */
    private void checkout() {
        MediaPlayer mPlayer = MediaPlayer.create(StoreActivity.this, R.raw.cash_register);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();
        if (total > mUser.getCash()) {
            Toast.makeText(getApplicationContext(), "Not Enough CatCash", Toast.LENGTH_SHORT).show();
        }
        else {
            mUser.setCash(mUser.getCash() - total);
            catCash.setText(String.format("CatCash: %d", mUser.getCash()));
            total = 0;
            totalText.setText(String.format("Total: %d", total));
            for (int i = 0; i < StoreActivity.amountListTextView.size(); i++) {
                StoreActivity.amountListTextView.get(i).setText(String.valueOf(0));
            }
            mUser.userCheckout(StoreActivity.amountListInt, StoreActivity.priceList);
        }
    }

    /**
     * Populates the store GUI with the item stored in class Store.
     */
    private void populateStore() {
        LinearLayout storeContainer = (LinearLayout) findViewById(R.id.storeContainer);
        storeContainer.setPadding(20, 0, 20, 0);

        LinearLayout hContainer = new LinearLayout(this);

        String tagPos;
        String tagNeg;
        for (int i = 0; i < Store.getItemList().size(); i++) {
            Item item = Store.getItemList().get(i);
            if (i%2 == 0) {
                // creating hcontainers
                hContainer = new LinearLayout(this);
                hContainer.setOrientation(LinearLayout.HORIZONTAL);
            }
            // create subcontainer
            LinearLayout subContainer = new LinearLayout(this);
            subContainer.setOrientation(LinearLayout.VERTICAL);
            subContainer.setGravity(Gravity.CENTER);
            //TODO: how to find the middle. try not to hard code the width of subcontainer
            subContainer.setLayoutParams(new LinearLayout.LayoutParams(680,700));
            // setting image
            ImageView itemIcon = new ImageView(this);
            itemIcon.setImageResource(item.getId());
            itemIcon.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            subContainer.addView(itemIcon);
            TextView priceTag = new TextView(this);
            priceTag.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            priceTag.setText(String.valueOf(item.getPrice()));
            this.priceList.add(item.getPrice());
            subContainer.addView(priceTag);
            // amount container
            LinearLayout amountContainer = new LinearLayout(this);
            amountContainer.setGravity(Gravity.CENTER);
            amountContainer.setOrientation(LinearLayout.HORIZONTAL);
            Button minusBtn = new Button(this);
            minusBtn.setWidth(150);
            minusBtn.setText("-");
            Button plusBtn = new Button(this);
            plusBtn.setWidth(150);
            plusBtn.setText("+");
            TextView amount = new TextView(this);
            amount.setText("0");
            amount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            amount.setWidth(100);
            amountContainer.addView(minusBtn);
            amountContainer.addView(amount);
            this.amountListTextView.add(amount);

            Integer.parseInt(amount.getText().toString());
            this.amountListInt.add(Integer.parseInt(amount.getText().toString()));
            amountContainer.addView(plusBtn);
            minusBtn.setOnClickListener(new MyClickListener());
            plusBtn.setOnClickListener(new MyClickListener());
            plusBtn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
            minusBtn.setLayoutParams(new LinearLayout.LayoutParams(200, 170));
            plusBtn.setLayoutParams(new LinearLayout.LayoutParams(200, 170));

            tagPos = String.format("+%d", i);
            tagNeg = String.format("-%d", i);
            minusBtn.setTag(tagNeg);
            plusBtn.setTag(tagPos);

            subContainer.addView(amountContainer);

            hContainer.addView(subContainer);
            if (i%2 == 0) {
                storeContainer.addView(hContainer);
                TextView temp = new TextView(this);
                temp.setText("");
                temp.setHeight(0);
                storeContainer.addView(temp);

            }
        }
    }

    private String incrementString(String s, int a) {
        int intStr = Integer.parseInt(s);
        intStr += a;
        return String.valueOf(intStr);
    }

    /**
     * Handles clicking
     */
    private final class MyClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            String tag = (String) v.getTag();
            char sign =  '\u0000' ;
            int pos = -1;
            if (tag != null) {
                sign = tag.charAt(0);
                pos = Integer.parseInt(tag.substring(1));
            }

            if (v.getId() == R.id.checkoutBtn) {
                checkout();
            }
            else if (sign == '+') {
                String incremented = incrementString(StoreActivity.amountListTextView.get(pos).getText().toString(), 1);
                StoreActivity.amountListTextView.get(pos).setText(incremented);
                StoreActivity.amountListInt.set(pos, StoreActivity.amountListInt.get(pos)+1);
                StoreActivity.total += StoreActivity.priceList.get(pos);
                StoreActivity.amountListTextView.get(pos).startAnimation(shake);
                TextView t = (TextView) findViewById(R.id.totalAmount);
                t.setText(String.format("Total: %d", StoreActivity.total));
            }
            else if (sign == '-') {
                if (Integer.parseInt(StoreActivity.amountListTextView.get(pos).getText().toString()) != 0) {
                    String incremented = incrementString(StoreActivity.amountListTextView.get(pos).getText().toString(), -1);
                    StoreActivity.amountListTextView.get(pos).setText(incremented);
                    StoreActivity.amountListInt.set(pos, StoreActivity.amountListInt.get(pos)-1);
                    StoreActivity.amountListTextView.get(pos).startAnimation(shake);
                    StoreActivity.total -= StoreActivity.priceList.get(pos);
                    TextView t = (TextView) findViewById(R.id.totalAmount);
                    t.setText(String.format("Total: %d", StoreActivity.total));
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

