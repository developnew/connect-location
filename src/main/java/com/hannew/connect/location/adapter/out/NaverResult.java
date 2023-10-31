package com.hannew.connect.location.adapter.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverResult {
    @JsonProperty("lastBuildDate")
    private String lastBuildDate;

    @JsonProperty("total")
    private int total;

    @JsonProperty("start")
    private int start;

    @JsonProperty("display")
    private int display;

    @JsonProperty("items")
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        @JsonProperty("title")
        private String title;

        @JsonProperty("link")
        private String link;

        @JsonProperty("category")
        private String category;

        @JsonProperty("description")
        private String description;

        @JsonProperty("telephone")
        private String telephone;

        @JsonProperty("address")
        private String address;

        @JsonProperty("roadAddress")
        private String roadAddress;

        @JsonProperty("mapx")
        private String mapx;

        @JsonProperty("mapy")
        private String mapy;
    }
}
//public record NaverResult(String lastBuildDate, int total, int start, int display, List<Item> items) {
//
//    public record Item(String title, String link, String category, String description, String telephone,
//                       String address, String roadAddress, String mapx, String mapy) {
//    }
//}