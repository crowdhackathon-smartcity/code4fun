package com.github.bagiasn.code4fun.helpers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
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

    public static String makeNlpRequest(String url, String data) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", data);
            return makePostJsonRequest(url, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String makePostJsonRequest(String url, JSONObject jsonObject)
    {
        HttpURLConnection httpCon;
        String result = null;
        try {
            httpCon = (HttpURLConnection) ((new URL (url).openConnection()));
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestMethod("POST");
            httpCon.connect();

            String toSend = jsonObject.toString();

            OutputStream os = httpCon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(toSend);
            writer.close();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

        } catch (IOException e) {
            Log.d("HttpHelper", e.getMessage());
        }

        return result;
    }
}
