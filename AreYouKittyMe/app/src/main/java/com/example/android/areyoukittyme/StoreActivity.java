package com.example.android.areyoukittyme;


        import android.app.Activity;
        import android.content.ClipData;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.support.design.widget.BaseTransientBottomBar;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.DividerItemDecoration;
        import android.util.Log;
        import android.view.DragEvent;
        import android.view.Gravity;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.View.DragShadowBuilder;
        import android.view.View.OnDragListener;
        import android.view.View.OnTouchListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.view.View.OnClickListener;
        import android.widget.TextView;

        import com.example.android.areyoukittyme.Item.Fish;
        import com.example.android.areyoukittyme.Item.Item;
        import com.example.android.areyoukittyme.Store.Store;
        import com.example.android.areyoukittyme.User.User;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class StoreActivity extends AppCompatActivity {

//    private static User user;
//    private static Store theStore;
    private static int ItemDogAmount = 0;
    private static int itemFishAmount = 0;
    public static ArrayList<Integer> priceList;
    public static ArrayList<TextView> amountList;
    public static int total = 0;
    private Context context;
    public static Snackbar mySnackbar;
    public static TextView catCash;
    public static TextView totalText;
    MediaPlayer mPlayer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_store);
        priceList = new ArrayList<>();
        amountList = new ArrayList<>();
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
        catCash.setText(String.format("CatCash: %d", User.getCash()));
        totalText = (TextView) findViewById(R.id.totalAmount);
    }

    //TODO: fix bug: must set total to zero when back button is clicked


    private void checkout() {
        MediaPlayer mPlayer = MediaPlayer.create(StoreActivity.this, R.raw.cash_register);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();
        if (total > User.getCash()) {
            mySnackbar.show();
        }
        else {
            User.setCash(User.getCash() - total);
            catCash.setText(String.format("CatCash: %d", User.getCash()));
            total = 0;
            totalText.setText(String.format("Total: %d", total));
            for (int i = 0; i < StoreActivity.amountList.size(); i++) {
                StoreActivity.amountList.get(i).setText(String.valueOf(0));
            }
            User.userCheckout(StoreActivity.amountList, StoreActivity.priceList);
        }
    }

    private void populateStore() {
        // TODO: change the layout to gridview
        LinearLayout storeContainer = (LinearLayout) findViewById(R.id.storeContainer);
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
            itemIcon.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
            subContainer.addView(itemIcon);
            // setting price tag
            TextView priceTag = new TextView(this);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(400, 170);
            priceTag.setLayoutParams(parms);
            priceTag.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            priceTag.setText(String.valueOf(item.getPrice()));
            this.priceList.add(item.getPrice());
            subContainer.addView(priceTag);
            // amount container
            LinearLayout amountContainer = new LinearLayout(this);
            amountContainer.setGravity(Gravity.CENTER);
            amountContainer.setOrientation(LinearLayout.HORIZONTAL);
            Button minusBtn = new Button(this);
            minusBtn.setText("-");
            Button plusBtn = new Button(this);
            plusBtn.setText("+");
            TextView amount = new TextView(this);
            amount.setText("0");
            amountContainer.addView(minusBtn);
            amountContainer.addView(amount);
            this.amountList.add(amount);
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
            }
        }
    }

    private String incrementString(String s, int a) {
        int intStr = Integer.parseInt(s);
        intStr += a;
        return String.valueOf(intStr);
    }

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
                String incremented = incrementString(StoreActivity.amountList.get(pos).getText().toString(), 1);
                StoreActivity.amountList.get(pos).setText(incremented);
                StoreActivity.total += StoreActivity.priceList.get(pos);
                TextView t = (TextView) findViewById(R.id.totalAmount);
                t.setText(String.format("Total: %d", StoreActivity.total));

            }
            else if (sign == '-') {
                if (Integer.parseInt(StoreActivity.amountList.get(pos).getText().toString()) != 0) {
                    String incremented = incrementString(StoreActivity.amountList.get(pos).getText().toString(), -1);
                    StoreActivity.amountList.get(pos).setText(incremented);
                    StoreActivity.total -= StoreActivity.priceList.get(pos);
                    TextView t = (TextView) findViewById(R.id.totalAmount);
                    t.setText(String.format("Total: %d", StoreActivity.total));
                }
            }
        }
    }
}

