package com.yumel.rehber;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ENabizPharmacy implements Serializable {

    @SerializedName("eczaneAdi")
    private String pharmacyName;

    @SerializedName("eczaneIlAdi")
    private String cityCode;

    @SerializedName("eczaneIlceAdi")
    private String districtName;

    @SerializedName("eczaneAdres")
    private String address;

    @SerializedName("tarih")
    private String date;

    @SerializedName("baslangic")
    private String startTime;

    @SerializedName("bitis")
    private String endTime;

    @SerializedName("enlem")
    private String latitude;

    @SerializedName("boylam")
    private String longitude;

    @SerializedName("glnNo")
    private String glnNumber;

    @SerializedName("id")
    private int id;

    @SerializedName("olusturmaTarihi")
    private String creationDate;

    @SerializedName("guncellemeTarihi")
    private Object updateDate;

    @SerializedName("silindi")
    private Object deleted;


    public ENabizPharmacy(String pharmacyName, String cityCode, String districtName, String address, String date, String startTime, String endTime, String latitude, String longitude, String glnNumber, int id, String creationDate, Object updateDate, Object deleted) {
        this.pharmacyName = pharmacyName;
        this.cityCode = cityCode;
        this.districtName = districtName;
        this.address = address;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.glnNumber = glnNumber;
        this.id = id;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.deleted = deleted;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGlnNumber() {
        return glnNumber;
    }

    public void setGlnNumber(String glnNumber) {
        this.glnNumber = glnNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
        this.deleted = deleted;
    }
}



