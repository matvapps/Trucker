package com.foora.perevozkadev;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.sos.SosActivity;
import com.foora.perevozkadev.utils.AppLogger;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alexandr
 */
public class PerevozkaApp extends MultiDexApplication {
    DataManager mDataManager;

//    @Inject
//    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        AppLogger.init();
        Fabric.with(this, new Crashlytics());

        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new NotificationReceivedHandler())
                .setNotificationOpenedHandler(new NotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title;
            String body = notification.payload.body;
            String smallIcon = notification.payload.smallIcon;
            String largeIcon = notification.payload.largeIcon;
            String bigPicture = notification.payload.bigPicture;
            String smallIconAccentColor = notification.payload.smallIconAccentColor;
            String sound = notification.payload.sound;
            String ledColor = notification.payload.ledColor;
            int lockScreenVisibility = notification.payload.lockScreenVisibility;
            String groupKey = notification.payload.groupKey;
            String groupMessage = notification.payload.groupMessage;
            String fromProjectNumber = notification.payload.fromProjectNumber;
            String rawPayload = notification.payload.rawPayload;

            String customKey;

            Log.i("OneSignal", "NotificationID received: " + notificationID);
            Log.d("OneSignal", "notificationReceived: " + data.toString());
        }
    }


    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;

            Intent intent = new Intent(getApplicationContext(), SosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

            try {
                Log.d("OneSignal", "notificationOpened: " + "longitude" + data.getDouble("longitude") +
                        "\n" + "latitude" + data.getDouble("latitude") +
                        "\n" + "user_id" + data.getInt("user_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                intent.putExtra("sos_request_id", data.getInt("sos_request_id"));
                intent.putExtra("longitude", data.getDouble("longitude"));
                intent.putExtra("latitude", data.getDouble("latitude"));
                intent.putExtra("user_id", data.getInt("user_id"));
                if (result.notification.payload.collapseId != null)
                    intent.putExtra("notification_id", result.notification.payload.collapseId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(intent);


        }
    }

}
