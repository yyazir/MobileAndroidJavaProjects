package com.yumel.rehber;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PostPharmacy {
    @SerializedName("success")
    boolean success;

    @SerializedName("result")
    ArrayList<Pharmacy> result;

    static class Pharmacy implements Serializable {

        public Pharmacy(String name, String dist, String address, String phone, String loc) {
            this.name = name;
            this.district = dist;
            this.address = address;
            this.phone = phone;
            this.location = loc;
            this.date = date;
            this.id = id;
        }

        @SerializedName("name")
        String name;

        @SerializedName("dist")
        String district;

        @SerializedName("address")
        String address;

        @SerializedName("phone")
        String phone;

        @SerializedName("loc")
        String location;

        @SerializedName("id")
        int id;

        @SerializedName("date")
        String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String dist) {
            this.district = dist;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String loc) {
            this.location = loc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public boolean isSuccess() {
        return success;
    }


    public ArrayList<Pharmacy> getResult() {
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(ArrayList<Pharmacy> result) {
        this.result = result;
    }
}
