package com.yumel.rehber;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class CustomInfoAutomaticMomentTensor extends InfoWindow {
    public CustomInfoAutomaticMomentTensor(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onOpen(Object item) {
        View view = getView();
        Marker marker = (Marker) item;
        Earthquake earthquake = (Earthquake) marker.getRelatedObject();

        TextView locationText1 = (TextView) view.findViewById(R.id.locationText1);
        locationText1.setText(earthquake.getLat().toString() + "  " + earthquake.getLon().toString());
        TextView magnitudeText1 = (TextView) view.findViewById(R.id.magnitudeText1);
        magnitudeText1.setText(earthquake.getMw());
        TextView dateText1 = (TextView) view.findViewById(R.id.dateText1);
        dateText1.setText(earthquake.getDate());
        TextView strikeText = (TextView) view.findViewById(R.id.strikeText);
        strikeText.setText(earthquake.getStrike1());
        TextView dipText = (TextView) view.findViewById(R.id.dipText);
        dipText.setText(earthquake.getDip1());
        TextView rakeText = (TextView) view.findViewById(R.id.rakeText);
        rakeText.setText(earthquake.getRake1());
        TextView strikeText1 = (TextView) view.findViewById(R.id.strikeText1);
        strikeText1.setText(earthquake.getStrike2());
        TextView dipText1 = (TextView) view.findViewById(R.id.dipText1);
        dipText1.setText(earthquake.getDip2());
        TextView rakeText1 = (TextView) view.findViewById(R.id.rakeText1);
        rakeText1.setText(earthquake.getRake1());
        TextView dcText = (TextView) view.findViewById(R.id.dcText);
        dcText.setText(earthquake.getDc());
        TextView clvdText = (TextView) view.findViewById(R.id.clvdText);
        clvdText.setText(earthquake.getClvd());
        TextView computedText = (TextView) view.findViewById(R.id.computedText);
        computedText.setText(earthquake.getComputed());
        TextView methodText = (TextView) view.findViewById(R.id.methodText);
        methodText.setText(earthquake.getMethod());
        TextView moText = (TextView) view.findViewById(R.id.moText);
        moText.setText(earthquake.getMo());
        TextView deepText1 = (TextView) view.findViewById(R.id.deepText1);
        deepText1.setText(earthquake.getCentDepth());
        TextView vrText = (TextView) view.findViewById(R.id.vrText);
        vrText.setText(earthquake.getVr());
        ImageView image = (ImageView) view.findViewById(R.id.imageEarthquake);
        Glide.with(view)
                .load("http://koeri.boun.edu.tr/sismo/focalmechanism/" + earthquake.getBeachball())
                .override(100, 100)
                .into(image);

    }

    @Override
    public void onClose() {

    }
}
