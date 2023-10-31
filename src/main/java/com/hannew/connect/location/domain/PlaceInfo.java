package com.hannew.connect.location.domain;

public class PlaceInfo {

    private String name;
    private String address;

    public PlaceInfo() {
    }

    public PlaceInfo(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String placeName) {
        this.name = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
