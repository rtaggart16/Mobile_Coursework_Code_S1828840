/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Bound
 * Description: Class that contains the bound information of a Google route
 */
public class Bound {
    // Private Variables

    // northeast - Co-ordinates of the northeast bound
    private LatLon northeast;

    // southwest - Co-ordinates of the southwest bound
    private LatLon southwest;

    // END: Private Variables

    // Getters and Setters

    public LatLon getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLon northeast) {
        this.northeast = northeast;
    }

    public LatLon getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLon southwest) {
        this.southwest = southwest;
    }

    // END: Getters and Setters
}
