package com.yumel.rehber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayManager;

import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class OsmFocalMechanism extends AppCompatActivity {
    MapView mapView;
    OverlayManager overlayManager;
    Criteria criteria;
    FocalMechanismInterface focalMechanismInterface;
    private static final String TAG = "OsmFocalMechanism";
    List<Earthquake> focalMechanismsList;
    IMapController iMapController;
    GeoPoint center;
    CustomInfoAutomaticMomentTensor customInfoWindowEarthquake;
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        center = new GeoPoint(38.97779993310298, 33.03097964609505);
        setContentView(R.layout.activity_osm_focal_mechanism);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://koeri.boun.edu.tr/")
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        focalMechanismInterface = retrofit.create(FocalMechanismInterface.class);
        mapView = findViewById(R.id.map_view);
        iMapController = mapView.getController();
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        overlayManager = mapView.getOverlayManager();
        mapView.setMultiTouchControls(true);
        loadFocalMechaniscm();
        iMapController.setCenter(center);
        iMapController.setZoom(7);
        mapView.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View v) {
                closeAllPopups(marker);
                return true;
            }
        });

    }

    private void loadFocalMechaniscm() {
        Call<AutomaticMomentTensors> focalMechanismCall = focalMechanismInterface
                .getFocalMechanism();
        focalMechanismCall.enqueue(new Callback<AutomaticMomentTensors>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<AutomaticMomentTensors> call, Response<AutomaticMomentTensors> response) {
                if (response.isSuccessful()) {
                    AutomaticMomentTensors automaticMomentTensors = response.body();
                    focalMechanismsList = automaticMomentTensors.getFocalMechanismsList();
                    focalMechanismsList.size();
                    for (int i = 1; i < focalMechanismsList.size(); i++) {
                        String imageUrl ="http://koeri.boun.edu.tr/sismo/focalmechanism/"+focalMechanismsList.get(i).getBeachball();
                        addMarker(imageUrl,Double.parseDouble(focalMechanismsList.get(i).getLat()),Double.parseDouble(focalMechanismsList.get(i).getLon()),focalMechanismsList.get(i) );
                    }
                    System.out.println(focalMechanismsList.size());

                } else {
                    Log.d(TAG, "Request not successful: " + response.code());
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(Call<AutomaticMomentTensors> call, Throwable t) {
                Log.d(TAG, "onFailure: Hata: ",t);
            }

        });

    }

    private  void addMarker(String url, double lat, double lon, Earthquake earthquake ){
        Glide.with(this)
                .load(url)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        marker = new Marker(mapView);
                        marker.setPosition(new GeoPoint(lat,lon));
                        marker.setIcon(resource);
                        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                        customInfoWindowEarthquake= new CustomInfoAutomaticMomentTensor(R.layout.custom_info_window_automatic_moment_tensor,mapView);
                        marker.setInfoWindow(customInfoWindowEarthquake);
                        marker.setRelatedObject(earthquake);
                        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker, MapView mapView) {
                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(3);
                                Bundle args = new Bundle();
                                args.putString("url", earthquake.getBeachball());
                                args.putString("location", earthquake.getLat()+" "+ earthquake.getLon());
                                args.putString("magnitude", earthquake.getMw());
                                args.putString("depth", earthquake.getCentDepth());
                                args.putString("mo", earthquake.getMo());
                                args.putString("date", earthquake.getDate());
                                args.putString("vr", earthquake.getVr());
                                args.putString("strike", earthquake.getStrike1());
                                args.putString("dip", earthquake.getDip1());
                                args.putString("rake", earthquake.getRake1());
                                args.putString("strike1", earthquake.getStrike2());
                                args.putString("dip1", earthquake.getDip2());
                                args.putString("rake1", earthquake.getRake2());
                                args.putString("clvd", earthquake.getClvd());
                                args.putString("dc", earthquake.getDc());
                                args.putString("computed", earthquake.getComputed());
                                args.putString("method", earthquake.getMethod());
                                bottomSheetDialog.setArguments(args);
                                bottomSheetDialog.show(getSupportFragmentManager(),bottomSheetDialog.getTag());
                                return true;
                            }
                        });
                        mapView.getOverlays().add(marker);
                        mapView.invalidate();

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void closeAllPopups(Marker selectedMarker) {
        for (Overlay overlay : mapView.getOverlays()) {
            Marker marker = (Marker) overlay;
            if (!marker.equals(selectedMarker)) {
                marker.closeInfoWindow();
            }
        }
    }


}