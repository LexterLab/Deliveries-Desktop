package com.tuvarna.delivery.utils;

public class ErrorFormatter {
    public static String[] formatError(String errorMessage) {
        return errorMessage
                .replace("{","").replace("}", "").split(":");
    }
}
