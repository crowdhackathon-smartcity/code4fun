package com.github.bagiasn.myticket.models.app;

/**
 * Class for representing an option in the menu.
 */

public class Option {
    public String title;
    public String subTitle;
    public int iconId;

    public Option(String title, String subTitle, int iconId) {
        this.title = title;
        this.subTitle = subTitle;
        this.iconId = iconId;
    }
}
