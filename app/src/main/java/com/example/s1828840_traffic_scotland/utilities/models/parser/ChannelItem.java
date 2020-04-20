/*
 * Name: Ross Taggart
 * ID: S1828840
 */

// Imports

package com.example.s1828840_traffic_scotland.utilities.models.parser;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ChannelItem
 * Description: Class that contains information on an individual item from one of the TrafficScotland RSS feeds
 */
public class ChannelItem {
    // Private Variables

    // Title - The title and identifier of the item
    private String Title;

    // Description - Brief description of the current item
    private String Description;

    // Link - URL link to detailed information on the item
    private String Link;

    // Point - The latitude and longitude of the current items
    private ItemPoint Point;

    // Author - Name of the author of the item
    private String Author;

    // Comments - Any comments made on the current item
    private String Comments;

    // Pub_Date - The date that the current item was published
    private String Pub_Date;

    // END: Private Variables

    // Constructors

    public ChannelItem() {}

    // END: Constructors

    // Getters and Setters

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public ItemPoint getPoint() {
        return Point;
    }

    public void setPoint(ItemPoint point) {
        Point = point;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getPub_Date() {
        return Pub_Date;
    }

    public void setPub_Date(String pub_Date) {
        Pub_Date = pub_Date;
    }

    // END: Getters and Setters
}
