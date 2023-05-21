package com.yumel.rehber;

import android.os.Bundle;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MarkerClickListener implements Marker.OnMarkerClickListener {
    private OsmPharmacyMap osmPharmacyMap;

    public MarkerClickListener(OsmPharmacyMap osmPharmacyMap) {
        this.osmPharmacyMap = osmPharmacyMap;
    }

    @Override
    public boolean onMarkerClick(Marker marker, MapView mapView) {
        marker.showInfoWindow();

        ENabizPharmacy eNabizPharmacy = (ENabizPharmacy) marker.getRelatedObject();
        double lat = Double.parseDouble(eNabizPharmacy.getLatitude());
        double lon = Double.parseDouble(eNabizPharmacy.getLongitude());

        // Create and show BottomSheetFragment
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(1);
        Bundle args = new Bundle();
        args.putString("name", eNabizPharmacy.getPharmacyName());
        args.putString("district", eNabizPharmacy.getDistrictName());
        args.putString("address", eNabizPharmacy.getAddress());
        args.putDouble("lat", lat);
        args.putDouble("lon", lon);
        bottomSheetDialog.setArguments(args);
        bottomSheetDialog.show(osmPharmacyMap.getSupportFragmentManager(), bottomSheetDialog.getTag());

        return true;
    }
}
