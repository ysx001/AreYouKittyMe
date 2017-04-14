package com.example.android.areyoukittyme;


        import android.app.Activity;
        import android.content.ClipData;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.DragEvent;
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

public class StoreActivity extends AppCompatActivity {

    private User user;
    private Store theStore;

    Button backToMainBtn;
    Button incrementCashBtn;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_store);
        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());

        incrementCashBtn = (Button) findViewById(R.id.AddCashTempBtn);
        incrementCashBtn.setOnClickListener(new MyClickListener());


        //TODO: find a way to store the model
        this.user = new User(100, new ArrayList<Item>());
        this.theStore = new Store();

        addItemToStore();

    }

    private void addItemToStore() {
        for (Item item: this.theStore.getItemList()) {
            // the linear layout for store inventory
            GridView storeInventory = (GridView) findViewById(R.id.topleft);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(170, 170);
            storeInventory.setAdapter(new ImageAdapter(this));
        }


//        storeInventory.addView(view);
    }

    private final class MyClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.AddCashTempBtn) {
                User.cash += 5;
                TextView lbl = (TextView) findViewById(R.id.currentCashAmount);
                lbl.setText(String.valueOf(User.cash));
            }
        }
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                Item item = view.getId();
                ClipData data = ClipData.newPlainText("Item price", "80");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        //TODO: resolve the drag to same area crash issue.
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
//                    ClipData.Item item = event.getClipData().getItemAt(0);
//                    String str = item.getText().toString();
//                    TextView lbl = (TextView) findViewById(R.id.currentCashAmount);
//                    int curVal = Integer.parseInt(lbl.getText().toString());
//                    curVal -= Integer.parseInt(str);
//                    User.cash = curVal;
//                    str = String.valueOf(curVal);
//                    lbl.setText(str);

                    View view = (View) event.getLocalState();
                    GridView owner = (GridView) view.getParent();
                    owner.removeView(view);
                    GridView container = (GridView) v;
//                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(170, 170));
                imageView.setOnTouchListener(new MyTouchListener());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
    }


    }
