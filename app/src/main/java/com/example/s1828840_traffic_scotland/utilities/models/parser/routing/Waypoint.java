/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

// Imports

import java.util.ArrayList;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Waypoint
 * Description: Class that contains information about the start and end locations of Google route
 */
public class Waypoint {
    // Private Variables

    // geocoder_status - Status of the geocoder
    private String geocoder_status;

    // place_id - The ID of the place
    private String place_id;

    // types - List of tags that identify the waypoint
    private ArrayList<String> types;

    // END: Private Variables

    // Constructors

    public Waypoint()
    {
        types = new ArrayList<String>();
    }

    // END: Constructors

    // Getters and Setters

    public String getGeocoder_status() {
        return geocoder_status;
    }

    public void setGeocoder_status(String geocoder_status) {
        this.geocoder_status = geocoder_status;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    // END: Getters and Setters
}
