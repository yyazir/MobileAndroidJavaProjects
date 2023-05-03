package com.yumel.rehber;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yumel.rehber.databinding.ActivityMapsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NearMeMapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    ActivityMapsBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;
    Adapter adapter;
    Location myLocation;
    LocationHelper locationHelper;
    LatLng currentLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);// TODO: Consider calling
        ArrayList<ENabizPharmacy> postPharmaList = getPharmacies();
        adapter = new Adapter(getApplicationContext(), postPharmaList);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        myLocation = locationHelper.getLocation(this);
        currentLatLng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
/*        double lat =  39.98059792297621;
        double lon = 32.62477539764798;
        currentLatLng =  new LatLng(lat,lon);*/
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);// TODO: Consider calling
    }

    private ArrayList<ENabizPharmacy> getPharmacies() {
        Bundle bundle = getIntent().getExtras();
        Serializable serializable = bundle.getSerializable("xx");
        return (ArrayList<ENabizPharmacy>) serializable;
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<LatLng> parhamcyArray = new ArrayList<LatLng>();
        // Eczaneler listesindeki lokaston verisini LatLng'a convert edip for ile dönerek haritada marker oluşturuyor.
        for (int i = 0; i < getPharmacies().size(); i++) {
            //String[] location = getPharmacies().get(i).getEnlem().split(",");
            //double lat = Double.parseDouble(location[0]);
            //double lon = Double.parseDouble(location[1]);
            //LatLng pharmacylatLng = new LatLng(lat, lon);
            //parhamcyArray.add(pharmacylatLng);
        }

        LatLng closestPharmacy = findClosestLocation(parhamcyArray,currentLatLng);
        String closestPharmacyString = closestPharmacy.toString();
        //Stream closestPharmacyObj= getPharmacies().stream().filter(s -> s.getEnlem().matches(closestPharmacy.toString()));
        Toast.makeText(this, closestPharmacyString, Toast.LENGTH_SHORT).show();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(closestPharmacy)
                .zoom(15)
                .bearing(90)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(closestPharmacy, 13));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions().position(closestPharmacy)
//                .title(getPharmacies().get(i).name + " ECZANESİ")
                .icon(bitmapDescriptorFromVector(this, R.drawable.icon_pharmacy)));
        mMap.addMarker(new MarkerOptions().position(currentLatLng)
//                .title(getPharmacies().get(i).name + " ECZANESİ")
                .icon(bitmapDescriptorFromVector(this, R.drawable.current)));



    }

    public LatLng findClosestLocation(@NonNull ArrayList<LatLng> locations, LatLng currentLocation) {
        HashMap<LatLng,Float> distanceMap = new HashMap<>();

        for (LatLng location : locations) {
            Location target = new Location("target");
            target.setLatitude(location.latitude);
            target.setLongitude(location.longitude);
            float distance = myLocation.distanceTo(target);
            distanceMap.put(location,distance);
        }

        LatLng closestLocation = null;
        float minDistance = Float.MAX_VALUE;
        for(Map.Entry<LatLng,Float> entry : distanceMap.entrySet()) {
            if (entry.getValue() < minDistance) {
                minDistance = entry.getValue();
                closestLocation = entry.getKey();

            }
        }
        return closestLocation;

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
