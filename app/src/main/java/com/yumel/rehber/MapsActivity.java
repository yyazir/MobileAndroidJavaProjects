package com.yumel.rehber;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.yumel.rehber.databinding.ActivityMapsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    ActivityMapsBinding binding;
    private static final int FINE_LOCATION_ACCESS_REQUEST_CODE = 102;
    protected LocationListener locationListener;
    private static final int REQUEST_CODE = 110;
    FusedLocationProviderClient fusedLocationProviderClient;
    Adapter adapter;
    ArrayList<ENabizPharmacy> postPharmaListMap = new ArrayList<ENabizPharmacy>();
    Location myLocation;
    LatLng MY_LATLNG;
    LocationManager locationManager;


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
        getCurrentLocation();
        MY_LATLNG = new LatLng(getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude());
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);// TODO: Consider calling
    }

    //Eczaneler listesini SecondActivity den taşıyor.
    private ArrayList<ENabizPharmacy> getPharmacies() {
        Bundle bundle = getIntent().getExtras();
        Serializable serializable = bundle.getSerializable("xx");
        return (ArrayList<ENabizPharmacy>) serializable;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Geçersiz Yetki", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Lokasyon permissions ı kontrol ediyor. Yetki yoksa yetki isiyor.
    public void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();

    }

    //Şu an ki konumu alıyor
    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                myLocation = location;
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(MapsActivity.this);

            }
        });
    }

    //Bilinen son konumu alıyor.
    public Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            }// TODO: Consider calling
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    //Custom Marker oluşturmak için
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

    /*    // Eczaneler listesindeki lokaston verisini LatLng'a convert edip for ile dönerek haritada marker oluşturuyor.
        for (int i = 0; i < getPharmacies().size(); i++) {

            String[] location = getPharmacies().get(i).enlem.split(",");
            double lat = Double.parseDouble(location[0]);
            double lon = Double.parseDouble(location[1]);
            LatLng latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(getPharmacies().get(i).eczaneAdi + " ECZANESİ")
                    .icon(bitmapDescriptorFromVector(this, R.drawable.icon_pharmacy)))
            ;

            //Telefon konumunu alıp açılışta otomatik zoom yapıyor.
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(MY_LATLNG)
                    .zoom(17)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MY_LATLNG, 13));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            enableUserLocation();*/
        }
    }

