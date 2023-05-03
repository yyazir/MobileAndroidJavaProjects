package com.yumel.rehber;
import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

public class EarthquakeAFAD {
    @SerializedName("id")
    private int id;

    @SerializedName("eventDate")
    private Date eventDate;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("magnitude")
    private double magnitude;

    @SerializedName("magnitudeType")
    private String magnitudeType;

    @SerializedName("location")
    private String location;

    @SerializedName("depth")
    private double depth;

    @SerializedName("rms")
    private double rms;

    @SerializedName("erh")
    private double erh;

    @SerializedName("erz")
    private double erz;

    @SerializedName("gap")
    private int gap;

    @SerializedName("eaeventId")
    private int eaeventId;

    @SerializedName("crustModelId")
    private int crustModelId;

    @SerializedName("eventTypeId")
    private int eventTypeId;

    @SerializedName("eventType")
    private String eventType;

    @SerializedName("magnitudeDescription")
    private String magnitudeDescription;

    @SerializedName("formattedDate")
    private String formattedDate;

    @SerializedName("depthDescription")
    private String depthDescription;

    @SerializedName("refId")
    private int refId;

    @SerializedName("province")
    private String province;

    @SerializedName("district")
    private String district;

    @SerializedName("typeName")
    private String typeName;

    @SerializedName("typeNameEng")
    private String typeNameEng;

    @SerializedName("magnitudeName")
    private String magnitudeName;

    @SerializedName("magnitudeNameEng")
    private String magnitudeNameEng;

    @SerializedName("timeName")
    private String timeName;

    @SerializedName("timeNameEng")
    private String timeNameEng;

    @SerializedName("momentTensor")
    private String momentTensor;

    @SerializedName("distanceInformation")
    private String distanceInformation;

    @SerializedName("bulletins")
    private String bulletins;

    @SerializedName("moments")
    private String moments;

    @SerializedName("amplitudes")
    private String amplitudes;

    public EarthquakeAFAD(int id, Date eventDate, double longitude, double latitude, double magnitude, String magnitudeType, String location, double depth, double rms, double erh, double erz, int gap, int eaeventId, int crustModelId, int eventTypeId, String eventType, String magnitudeDescription, String formattedDate, String depthDescription, int refId, String province, String district, String typeName, String typeNameEng, String magnitudeName, String magnitudeNameEng, String timeName, String timeNameEng, String momentTensor, String distanceInformation, String bulletins, String moments, String amplitudes) {
        this.id = id;
        this.eventDate = eventDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.magnitude = magnitude;
        this.magnitudeType = magnitudeType;
        this.location = location;
        this.depth = depth;
        this.rms = rms;
        this.erh = erh;
        this.erz = erz;
        this.gap = gap;
        this.eaeventId = eaeventId;
        this.crustModelId = crustModelId;
        this.eventTypeId = eventTypeId;
        this.eventType = eventType;
        this.magnitudeDescription = magnitudeDescription;
        this.formattedDate = formattedDate;
        this.depthDescription = depthDescription;
        this.refId = refId;
        this.province = province;
        this.district = district;
        this.typeName = typeName;
        this.typeNameEng = typeNameEng;
        this.magnitudeName = magnitudeName;
        this.magnitudeNameEng = magnitudeNameEng;
        this.timeName = timeName;
        this.timeNameEng = timeNameEng;
        this.momentTensor = momentTensor;
        this.distanceInformation = distanceInformation;
        this.bulletins = bulletins;
        this.moments = moments;
        this.amplitudes = amplitudes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getMagnitudeType() {
        return magnitudeType;
    }

    public void setMagnitudeType(String magnitudeType) {
        this.magnitudeType = magnitudeType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getRms() {
        return rms;
    }

    public void setRms(double rms) {
        this.rms = rms;
    }

    public double getErh() {
        return erh;
    }

    public void setErh(double erh) {
        this.erh = erh;
    }

    public double getErz() {
        return erz;
    }

    public void setErz(double erz) {
        this.erz = erz;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getEaeventId() {
        return eaeventId;
    }

    public void setEaeventId(int eaeventId) {
        this.eaeventId = eaeventId;
    }

    public int getCrustModelId() {
        return crustModelId;
    }

    public void setCrustModelId(int crustModelId) {
        this.crustModelId = crustModelId;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMagnitudeDescription() {
        return magnitudeDescription;
    }

    public void setMagnitudeDescription(String magnitudeDescription) {
        this.magnitudeDescription = magnitudeDescription;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getDepthDescription() {
        return depthDescription;
    }

    public void setDepthDescription(String depthDescription) {
        this.depthDescription = depthDescription;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeNameEng() {
        return typeNameEng;
    }

    public void setTypeNameEng(String typeNameEng) {
        this.typeNameEng = typeNameEng;
    }

    public String getMagnitudeName() {
        return magnitudeName;
    }

    public void setMagnitudeName(String magnitudeName) {
        this.magnitudeName = magnitudeName;
    }

    public String getMagnitudeNameEng() {
        return magnitudeNameEng;
    }

    public void setMagnitudeNameEng(String magnitudeNameEng) {
        this.magnitudeNameEng = magnitudeNameEng;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public String getTimeNameEng() {
        return timeNameEng;
    }

    public void setTimeNameEng(String timeNameEng) {
        this.timeNameEng = timeNameEng;
    }

    public String getMomentTensor() {
        return momentTensor;
    }

    public void setMomentTensor(String momentTensor) {
        this.momentTensor = momentTensor;
    }

    public String getDistanceInformation() {
        return distanceInformation;
    }

    public void setDistanceInformation(String distanceInformation) {
        this.distanceInformation = distanceInformation;
    }

    public String getBulletins() {
        return bulletins;
    }

    public void setBulletins(String bulletins) {
        this.bulletins = bulletins;
    }

    public String getMoments() {
        return moments;
    }

    public void setMoments(String moments) {
        this.moments = moments;
    }

    public String getAmplitudes() {
        return amplitudes;
    }

    public void setAmplitudes(String amplitudes) {
        this.amplitudes = amplitudes;
    }

    public GeoPoint getGeoPoint(){
        return new GeoPoint(this.latitude, this.longitude);

    }
}




