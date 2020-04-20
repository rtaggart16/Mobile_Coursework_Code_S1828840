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
 * Class: Route
 * Description: Class that contains all information regarding a particular route or a Google journey
 */
public class Route {
    // Private Variables

    // bounds - The northeast and southwest bounds of a route
    private Bound bounds;

    // copyrights - Google copyright
    private String copyrights;

    // legs - List of the legs involved in the route
    private ArrayList<Leg> legs;

    // overview_polyline - The polyline which plots the start -> end of the route
    private Polyline overview_polyline;

    // summary - A summary of the route
    private String summary;

    // END: Private Variables

    // Getters and Setters

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public Polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // END: Getters and Setters
}
