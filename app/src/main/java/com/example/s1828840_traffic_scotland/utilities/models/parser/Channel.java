/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.parser;

// Imports

import java.util.ArrayList;
import java.util.List;

// Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Channel
 * Description: Class that contains the result of a TrafficScotland RSS fetch
 */
public class Channel {
    // Private variables

    // Title - The title that identifies the channel e.g. "Current Incidents"
    public String Title;

    // Description - A description of the data included in the channel
    public String Description;

    // Link - A URL link to the web view of the feed
    public String Link;

    // Language - The language code of the feed
    public String Language;

    // Copyright - The copyright notice of the feed
    public String Copyright;

    // Managing_Editor - The name of the managing editor
    public String Managing_Editor;

    // Web_Master - Web master of the feed
    public String Web_Master;

    // Last_Build_Date - The Date of the last feed update
    public String Last_Build_Date;

    // Docs - Link to the TrafficScotland feed documentation
    public String Docs;

    // Rating - Rating of the feed
    public String Rating;

    // TTL - TTL of the feed
    public int TTL;

    // Data_Present - Flag variable that signifies if the feed had any data
    public boolean Data_Present;

    // END: Private Variables

    // Constructors

    public Channel() {
        Items = new ArrayList<ChannelItem>();
    }

    // END: Constructors

    // Getters and Setters

    public void setItems(ArrayList<ChannelItem> items) {
        Items = items;
    }

    public ArrayList<ChannelItem> Items;

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

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCopyright() {
        return Copyright;
    }

    public void setCopyright(String copyright) {
        Copyright = copyright;
    }

    public String getManaging_Editor() {
        return Managing_Editor;
    }

    public void setManaging_Editor(String managing_Editor) {
        Managing_Editor = managing_Editor;
    }

    public String getWeb_Master() {
        return Web_Master;
    }

    public void setWeb_Master(String web_Master) {
        Web_Master = web_Master;
    }

    public String getLast_Build_Date() {
        return Last_Build_Date;
    }

    public void setLast_Build_Date(String last_Build_Date) {
        Last_Build_Date = last_Build_Date;
    }

    public String getDocs() {
        return Docs;
    }

    public void setDocs(String docs) {
        Docs = docs;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public boolean isData_Present() {
        return Data_Present;
    }

    public void setData_Present(boolean data_Present) {
        Data_Present = data_Present;
    }

    public List<ChannelItem> getItems() {
        return Items;
    }

    public void addItem(ChannelItem item) {
        Items.add(item);
    }

    // END: Getters and Setters
}
