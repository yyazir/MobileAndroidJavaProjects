package com.yumel.rehber;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.Locale;

public class CustomInfoWindow extends InfoWindow {
    Marker marker;
    ENabizPharmacy eNabizPharmacy;



    public CustomInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object item) {
        View view = getView();
        marker = (Marker) item;
        eNabizPharmacy = (ENabizPharmacy) marker.getRelatedObject();
        ImageButton routeButton = view.findViewById(R.id.popupRouteBtn);
        ImageButton shareButton = view.findViewById(R.id.popupShareBtn);
        TextView pharmacyName = view.findViewById(R.id.popupName);
        TextView pharmacyAddress = view.findViewById(R.id.popupAdress);
        pharmacyName.setText(eNabizPharmacy.getPharmacyName());
        pharmacyAddress.setText(eNabizPharmacy.getAddress());
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null) {
                    GeoPoint position = marker.getPosition();
                    double lat = position.getLatitude();
                    double lon = position.getLongitude();
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", lat, lon);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    view.getContext().startActivity(intent);
                }
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GeoPoint position = marker.getPosition();
                double lat = position.getLatitude();
                double lon = position.getLongitude();
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", lat, lon, eNabizPharmacy.getPharmacyName()+ " ECZANESİ");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Nöbetçi Eczane";
                String sub = uri;
                intent.putExtra(Intent.EXTRA_TEXT, body);
                intent.putExtra(Intent.EXTRA_TEXT, sub);
                view.getContext().startActivity(Intent.createChooser(intent, "Nöbetçi Eczane Paylaş"));
            }
        });

    }

    @Override
    public void onClose() {
    }



}
