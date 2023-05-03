package com.yumel.rehber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {

    ArrayList<ENabizPharmacy> pharmacies;
    Context context;

    public Adapter(Context context, ArrayList<ENabizPharmacy> pharmacies) {
        this.pharmacies = pharmacies;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return new CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(pharmacies.get(position).getPharmacyName()+" ECZANESİ");
        holder.district.setText(pharmacies.get(position).getDistrictName());
        holder.address.setText(pharmacies.get(position).getAddress());
        holder.startTime.setText(pharmacies.get(position).getStartTime());
        holder.endTime.setText(pharmacies.get(position).getEndTime());
        holder.routeBtn.setOnClickListener(view -> {
            double latitude = Double.parseDouble(pharmacies.get(position).getLatitude());
            double longitude = Double.parseDouble(pharmacies.get(position).getLongitude());
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", latitude, longitude, pharmacies.get(position).getPharmacyName());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            view.getContext().startActivity(intent);
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = Double.parseDouble(pharmacies.get(position).getLatitude());
                double longitude = Double.parseDouble(pharmacies.get(position).getLongitude());;
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", latitude, longitude, pharmacies.get(position).getPharmacyName() + " ECZANESİ");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Nöbetçi Eczane";
                String Sub = uri;
                intent.putExtra(Intent.EXTRA_TEXT, Body);
                intent.putExtra(Intent.EXTRA_TEXT, Sub);
                view.getContext().startActivity(Intent.createChooser(intent, "Nöbetcçi Eczane Paylaş"));

            }
        });


    }

    @Override
    public int getItemCount() {
        return (pharmacies != null) ? pharmacies.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView district;
        TextView address;
        TextView startTime;
        TextView endTime;
        ImageButton routeBtn;
        ImageButton shareBtn;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            district = itemView.findViewById(R.id.district);
            address = itemView.findViewById(R.id.adress);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            routeBtn = itemView.findViewById(R.id.routeBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
        }
    }
}


