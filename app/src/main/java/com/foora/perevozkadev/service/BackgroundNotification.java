package com.foora.perevozkadev.service;

import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.Locale;

/**
 * Created by Alexandr.
 */
public class BackgroundNotification extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {

        JSONObject data = notification.payload.additionalData;

        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = builder -> {
            try {

                Log.d("OneSignal", "onNotificationProcessing: " + data.toString());

                return builder
                        .setContentTitle("SOS")
                        .setContentText(String.format(Locale.getDefault(), "Координаты: %f,%f;\nID Водителя %d", data.getDouble("latitude"),
                                data.getDouble("longitude"), data.getInt("user_id")))
                        .setAutoCancel(false)
                        .setColor(new BigInteger("FF00FF00", 16).intValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        };


        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);

        Log.d("OneSignal", "onNotificationProcessing: " + notification.payload.toJSONObject().toString());
        Log.d("OneSignal", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return false;
    }

}
