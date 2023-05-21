package com.yumel.rehber;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yumel.rehber.databinding.AfadeqBottomSheetLayoutBinding;
import com.yumel.rehber.databinding.PharmacyBottomSheetDialogBinding;

import java.util.Locale;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    View view;
    int itemType;

    public BottomSheetDialog(int itemType) {
        this.itemType = itemType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (itemType) {
            case 1:
                PharmacyBottomSheetDialogBinding filterBinding;
                view = inflater.inflate(R.layout.filter_bottom_sheet_layout, container, false);
                filterBinding = PharmacyBottomSheetDialogBinding.inflate(getLayoutInflater());
                view = filterBinding.getRoot();
                Bundle filterArgs = getArguments();
                //GeoPoint geoPoint = new GeoPoint(filterArgs.getDouble("lat"), filterArgs.getDouble("lon"));
                filterBinding.pharmacyNameBottomText.setText(filterArgs.getString("name"));
                filterBinding.pharmacyDistrictBottomText.setText(filterArgs.getString("district"));
                filterBinding.pharmacyadressBottomText.setText(filterArgs.getString("address"));
                filterBinding.routeApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH,
                                "http://maps.google.com/maps?daddr=%f,%f",
                                filterArgs.getDouble("lat"),
                                filterArgs.getDouble("lon"));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        view.getContext().startActivity(intent);
                    }
                });

                filterBinding.shareApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH,
                                "http://maps.google.com/maps?daddr=%f,%f",
                                filterArgs.getDouble("lat"), filterArgs.getDouble("lon"),
                                filterArgs.getString("name") + " ECZANESİ");
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String body = "Nöbetçi Eczane";
                        String sub = uri;
                        intent.putExtra(Intent.EXTRA_TEXT, body);
                        intent.putExtra(Intent.EXTRA_TEXT, sub);
                        view.getContext().startActivity(Intent.createChooser(intent, "Nöbetçi Eczane Paylaş"));
                    }
                });
                break;

            case 2:
                AfadeqBottomSheetLayoutBinding afadeqBinding;
                view = inflater.inflate(R.layout.afadeq_bottom_sheet_layout, container, false);
                afadeqBinding = AfadeqBottomSheetLayoutBinding.inflate(getLayoutInflater());
                view = afadeqBinding.getRoot();
                Bundle earthquakeAFADArgs = getArguments();
                assert earthquakeAFADArgs != null;
                afadeqBinding.locationEQText.setText(earthquakeAFADArgs.getString("location"));
                afadeqBinding.dateEQText.setText(earthquakeAFADArgs.getString("date"));
                afadeqBinding.depthEQText.setText(earthquakeAFADArgs.get("depth").toString());
                afadeqBinding.magnitudeEQText.setText(earthquakeAFADArgs.get("magnitude").toString());
                afadeqBinding.shareEQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH,
                                "http://maps.google.com/maps?daddr=%f,%f",
                                earthquakeAFADArgs.getDouble("lat"),
                                earthquakeAFADArgs.getDouble("lon"),
                                earthquakeAFADArgs.getString("location") + " " + earthquakeAFADArgs.getString("magnitude") + " Depremi");
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String body = "DEPREM";
                        String sub = uri;
                        intent.putExtra(Intent.EXTRA_TEXT, body);
                        intent.putExtra(Intent.EXTRA_TEXT, sub);
                        view.getContext().startActivity(Intent.createChooser(intent, "Deprem Paylaş"));

                    }
                });
                break;

        }
        return view;
    }
}
