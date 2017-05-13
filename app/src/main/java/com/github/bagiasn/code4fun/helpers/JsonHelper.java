package com.github.bagiasn.code4fun.helpers;

import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.CategoryChild;
import com.github.bagiasn.code4fun.models.database.Organization;

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
                    for (int j = 0; i < requiredAttr.length(); i++) {
                        JSONObject child = root.getJSONObject(j);
                        if (child != null) {
                            CategoryChild categoryChild= new CategoryChild();
                            categoryChild.setTitle(child.getString("title"));
                            categoryChild.setId(child.getString("_id"));
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
}
