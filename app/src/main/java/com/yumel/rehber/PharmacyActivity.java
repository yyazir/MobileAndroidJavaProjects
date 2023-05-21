package com.yumel.rehber;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumel.rehber.databinding.ActivityPharmaBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PharmacyActivity extends AppCompatActivity {

    ENabizPharmacyApiInterface eNabizPharmacyApiInterface;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<ENabizPharmacy> arrayPostPharmacyList;
    public String selectedNeighborhood = " ";
    Bundle bundle;
    Intent intent;
    FilterBottomSheetDialogFilter pharmacyBottomSheetDialogFilter;
    private int ilKodu;
    private String ilceKodu;
    private int nobetGunu;
    ProgressBar progressBar;
    com.yumel.rehber.databinding.ActivityPharmaBinding binding;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivityPharmaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        intent = getIntent();
        progressBar = binding.progressBar;
        ilKodu = intent.getIntExtra("city",0);
        if (Objects.equals(intent.getStringExtra("district"), "Seçiniz")){
            ilceKodu = "Tumu";
        }else {
            ilceKodu = intent.getStringExtra("district");
        }
        nobetGunu = intent.getIntExtra("bout",0);
        bundle = intent.getExtras();
        String baseUrl = "https://enabiz.gov.tr/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        eNabizPharmacyApiInterface = RetrofitIns.getPharmacyRetrofit().create(ENabizPharmacyApiInterface.class);
        retrofit.create(ENabizPharmacyApiInterface.class);
        Call<List<ENabizPharmacy>> pharmacyCall = eNabizPharmacyApiInterface.getNobetciEczaneler(ilKodu, ilceKodu, nobetGunu);
        try {
            selectedNeighborhood = bundle.getString("neighborhood");
        } catch (Exception e) {
        }
        recyclerView = binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.query.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pharmacyBottomSheetDialogFilter = new FilterBottomSheetDialogFilter();
                pharmacyBottomSheetDialogFilter.show(getSupportFragmentManager(), "dialog");

            }
        });

        loadPharmacies();

        binding.goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OsmPharmacyMap.class);
                intent.putExtra("xx", arrayPostPharmacyList);
                startActivity(intent);
            }
        });

        binding.nearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OsmNearMeMap.class);
                intent.putExtra("xx", arrayPostPharmacyList);
                startActivity(intent);

            }
        });
    }

    public void loadPharmacies() {
        arrayPostPharmacyList = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(), arrayPostPharmacyList);
        recyclerView.setAdapter(adapter);
        Call<List<ENabizPharmacy>> pharmacyCall = eNabizPharmacyApiInterface.getNobetciEczaneler(ilKodu, ilceKodu, nobetGunu);
        pharmacyCall.enqueue(new Callback<List<ENabizPharmacy>>() {

            @SuppressLint({"NotifyDataSetChanged", "RestrictedApi"})
            @Override
            public void onResponse(@NonNull Call<List<ENabizPharmacy>> call, @NonNull Response<List<ENabizPharmacy>> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    List<ENabizPharmacy> pharmacies = response.body();
                    arrayPostPharmacyList.clear();
                    assert pharmacies != null;
                    arrayPostPharmacyList.addAll(pharmacies);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: Veriler Yüklendi");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ENabizPharmacy>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }



}



