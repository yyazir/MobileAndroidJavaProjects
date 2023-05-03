package com.yumel.rehber;

public class CitiesAndDistricts {
    private String value, name, color;


    public CitiesAndDistricts(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

}
