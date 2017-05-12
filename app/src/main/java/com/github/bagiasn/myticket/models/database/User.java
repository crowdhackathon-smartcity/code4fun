package com.github.bagiasn.myticket.models.database;

/**
 * This model represents a registered user.
 * It follows the structure of the server's database.
 */

public class User {
    private String ssn;
    private String password;
    private String name;
    private String surname;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
