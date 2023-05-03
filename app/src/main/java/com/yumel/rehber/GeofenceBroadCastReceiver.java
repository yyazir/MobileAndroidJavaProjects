package com.yumel.rehber;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeofenceBroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        NotificationHelper notificationHelper = new NotificationHelper(context);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        double lat = geofencingEvent.getTriggeringLocation().getLatitude();
        double lon = geofencingEvent.getTriggeringLocation().getLongitude();
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "TAG");
            return;
        }
        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence : geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
        int transitionType = geofencingEvent.getGeofenceTransition();
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                notificationHelper.sendHighPriorityNotification(getAddress(lat, lon, context) + "'ne Hoşgeldiniz.", "", SecondActivity.class);
                Log.d(TAG, "onReceive: Alan İçerisi");
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                notificationHelper.sendHighPriorityNotification("Alanda beklenmektedir.", "", SecondActivity.class);
                Log.d(TAG, "onReceive: Alan Bekleme");
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                notificationHelper.sendHighPriorityNotification(getAddress(lat, lon, context) + "'den çıktınız.", "", SecondActivity.class);
                Log.d(TAG, "onReceive: Alan Dışı");
                break;
        }
    }

    private String getAddress(double latitude, double longitude, Context context) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getSubAdminArea());
                result.append(" / ");
                result.append(address.getSubLocality());
                result.append(" Mahallesi");
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }


}