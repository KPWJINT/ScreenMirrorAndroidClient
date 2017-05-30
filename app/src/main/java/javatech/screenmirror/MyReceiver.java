package javatech.screenmirror;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sobczak on 30.05.2017.
 */

public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("DEBUG", "receiver got messs");
            String message=intent.getStringExtra("DATA_PASS");
//            mess.setText(message);
        }
    }
