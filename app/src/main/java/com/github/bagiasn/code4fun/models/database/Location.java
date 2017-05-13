package com.github.bagiasn.code4fun.models.database;

/**
 * The location holds info about the whereabouts of an organization.
 * An organization can have many.
 */

public class Location {
    private String address;
    private String postalCode;
    private String telephone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
