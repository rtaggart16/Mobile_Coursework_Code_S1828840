/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: LatLon
 * Description: Class that contains the co-ordinates of a bound for a GoogleMap route
 */
public class LatLon {
    // Private Variables

    // lat - The latitude of the item
    private Double lat;

    // lng - The longitude of the item
    private Double lng;

    // END: Private Variables

    // Getters and Setters

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLon(Double lng) {
        this.lng = lng;
    }

    // END: Getters and Setters
}
