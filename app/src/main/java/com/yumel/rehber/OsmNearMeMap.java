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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class OsmNearMeMap extends AppCompatActivity {

    private static final int PERMISSION_CODE = 101;
    private static final int REQUEST_LOCATION = 103;
    OverlayManager overlayManager;
    Drawable drawable;
    Drawable pharmacyDrawble;
    Marker marker;
    Marker pharmacyMarker;
    private MapView mapView;
    GeoPoint currentLocation;
    Location myLocation;
    LatLng pharmacyLatlang;
    ArrayList<LatLng> pharmacyLatLngList;
    ArrayList<ENabizPharmacy> postPharmaListMap = new ArrayList<ENabizPharmacy>();
    CustomInfoWindow customInfoWindow;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osm_near_me_map);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        Objects.requireNonNull(getSupportActionBar()).hide();
        mapView = (MapView) findViewById(R.id.mapOne);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        overlayManager = mapView.getOverlayManager();
        drawable = getResources().getDrawable(R.drawable.location);
        pharmacyDrawble = getResources().getDrawable(R.drawable.icon_pharmacy);
        pharmacyLatLngList = new ArrayList<LatLng>();
        marker = new Marker(mapView);
        Criteria criteria = new Criteria();

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);
        } else {
            Toast.makeText(context, "çalışıyor", Toast.LENGTH_SHORT).show();
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String bestProvider = locationManager.getBestProvider(criteria, false);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        myLocation = locationManager.getLastKnownLocation(bestProvider);

        if (myLocation != null) {
            currentLocation = new GeoPoint(myLocation.getLatitude(), myLocation.getLongitude());
        } else {
            currentLocation = new GeoPoint(39.90986543595399, 32.856267644871224);
        }

        IMapController mapController = mapView.getController();
        mapController.setZoom(18);
        mapController.setCenter(currentLocation);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(drawable);
        mapView.getOverlays().add(marker);
        postPharmaListMap = getPharmacies();
        pharmacyMarker = new Marker(mapView);
        ENabizPharmacy closestLocation = findClosestLocation(pharmacyLatLngList);
        pharmacyMarker.setPosition(new GeoPoint(Double.parseDouble(closestLocation.getLatitude()), Double.parseDouble(closestLocation.getLongitude())));
        pharmacyMarker.setIcon(pharmacyDrawble);
        customInfoWindow = new CustomInfoWindow(R.layout.custom_info_window_layout, mapView);
        pharmacyMarker.setInfoWindow(customInfoWindow);
        pharmacyMarker.setRelatedObject(closestLocation);
        mapView.getOverlays().add(pharmacyMarker);
        mapView.setMultiTouchControls(true);
        mapView.isAnimating();
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

    private ArrayList<ENabizPharmacy> getPharmacies() {
        Bundle bundle = getIntent().getExtras();
        Serializable serializable = bundle.getSerializable("xx");
        return (ArrayList<ENabizPharmacy>) serializable;
    }

    public ENabizPharmacy findClosestLocation(@NonNull ArrayList<LatLng> locations) {
        ENabizPharmacy closestPharmacy = null;
        double minDistance = Double.MAX_VALUE;

        for (ENabizPharmacy pharmacy : postPharmaListMap) {
            double latitude = Double.parseDouble(pharmacy.getLatitude());
            double longitude = Double.parseDouble(pharmacy.getLongitude());
            GeoPoint pharmacyLocation = new GeoPoint(latitude, longitude);
            double distance = currentLocation.distanceToAsDouble(pharmacyLocation);
            if (distance < minDistance) {
                minDistance = distance;
                closestPharmacy = pharmacy;
            }
        }
        return closestPharmacy;
    }

}