//package com.example.android.areyoukittyme;
//
//
//        import android.app.Activity;
//        import android.content.ClipData;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.graphics.drawable.Drawable;
//        import android.os.Bundle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.util.Log;
//        import android.view.DragEvent;
//        import android.view.MotionEvent;
//        import android.view.View;
//        import android.view.View.DragShadowBuilder;
//        import android.view.View.OnDragListener;
//        import android.view.View.OnTouchListener;
//        import android.view.ViewGroup;
//        import android.widget.BaseAdapter;
//        import android.widget.Button;
//        import android.widget.GridView;
//        import android.widget.ImageView;
//        import android.widget.LinearLayout;
//        import android.view.View.OnClickListener;
//        import android.widget.TextView;
//
//        import com.example.android.areyoukittyme.Item.Fish;
//        import com.example.android.areyoukittyme.Item.Item;
//        import com.example.android.areyoukittyme.Store.Store;
//        import com.example.android.areyoukittyme.User.User;
//
//        import org.w3c.dom.Text;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class StoreActivity extends AppCompatActivity {
//
//    private User user;
//    private Store theStore;
//
//    Button backToMainBtn;
//    Button incrementCashBtn;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setContentView(R.layout.activity_store);
//        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
//        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
//
//        incrementCashBtn = (Button) findViewById(R.id.AddCashTempBtn);
//        incrementCashBtn.setOnClickListener(new MyClickListener());
//
//
//        this.user = new User(100, new ArrayList<Item>());
//        this.theStore = new Store();
//
//        addItemToStore();
//
//    }
//
//    private void addItemToStore() {
//        List<Item> itemL = new ArrayList<Item>();
//        for (Item item: this.theStore.getItemList()) {
//            // the linear layout for store inventory
//            itemL.add(item);
//        }
//        GridView storeInventory = (GridView) findViewById(R.id.topleft);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(170, 170);
//        storeInventory.setAdapter(new ImageAdapter(this));
////        storeInventory.addView(view);
//    }
//
//    private final class MyClickListener implements OnClickListener {
//        @Override
//        public void onClick(View v) {
//            if(v.getId() == R.id.AddCashTempBtn) {
//                User.cash += 5;
//                TextView lbl = (TextView) findViewById(R.id.currentCashAmount);
//                lbl.setText(String.valueOf(User.cash));
//            }
//        }
//    }
//
//    private final class MyTouchListener implements OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
////                ClipData data = ClipData.newPlainText("", "");
////                Item item = view.getId();
//                ClipData data = ClipData.newPlainText("Item price", "80");
//                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                        view);
//                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    class MyDragListener implements OnDragListener {
//        Drawable enterShape = getResources().getDrawable(
//                R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.shape);
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int action = event.getAction();
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    // do nothing
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
//                    break;
//                case DragEvent.ACTION_DROP:
//                    // Dropped, reassign View to ViewGroup
////                    ClipData.Item item = event.getClipData().getItemAt(0);
////                    String str = item.getText().toString();
////                    TextView lbl = (TextView) findViewById(R.id.currentCashAmount);
////                    int curVal = Integer.parseInt(lbl.getText().toString());
////                    curVal -= Integer.parseInt(str);
////                    User.cash = curVal;
////                    str = String.valueOf(curVal);
////                    lbl.setText(str);
//
//                    View view = (View) event.getLocalState();
//                    GridView owner = (GridView) view.getParent();
//                    owner.removeView(view);
//                    GridView container = (GridView) v;
////                    container.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundDrawable(normalShape);
//                default:
//                    break;
//            }
//            return true;
//        }
//    }
//
//    public class ImageAdapter extends BaseAdapter {
//        private Context mContext;
//
//        public ImageAdapter(Context c) {
//            mContext = c;
//        }
//
//        public int getCount() {
//            return mThumbIds.length;
//        }
//
//        public Object getItem(int position) {
//            return null;
//        }
//
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        // create a new ImageView for each item referenced by the Adapter
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView;
//            if (convertView == null) {
//                // if it's not recycled, initialize some attributes
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams(170, 170));
//                imageView.setOnTouchListener(new MyTouchListener());
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setPadding(8, 8, 8, 8);
//            } else {
//                imageView = (ImageView) convertView;
//            }
//
//            imageView.setImageResource(mThumbIds[position]);
//            return imageView;
//        }
//
//        // references to our images
//        private Integer[] mThumbIds = {
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7,
//                R.drawable.sample_0, R.drawable.sample_1,
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7,
//                R.drawable.sample_0, R.drawable.sample_1,
//                R.drawable.sample_2, R.drawable.sample_3,
//                R.drawable.sample_4, R.drawable.sample_5,
//                R.drawable.sample_6, R.drawable.sample_7
//        };
//    }
//


