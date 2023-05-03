package com.yumel.rehber;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.Locale;

public class CustomInfoWindowEarthquake extends InfoWindow {
    public CustomInfoWindowEarthquake(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }


    @Override
    public void onOpen(Object item) {
        View view = getView();
        Marker marker = (Marker) item;
        EarthquakeAFAD earthquake = (EarthquakeAFAD) marker.getRelatedObject();
        TextView depthText = (TextView) view.findViewById(R.id.depthText);
        depthText.setText(String.valueOf(earthquake.getDepth()));
        TextView magnitudeText = (TextView) view.findViewById(R.id.magnitudeText);
        magnitudeText.setText(String.valueOf(earthquake.getMagnitude()));
        TextView dateText = (TextView) view.findViewById(R.id.dateText);
        dateText.setText(String.valueOf(earthquake.getEventDate()));
        TextView locationText = (TextView) view.findViewById(R.id.locationText);
        locationText.setText(earthquake.getLocation());
        ImageView shareButton = (ImageView) view.findViewById(R.id.popupShareBtnEQ);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoPoint position = marker.getPosition();
                double lat = position.getLatitude();
                double lon = position.getLongitude();
                String uri = String.format(Locale.ENGLISH,"http://maps.google.com/maps?daddr=%f,%f",lat,lon);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Deprem";
                String sub = uri;
                intent.putExtra(Intent.EXTRA_TEXT, body);
                intent.putExtra(Intent.EXTRA_TEXT, sub);
                view.getContext().startActivity(Intent.createChooser(intent,"Afet Bilgilendir"));
            }
        });

    }

    @Override
    public void onClose() {

    }
}
