package com.tuvarna.delivery.authentication.gui;

import java.util.prefs.Preferences;

public class AccessTokenStorage {
    private static final String ACCESS_TOKEN_KEY = "access_token";

    public static void storeAccessToken(String accessToken) {
        Preferences prefs = Preferences.userNodeForPackage(AccessTokenStorage.class);
        prefs.put(ACCESS_TOKEN_KEY, accessToken);
    }

    public static String retrieveAccessToken() {
        Preferences prefs = Preferences.userNodeForPackage(AccessTokenStorage.class);
        return prefs.get(ACCESS_TOKEN_KEY, null);
    }
}
