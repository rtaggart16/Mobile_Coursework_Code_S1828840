/*
 * Name: Ross Taggart
 * ID: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.parser;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ItemPoint
 * Description: Class that contains co-ordinate information for a ChannelItem
 */
public class ItemPoint {
    // Private Variables

    // Prefix - Prefix of the co-ordinates
    private String Prefix;

    // Lat - The latitude of the point
    private double Lat;

    // Lon - the longitude of the point
    private double Lon;

    // END: Private Variables

    // Constructors

    public ItemPoint() {
    }

    // END: Constructors

    // Getters and Setters

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    // END: Getters and Setters
}
