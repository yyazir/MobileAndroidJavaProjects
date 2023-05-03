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
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.clans.fab.FloatingActionButton;

import org.jetbrains.annotations.Contract;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OsmPharmacyMap extends AppCompatActivity {
    private static final int PERMISSION_CODE = 101;
    private static final int REQUEST_LOCATION = 103;
    OverlayManager overlayManager;
    Drawable drawable;
    Drawable pharmacyDrawable;
    Marker marker;
    Marker pharmacyMarker;
    MapView mapView;
    GeoPoint currentLocation;
    ArrayList<ENabizPharmacy> postPharmaListMap = new ArrayList<>();
    CustomInfoWindow customInfoWindow;
    ENabizPharmacy eNabizPharmacy;
    List<GeoPoint> listGeoPoints = new ArrayList<>();
    FloatingActionButton baseMap;
    SeekBar zoomSlider;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osm_pharmacy_map);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        mapView = findViewById(R.id.mapOne);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        overlayManager = mapView.getOverlayManager();
        drawable = getResources().getDrawable(R.drawable.location);
        pharmacyDrawable = getResources().getDrawable(R.drawable.icon_pharmacy);
        zoomSlider = findViewById(R.id.zoomSlider);
        mapView.setBuiltInZoomControls(false);
        marker = new Marker(mapView);
        Criteria criteria = new Criteria();

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String bestProvider = locationManager.getBestProvider(criteria, false);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        } else {
            currentLocation = new GeoPoint(39.984652888844586, 32.62725496566937);
        }

        IMapController mapController = mapView.getController();
        mapController.setZoom(18);
        mapController.setCenter(currentLocation);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(drawable);
        marker.setTitle("Konumunuz.");
        mapView.getOverlays().add(marker);
        postPharmaListMap = getPharmacies();
        for (int i = 0; i < postPharmaListMap.size(); i++) {
            eNabizPharmacy = postPharmaListMap.get(i);
            eNabizPharmacy.setLatitude(eNabizPharmacy.getLatitude());
            eNabizPharmacy.setLongitude(eNabizPharmacy.getLongitude());
            pharmacyMarker = new Marker(mapView);
            pharmacyMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            pharmacyMarker.setIcon(drawable);
            customInfoWindow = new CustomInfoWindow(R.layout.custom_info_window_layout, mapView);
            pharmacyMarker.setInfoWindow(customInfoWindow);
            pharmacyMarker.setRelatedObject(eNabizPharmacy);
            double lat = Double.parseDouble(postPharmaListMap.get(i).getLatitude());
            double lon = Double.parseDouble(postPharmaListMap.get(i).getLongitude());
            GeoPoint pharmacyGeoPoint = new GeoPoint(lat, lon);
            listGeoPoints.add(pharmacyGeoPoint);
            pharmacyMarker.setPosition(pharmacyGeoPoint);
            pharmacyMarker.setIcon(pharmacyDrawable);
            mapView.getOverlays().add(pharmacyMarker);
            pharmacyMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                        @Override
                                                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                            closeAllPopups(pharmacyMarker);
                                                            marker.showInfoWindow();
                                                            return true;
                                                        }
                                                    }
            );
        }
        mapView.setMultiTouchControls(true);
        mapView.isAnimating();




        zoomSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mapView.getController().setZoom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

    public void zoomPoints(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) return;

        double minLat = 85.05112877980658;
        double minLon = 180.0;
        double maxLat = -85.05112877980658;
        double maxLon = -180.0;

        for (GeoPoint point : points) {
            minLat = Math.min(point.getLatitude(), minLat);
            minLon = Math.min(point.getLongitude(), minLon);
            maxLat = Math.max(point.getLatitude(), maxLat);
            maxLon = Math.max(point.getLongitude(), maxLon);
        }

        BoundingBox boundingBox = new BoundingBox(maxLat, maxLon, minLat, minLon);
        mapView.zoomToBoundingBox(boundingBox, true, 50);
    }


    private void closeAllPopups(Marker selectedMarker) {
        for (Overlay overlay : mapView.getOverlays()) {
            Marker marker = (Marker) overlay;
            if (!marker.equals(selectedMarker)) {
                marker.closeInfoWindow();
            }
        }
    }

    @NonNull
    @Contract(" -> new")
    private OnlineTileSourceBase createCustomTileSource() {
        return new OnlineTileSourceBase("Custom Tiles", 1, 19, 256, "",
                new String[]{"https://demo.gempa.de/gaps/tiles/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl() + MapTileIndex.getZoom(pMapTileIndex) + "/"
                        + MapTileIndex.getX(pMapTileIndex) + "/" + MapTileIndex.getY(pMapTileIndex)
                        + ".png";
            }
        };
    }

}