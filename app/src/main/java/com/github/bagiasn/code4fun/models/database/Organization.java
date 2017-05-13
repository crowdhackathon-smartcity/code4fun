package com.github.bagiasn.code4fun.models.database;

import java.util.ArrayList;

/**
 * This model represents an organization.
 */

public class Organization {
    private String name;
    private ArrayList<Location> locationList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
