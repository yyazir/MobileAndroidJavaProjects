package com.yumel.rehber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class PharmacyBottomSheetDialogFilter extends BottomSheetDialogFragment {
    View view;
    DataBaseHelper db;
    ArrayAdapter<String> adapterNeighborhood;
    ArrayList<String> arrayDistrict;
    ArrayAdapter<String> adapterDistrict;
    ArrayList<PostPharmacy.Pharmacy> pharma;
    Bundle bundle;
    Spinner neighborhoodSpinner;
    Spinner districtSpinner;
    Button filterButton;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pharmacy_query_bottom_sheet_layout, container, false);


        neighborhoodSpinner = view.findViewById(R.id.neighborhoodspinner);
        districtSpinner = view.findViewById(R.id.districtspinner);
        filterButton = view.findViewById(R.id.filter);

        bundle = new Bundle();
        //ArrayList<String> arrayNeighborhood = new ArrayList<>();
        //adapterNeighborhood = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, arrayNeighborhood);
        //neighborhoodSpinner.setAdapter(adapterNeighborhood);
        //adapterDistrict = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, arrayDistrict);
        //districtSpinner.setAdapter(adapterDistrict);
        //adapterDistrict.notifyDataSetChanged();

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //arrayNeighborhood.clear();
                //adapterNeighborhood.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bundle.putString("neighborhood", neighborhoodSpinner.getSelectedItem().toString());
                //adapterNeighborhood.notifyDataSetChanged();
                //Intent intent = new Intent(getActivity(), PharmacyActivity.class);
                //intent.putExtras(bundle);
                //startActivity(intent);


            }
        });
        return view;


    }
}

