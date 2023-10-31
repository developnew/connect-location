package com.hannew.connect.location.domain;

import java.util.List;

public class PlaceSearchResult {
    public List<PlaceInfo> getPlaceInfos() {
        return placeInfos;
    }

    List<PlaceInfo> placeInfos;

    public void setPlaceInfos(List<PlaceInfo> placeInfos) {
        this.placeInfos = placeInfos;
    }
}

