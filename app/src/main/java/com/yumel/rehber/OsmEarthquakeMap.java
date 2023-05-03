package com.yumel.rehber;

import static androidx.fragment.app.FragmentManager.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.clans.fab.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OsmEarthquakeMap extends AppCompatActivity {
    private static final int PERMISSION_CODE = 101;
    private static final int REQUEST_LOCATION = 102;
    OverlayManager overlayManager;

    GeoPoint currentLocation;
    EventInterface eventInterface;
    ArrayList<EarthquakeAFAD> earthquakeArrayList;
    MapView mapView;
    Marker marker;
    CustomInfoWindowEarthquake customInfoWindowEQ;
    FloatingActionButton beachButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osm_earthquake_map);
        getSupportActionBar().hide();
        beachButton = findViewById(R.id.focalMechanismButton);
        String baseUrlEvent = "https://deprem.afad.gov.tr/";
        earthquakeArrayList = new ArrayList<EarthquakeAFAD>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlEvent)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventInterface = retrofit.create(EventInterface.class);
        mapView = (MapView) findViewById(R.id.mapEarthquakeMap);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        overlayManager =mapView.getOverlayManager();
        Drawable drawable = getResources().getDrawable(R.drawable.location);
        Marker marker = new Marker(mapView);
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
        mapController.setZoom(7);
        mapController.setCenter(currentLocation);
        marker.setPosition(currentLocation);
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        marker.setIcon(drawable);
        marker.setTitle("Konumunuz");
        mapView.getOverlays().add(marker);
        loadEvents();

        beachButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, OsmFocalMechanism.class);
            startActivity(intent);
        });


    }

    private void loadMarkers() {
        Drawable earthquakeDrawable1 = getResources().getDrawable(R.drawable.eq3_0);
        Drawable earthquakeDrawable2 = getResources().getDrawable(R.drawable.eq3_0_3_9);
        Drawable earthquakeDrawable3 = getResources().getDrawable(R.drawable.eq4_0_4_9);
        Drawable earthquakeDrawable4 = getResources().getDrawable(R.drawable.eq5_0_5_9);
        Drawable earthquakeDrawable5 = getResources().getDrawable(R.drawable.eq6_0_6_09);
        Drawable earthquakeDrawable6 = getResources().getDrawable(R.drawable.eq7_0);


        if (earthquakeArrayList != null) {
            for (int i = 0; i < earthquakeArrayList.size(); i++) {
                double magnitude = (double) earthquakeArrayList.get(i).getMagnitude();

                if (magnitude <= 3.0){
                    Marker earthquakeMarker1 = new Marker(mapView);
                    earthquakeMarker1.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker1.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker1.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker1.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker1.setIcon(earthquakeDrawable1);
                    mapView.getOverlays().add(earthquakeMarker1);
                    earthquakeMarker1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                   @Override
                                                                   public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                       closeAllPopups(earthquakeMarker1);
                                                                       marker.showInfoWindow();
                                                                       return true;
                                                                   }
                                                               }
                    );
                } else if (magnitude >= 3.1 && magnitude <= 3.9) {
                    Marker earthquakeMarker2 = new Marker(mapView);
                    earthquakeMarker2.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker2.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker2.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker2.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker2.setIcon(earthquakeDrawable2);
                    mapView.getOverlays().add(earthquakeMarker2);
                    earthquakeMarker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                   @Override
                                                                   public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                       closeAllPopups(earthquakeMarker2);
                                                                       marker.showInfoWindow();
                                                                       return true;
                                                                   }
                                                               }
                    );
                } else if (magnitude >= 4.1 && magnitude <= 4.9 ) {
                    Marker earthquakeMarker3 = new Marker(mapView);
                    earthquakeMarker3.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker3.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker3.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker3.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker3.setIcon(earthquakeDrawable3);
                    mapView.getOverlays().add(earthquakeMarker3);
                    earthquakeMarker3.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                   @Override
                                                                   public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                       closeAllPopups(earthquakeMarker3);
                                                                       marker.showInfoWindow();
                                                                       return true;
                                                                   }
                                                               }
                    );
                } else if (magnitude >= 5.1 && magnitude <= 5.9 ) {
                    Marker earthquakeMarker4 = new Marker(mapView);
                    earthquakeMarker4.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker4.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker4.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker4.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker4.setIcon(earthquakeDrawable4);
                    mapView.getOverlays().add(earthquakeMarker4);
                    earthquakeMarker4.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                   @Override
                                                                   public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                       closeAllPopups(earthquakeMarker4);
                                                                       marker.showInfoWindow();
                                                                       return true;
                                                                   }
                                                               }
                    );
                }  else if (magnitude >= 6.1 && magnitude <= 6.9 ) {
                    Marker earthquakeMarker5 = new Marker(mapView);
                    earthquakeMarker5.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker5.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker5.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker5.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker5.setIcon(earthquakeDrawable5);
                    mapView.getOverlays().add(earthquakeMarker5);
                    earthquakeMarker5.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                   @Override
                                                                   public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                       closeAllPopups(earthquakeMarker5);
                                                                       marker.showInfoWindow();
                                                                       return true;
                                                                   }
                                                               }
                    );
                }   else  {
                    Marker earthquakeMarker6 = new Marker(mapView);
                    earthquakeMarker6.setPosition(earthquakeArrayList.get(i).getGeoPoint());
                    earthquakeMarker6.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                    customInfoWindowEQ = new CustomInfoWindowEarthquake(R.layout.custom_info_window_earthquake, mapView);
                    earthquakeMarker6.setInfoWindow(customInfoWindowEQ);
                    earthquakeMarker6.setRelatedObject(earthquakeArrayList.get(i));
                    earthquakeMarker6.setIcon(earthquakeDrawable6);
                    mapView.getOverlays().add(earthquakeMarker6);
                    earthquakeMarker6.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                                                @Override
                                                                public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                                    closeAllPopups(earthquakeMarker6);
                                                                    marker.showInfoWindow();
                                                                    return true;
                                                                }
                                                            }
                    );
                }
            }
        }
    }

    private void closeAllPopups(Marker selectedMarker) {
        for (Overlay overlay : mapView.getOverlays()) {
            Marker marker = (Marker) overlay;
            if (!marker.equals(selectedMarker)) {
                marker.closeInfoWindow();
            }
        }
    }

    private void loadEvents() {
        Call<List<EarthquakeAFAD>> eventCall = eventInterface
                .getEvents();
        eventCall.enqueue(new Callback<List<EarthquakeAFAD>>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<List<EarthquakeAFAD>> call, @NonNull Response<List<EarthquakeAFAD>> response) {
                if (response.isSuccessful()) {
                    List<EarthquakeAFAD> earthquakesAFAD = response.body();
                    if (earthquakeArrayList != null) {
                        earthquakeArrayList.clear();
                    }
                    if (response.body() != null) {
                        earthquakeArrayList.addAll(earthquakesAFAD);
                        Log.d(TAG, earthquakeArrayList.toString());
                        loadMarkers();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EarthquakeAFAD>> call, @NonNull Throwable t) {

            }
        });

    }




}