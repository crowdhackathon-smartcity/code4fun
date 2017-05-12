package com.github.bagiasn.myticket.models.database;

/**
 * This model represents a given (from the server) ticket.
 * It follows the structure of the server's database.
 */

public class Ticket {
    private String service;
    private Division division;
    private String date;
    private int state;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
