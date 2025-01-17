package com.applozic.mobicomkit.sample.pushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.applozic.mobicomkit.api.notification.MobiComPushReceiver;

public class GcmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GcmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MobiComPushReceiver.isMobiComPushNotification(intent)) {
            Log.i(TAG, "Applozic notification processing...");
            MobiComPushReceiver.processMessageAsync(context, intent);
            return;
        }
    }

}