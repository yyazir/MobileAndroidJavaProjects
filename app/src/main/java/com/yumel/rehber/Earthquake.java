package com.yumel.rehber;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "earthquake")
public class Earthquake {
    @Element(name = "date", required = false)
    private String date;

    @Element(name = "time", required = false)
    private String time;

    @Element(name = "lon", required = false)
    private String lon;

    @Element(name = "lat", required = false)
    private String lat;

    @Element(name = "cent_depth", required = false)
    private String centDepth;

    @Element(name = "mw", required = false)
    private String mw;

    @Element(name = "mo", required = false)
    private String mo;

    @Element(name = "strike1", required = false)
    private String strike1;

    @Element(name = "dip1", required = false)
    private String dip1;

    @Element(name = "rake1", required = false)
    private String rake1;

    @Element(name = "strike2", required = false)
    private String strike2;

    @Element(name = "dip2", required = false)
    private String dip2;

    @Element(name = "rake2", required = false)
    private String rake2;

    @Element(name = "dc", required = false)
    private String dc;

    @Element(name = "vr", required = false)
    private String vr;

    @Element(name = "clvd", required = false)
    private String clvd;

    @Element(name = "computed", required = false)
    private String computed;

    @Element(name = "method", required = false)
    private String method;

    @Element(name = "beachball", required = false)
    private String beachball;

    public Earthquake() {
        this.date = date;
        this.time = time;
        this.lon = lon;
        this.lat = lat;
        this.centDepth = centDepth;
        this.mw = mw;
        this.mo = mo;
        this.strike1 = strike1;
        this.dip1 = dip1;
        this.rake1 = rake1;
        this.strike2 = strike2;
        this.dip2 = dip2;
        this.rake2 = rake2;
        this.dc = dc;
        this.vr = vr;
        this.clvd = clvd;
        this.computed = computed;
        this.method = method;
        this.beachball = beachball;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCentDepth() {
        return centDepth;
    }

    public void setCentDepth(String centDepth) {
        this.centDepth = (String) centDepth;
    }

    public String getMw() {
        return mw;
    }

    public void setMw(String mw) {
        this.mw = mw;
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = String.valueOf(mo);
    }

    public String getStrike1() {
        return strike1;
    }

    public void setStrike1(String strike1) {
        this.strike1 = strike1;
    }

    public String getDip1() {
        return dip1;
    }

    public void setDip1(String dip1) {
        this.dip1 = dip1;
    }

    public String getRake1() {
        return rake1;
    }

    public void setRake1(String rake1) {
        this.rake1 = rake1;
    }

    public String getStrike2() {
        return strike2;
    }

    public void setStrike2(String strike2) {
        this.strike2 = strike2;
    }

    public String getDip2() {
        return dip2;
    }

    public void setDip2(String dip2) {
        this.dip2 = dip2;
    }

    public String getRake2() {
        return rake2;
    }

    public void setRake2(String rake2) {
        this.rake2 = rake2;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getVr() {
        return vr;
    }

    public void setVr(String vr) {
        this.vr = vr;
    }

    public String getClvd() {
        return clvd;
    }

    public void setClvd(String clvd) {
        this.clvd = clvd;
    }

    public String getComputed() {
        return computed;
    }

    public void setComputed(String computed) {
        this.computed = computed;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBeachball() {
        return beachball;
    }

    public void setBeachball(String beachball) {
        this.beachball = beachball;
    }
}
