package com.github.bagiasn.myticket.models.database;

/**
 * This model represents an organization.
 * It follows the structure of the server's database.
 */

public class Organization {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
