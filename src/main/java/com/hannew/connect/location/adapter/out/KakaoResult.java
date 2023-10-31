package com.hannew.connect.location.adapter.out;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoResult {
    @JsonProperty("documents")
    private List<Document> documents;

    @JsonProperty("meta")
    private Meta meta;

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("distance")
        private String distance;

        @JsonProperty("id")
        private String id;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("place_url")
        private String placeUrl;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        @JsonProperty("x")
        private String x;

        @JsonProperty("y")
        private String y;
    }

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("is_end")
        private boolean isEnd;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("same_name")
        private SameName sameName;

        @JsonProperty("total_count")
        private int totalCount;
    }

    @Getter
    @Setter
    public static class SameName {
        @JsonProperty("keyword")
        private String keyword;

        @JsonProperty("region")
        private List<Object> region;

        @JsonProperty("selected_region")
        private String selectedRegion;
    }
}