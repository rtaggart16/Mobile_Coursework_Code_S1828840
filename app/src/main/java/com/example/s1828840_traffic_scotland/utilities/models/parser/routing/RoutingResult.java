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
 * Class: RoutingResult
 * Description: Class that contains all information about a Google journey
 */
public class RoutingResult {
    // Private Variables

    // geocoded_waypoints - List of waypoints that make up routes e.g. start and end
    private ArrayList<Waypoint> geocoded_waypoints;

    // routes - List of individual routes for the journey
    private ArrayList<Route> routes;

    // status - The status of the response
    private String status;

    // END: Private Variables

    // Getters and Setters

    public ArrayList<Waypoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(ArrayList<Waypoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // END: Getters and Setters
}
