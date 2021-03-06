package com.github.bagiasn.code4fun.models.database;

import java.util.ArrayList;

/**
 * This model represents one of the many possible services, provided by an organization.
 * It has a recursive nature, for easier representations of certifications etc.
 */

public class Attribute {

    private String id;
    private Organization owner;
    private String category;
    private String name;
    private String externalLink;
    private ArrayList<CategoryChild> childrenList;

    public ArrayList<String> getDocsList() {
        return docsList;
    }

    public void setDocsList(ArrayList<String> docsList) {
        this.docsList = docsList;
    }

    private ArrayList<String> docsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Organization getOwner() {
        return owner;
    }

    public void setOwner(Organization owner) {
        this.owner = owner;
    }

    public ArrayList<CategoryChild> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<CategoryChild> childrenList) {
        this.childrenList = childrenList;
    }
}
