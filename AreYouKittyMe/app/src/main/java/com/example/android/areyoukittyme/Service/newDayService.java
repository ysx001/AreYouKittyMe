package com.example.android.areyoukittyme.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.android.areyoukittyme.User.User;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class newDayService extends IntentService {
    /*
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.android.areyoukittyme.Service.action.FOO";
    private static final String ACTION_BAZ = "com.example.android.areyoukittyme.Service.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.example.android.areyoukittyme.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.areyoukittyme.Service.extra.PARAM2";
    */

    User mUser;

    public newDayService() {
        super("newDayService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mUser = intent.getExtras().getParcelable("User");
        mUser.newDay();
    }
}
