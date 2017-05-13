package com.github.bagiasn.code4fun.models.database;

import java.util.ArrayList;

/**
 * This model represents one of the many possible services, provided by an organization.
 * It has a recursive nature, for easier representations of certifications etc.
 */

public class Attribute {
    private Organization owner;
    private String name;
    private ArrayList<Attribute> attributeList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOwner() {
        return owner;
    }

    public void setOwner(Organization owner) {
        this.owner = owner;
    }

    public ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(ArrayList<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
