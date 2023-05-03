package com.yumel.rehber;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationHelper {


    public static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            // GPS konumunu alma
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            // GPS konumu yoksa ve Wi-Fi sinyalleri kullanılabilirse, Wi-Fi konumunu alma
            if (location == null) {
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isNetworkEnabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return location;
    }
}
