package com.foora.perevozkadev.service.location;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class DriverLocationService extends Service {
    private final LocationServiceBinder binder = new LocationServiceBinder();
    private final String TAG = "DriverLocationService";
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private NotificationManager notificationManager;
    private Handler handler = new Handler();

    RemoteRepo remoteRepo;
    PrefRepo prefRepo;

    private final int LOCATION_INTERVAL = 500;
    private final int LOCATION_DISTANCE = 10;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class LocationListener implements android.location.LocationListener {
        private Location lastLocation = null;
        private final String TAG = "LocationListener";
        private Location mLastLocation;



        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            Log.d(TAG, "LocationChanged: " + location);

            // TODO: send user coordinates
            com.foora.perevozkadev.data.network.model.Location userLocation
                    = new com.foora.perevozkadev.data.network.model.Location();

            userLocation.setLatitude(location.getLatitude());
            userLocation.setLongitude(location.getLongitude());

            remoteRepo.addUserLocation(prefRepo.getUserToken(), userLocation)
                    .enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                Log.e(TAG, "onResponse: Location" + userLocation );
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        }
                    });
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + status);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        handler.post(statusCheck);

        return START_NOT_STICKY;
    }

    private Runnable statusCheck = new Runnable() {
        @Override
        public void run() {
            remoteRepo.getExecutorOrders(prefRepo.getUserToken())
                    .enqueue(new Callback<GetOrderResponse>() {
                        @Override
                        public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                            if (response.isSuccessful()) {
                                List<Order> orders = response.body().getOrders();
                                List<Order> activeOrders = new ArrayList<>();

                                for (Order order : orders) {
                                    if (!order.getStatus().equals("finished")
                                            || !order.getStatus().equals("Груз доставлен")) {
                                        activeOrders.add(order);
                                    }
                                }

                                Log.e(TAG, "onResponse: " + activeOrders);

                                if (activeOrders.size() > 0) {
                                    startTracking();
                                } else {
                                    handler.postDelayed(statusCheck, 15000);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<GetOrderResponse> call, Throwable t) {

                        }
                    });
        }
    };

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        startForeground(12345678, getNotification());

        remoteRepo = new RemoteRepoImpl();
        prefRepo = new PrefRepoImpl(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            try {
                mLocationManager.removeUpdates(mLocationListener);
            } catch (Exception ex) {
                Log.d(TAG, "fail to remove location listners, ignore", ex);
            }
        }
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public void startTracking() {
        initializeLocationManager();
        mLocationListener = new LocationListener(LocationManager.GPS_PROVIDER);

        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListener);

        } catch (java.lang.SecurityException ex) {
            // Log.d(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }

    public void stopTracking() {
        this.onDestroy();
    }

    private Notification getNotification() {
        createNotificationChannel();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), "channel_01")
                        .setAutoCancel(true);
        return builder.build();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_01";
            String description = "Location Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_01", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public class LocationServiceBinder extends Binder {
        public DriverLocationService getService() {
            return DriverLocationService.this;
        }
    }
}
