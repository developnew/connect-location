package com.hannew.connect.location.application.helper;

public class HTMLTagRemover {
    public static String removeHtmlTags(String input) {
        return input.replaceAll("\\<.*?\\>", "");
    }

    public static String removeHtmlTagsAndSpace(String input) {
        return input.replaceAll("\\<.*?\\>", "").replaceAll("\\s+", "");
    }
}