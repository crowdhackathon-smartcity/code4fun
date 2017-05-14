package com.github.bagiasn.code4fun.helpers;

import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.models.database.Organization;
import com.github.bagiasn.code4fun.models.database.OrganizationMarker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
            JSONArray root = new JSONArray(json);

            for (int i = 0; i < root.length(); i++) {
                JSONObject c = root.getJSONObject(i);

                String id = c.getString("_id");
                String category = c.getString("category");
                String title = c.getString("title");
                String orgCategory = c.getString("organizationCategory");
                String url = c.getString("url");
                JSONArray requiredAttr = c.getJSONArray("requiredServices");
                // Start building the attribute.
                Attribute attr = new Attribute();
                if (requiredAttr == null || requiredAttr.length() == 0) {
                    // This is a document.
                    attr.setChildrenList(null);
                } else {
                    ArrayList<CategoryChild> childAttributes = new ArrayList<>();
                    for (int j = 0; j < requiredAttr.length(); j++) {
                        JSONObject child = requiredAttr.getJSONObject(j);
                        if (child != null) {
                            CategoryChild categoryChild= new CategoryChild();
                            categoryChild.setTitle(child.getString("title"));
                            categoryChild.setId(child.getString("id"));
                            childAttributes.add(categoryChild);
                        }
                    }
                    attr.setChildrenList(childAttributes);
                }
                JSONArray requiredDocs = c.getJSONArray("requiredDocuments");
                ArrayList<String> childDocs = new ArrayList<>();
                for (int j = 0; j < requiredDocs.length(); j++) {
                    String doc = (String) requiredDocs.get(j);
                    if (doc != null && !doc.isEmpty()) {
                        childDocs.add(doc);
                    }
                }
                attr.setDocsList(childDocs);
                attr.setId(id);
                attr.setName(title);
                attr.setCategory(category);
                if (url.isEmpty())
                    attr.setExternalLink("");
                else
                    attr.setExternalLink(url);
                Organization org = new Organization();
                org.setName(orgCategory);
                attr.setOwner(org);

                attributes.add(attr);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attributes;
    }

    public static ArrayList<OrganizationMarker> getMarkers(String json) {
        ArrayList<OrganizationMarker> markers = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");
            for(int i = 0; i < results.length(); i++) {
                JSONObject item = results.getJSONObject(i);
                String name = item.getString("name");
                double longtitude = item.getDouble("longitude");
                double latitude = item.getDouble("latitude");
                OrganizationMarker marker = new OrganizationMarker();
                marker.setName(name);
                marker.setLatitude(latitude);
                marker.setLongtitude(longtitude);
                markers.add(marker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return markers;
    }

    public static Attribute getAttribute(String json, boolean isRootArray) {
        Attribute attribute = new Attribute();
        try {
            JSONObject item;
            if (isRootArray) {
                JSONArray root = new JSONArray(json);
                item = root.getJSONObject(0);
            } else {
                item = new JSONObject(json);
            }

            String id = item.getString("_id");
            String category = item.getString("category");
            String title = item.getString("title");
            String orgCategory = item.getString("organizationCategory");
            String url = item.getString("url");
            JSONArray requiredAttr = item.getJSONArray("requiredServices");
            // Start building the attribute.
            if (requiredAttr == null || requiredAttr.length() == 0) {
                // This is a document.
                attribute.setChildrenList(null);
            } else {
                ArrayList<CategoryChild> childAttributes = new ArrayList<>();
                for (int j = 0; j < requiredAttr.length(); j++) {
                    JSONObject child = requiredAttr.getJSONObject(j);
                    if (child != null) {
                        CategoryChild categoryChild= new CategoryChild();
                        categoryChild.setTitle(child.getString("title"));
                        categoryChild.setId(child.getString("id"));
                        childAttributes.add(categoryChild);
                    }
                }
                attribute.setChildrenList(childAttributes);
            }
            JSONArray requiredDocs = item.getJSONArray("requiredDocuments");
            ArrayList<String> childDocs = new ArrayList<>();
            for (int j = 0; j < requiredDocs.length(); j++) {
                String doc = (String) requiredDocs.get(j);
                if (doc != null && !doc.isEmpty()) {
                    childDocs.add(doc);
                }
            }
            attribute.setDocsList(childDocs);
            attribute.setId(id);
            attribute.setName(title);
            attribute.setCategory(category);
            if (url.isEmpty())
                attribute.setExternalLink("");
            else
                attribute.setExternalLink(url);
            Organization org = new Organization();
            org.setName(orgCategory);
            attribute.setOwner(org);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return attribute;
    }
}
