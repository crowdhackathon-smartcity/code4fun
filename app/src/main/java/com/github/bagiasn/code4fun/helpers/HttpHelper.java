package com.github.bagiasn.code4fun.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
/**
 * Helper class for HTTP requests
 */
public class HttpHelper {

    public static String makeJsonRequest(String url)
    {
        try {
            String finalUrl = url.replaceAll(" ","%20");
            URLConnection connection = new URL(finalUrl).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return reader.readLine();

        } catch (Exception e) {
            return null;
        }

    }
}
