package com.github.bagiasn.myticket.models.database;

/**
 * This model represents one of the many possible services, provided by an organization.
 * It follows the structure of the server's database.
 */

public class Division {
    private int organizationID;
    private String name;
    private int estimatedTime;

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
