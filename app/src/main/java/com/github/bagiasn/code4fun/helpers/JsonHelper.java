package com.github.bagiasn.code4fun.helpers;

import com.github.bagiasn.code4fun.models.database.Attribute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper class for parsing json strings.
 */

public class JsonHelper {

    public static ArrayList<Attribute> getAttributes(String json) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(json);

            JSONArray services = jsonObj.getJSONArray("services");

            for (int i = 0; i < services.length(); i++) {
                JSONObject c = services.getJSONObject(i);

                String name = c.getString("name");
                // ...
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attributes;
    }
}
