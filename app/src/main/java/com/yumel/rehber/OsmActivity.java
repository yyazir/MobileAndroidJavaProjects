package com.yumel.rehber;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayManager;

public class OsmActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 101;
    private static final int REQUEST_LOCATION = 103;
    private MapView mapView = null;
    OverlayManager overlayManager;
    Drawable drawable;
    Marker marker;
    CustomInfoWindow customInfoWindow;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osm);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        mapView = (MapView) findViewById(R.id.mapOne);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        overlayManager = mapView.getOverlayManager();
        drawable = getResources().getDrawable(R.drawable.location);
        marker = new Marker(mapView);
        Criteria criteria = new Criteria();


        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);
        } else {
            Toast.makeText(context, "çalışıyor", Toast.LENGTH_SHORT).show();
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String bestProvider = locationManager.getBestProvider(criteria, false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
        }


        Location location = locationManager.getLastKnownLocation(bestProvider);


        GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());

        IMapController mapController = mapView.getController();

        marker = new Marker(mapView);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(drawable);
        customInfoWindow = new CustomInfoWindow(R.layout.custom_info_window_layout, mapView);
        marker.setInfoWindow(customInfoWindow);
        mapView.getOverlays().add(marker);
        mapController.setZoom(18);
        mapController.setCenter(currentLocation);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(drawable);
        mapView.getOverlays().add(marker);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.isAnimating();

        //marker.setInfoWindow(new MyCustomInfoWindows(mapView));
        //mapView.invalidate();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


}