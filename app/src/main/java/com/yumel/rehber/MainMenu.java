package com.yumel.rehber;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainMenu extends AppCompatActivity {
    private String selectedCity;
    private String selectedDistrict;
    private List<CitiesAndDistricts> districts;
    private List<CitiesAndDistricts> cities;
    Spinner citiesSpinner, districtSpinner, boutSpinner;
    CheckBox rememberMeCheckBox;
    AlertDialog.Builder builder;
    String[] city, district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar())
                .hide();
        setContentView(R.layout.activity_main_menu);

        CardView pharmacy = findViewById(R.id.card1);
        pharmacy.setOnClickListener(view -> {
            fetchCities();
        });

        CardView application = findViewById(R.id.card2);
        application.setOnClickListener(view -> {
        });

        CardView mapsOsm = findViewById(R.id.card3);
        mapsOsm.setOnClickListener(view -> {
            Intent intent = new Intent(this, OsmEarthquakeMap.class);
            startActivity(intent);
        });
    }

    private void SelectionPopup(List<CitiesAndDistricts> cities) {
        List<String> cityNames = new ArrayList<String>();
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Lütfen Seçim Yapınız.");
        View view = getLayoutInflater().inflate(R.layout.selection_popup, null);
        Spinner citiesSpinner = view.findViewById(R.id.cities);
        districtSpinner = view.findViewById(R.id.district);
        boutSpinner = view.findViewById(R.id.question);
        rememberMeCheckBox = view.findViewById(R.id.rememberMe);
        List<String> boutList = new ArrayList<String>();
        boutList.add("Bugün");
        boutList.add("Yarın");
        for (int i = 0; i < cities.size(); i++) {
            cityNames.add(cities.get(i).getName());
        }
        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNames);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(adapterCities);
        citiesSpinner.setSelection(0);
        builder.setView(view);
        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = citiesSpinner.getSelectedItem().toString();
                if (!selectedCity.equals("Seçiniz")) {
                    city = selectedCity.split("-");
                    fetchDistricts(Integer.parseInt(city[0]), view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterBout = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, boutList);
        adapterBout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boutSpinner.setAdapter(adapterBout);
        boutSpinner.setSelection(0);
        rememberMeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rememberMeCheckBox.isChecked()) {

                }
            }
        });

        builder.setPositiveButton("Tamam", null);
        builder.setNegativeButton("İptal", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view1 -> {
                selectedCity = (String) citiesSpinner.getSelectedItem();
                if (!Objects.equals(selectedCity, "Seçiniz")) {
                    Intent intent = new Intent(getApplicationContext(), PharmacyActivity.class);
                    if (districtSpinner.getSelectedItem() != null) {
                        selectedDistrict = districtSpinner.getSelectedItem().toString();
                    }else{
                        selectedDistrict = "Seçiniz";
                    }

                    if (Objects.equals(boutSpinner.getSelectedItem().toString(), "Bugün")) {
                        intent.putExtra("bout", 0);
                    } else {
                        intent.putExtra("bout", 1);
                    }
                    intent.putExtra("city", Integer.parseInt(city[0]));
                    intent.putExtra("district", selectedDistrict);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen il seçimi yapınız..", Toast.LENGTH_SHORT).show();
                }
            });
        });

        alertDialog.show();
    }

    private List<CitiesAndDistricts> parseCitiesFromHtml(String html) {
        List<CitiesAndDistricts> citiesAndDistricts = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements options = document.select("option");
        for (Element option : options) {
            String id = option.attr("value");
            String name = option.text();
            citiesAndDistricts.add(new CitiesAndDistricts(id, name));
        }
        return citiesAndDistricts;
    }

    private Retrofit getUrl() {
        return new Retrofit.Builder().baseUrl("https://enabiz.gov.tr/").addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    private void fetchCities() {
        CityInterface cityInterface;
        getUrl();
        cityInterface = getUrl().create(CityInterface.class);
        Call<String> call = cityInterface.getCities();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String htmlCities = response.body();
                    if (htmlCities != null) {
                        cities = parseCitiesFromHtml(htmlCities);
                        SelectionPopup(cities);
                    } else {
                        Toast.makeText(MainMenu.this, "Bir hata oluştu.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(MainMenu.this, "İşleminiz gerçkeleştirilemedi.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchDistricts(int selected, View view) {
        DistrictsInterface districtsInterface;
        districtsInterface = getUrl().create(DistrictsInterface.class);
        Call<String> call = districtsInterface.getDistricts(selected, 0);
        call.enqueue(new Callback<String>() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d(TAG, response.body());
                if (response.body() != null) {
                    String htmlDistricts = response.body();
                    districts = parseCitiesFromHtml(htmlDistricts);
                    List<String> districtNames = new ArrayList<String>();
                    for (int i = 0; i < districts.size(); i++) {
                        districtNames.add(districts.get(i).getName());
                    }
                    ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, districtNames);
                    adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpinner.setAdapter(adapterDistrict);
                    districtSpinner.setSelection(0);
                    builder.setView(view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });

    }

}