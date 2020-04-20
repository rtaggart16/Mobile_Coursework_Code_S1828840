/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Leg
 * Description: Class that contains the information for a leg of a Google route
 */
public class Leg {
    // Private Variables

    // distance - The distance of the journey in Kilometres
    private TextValue distance;

    // duration - The time taken for the journey in minutes
    private TextValue duration;

    // end_address - The string representation of the end of the journey
    private String end_address;

    // end_location - The co-ordinates of the end of the journey
    private LatLon end_location;

    // start_address - The string representation of the start of the journey
    private String start_address;

    // start_location - The co-ordinates of the start of the journey
    private LatLon start_location;

    // END: Private Variables

    // Getters and Setters

    public TextValue getDistance() {
        return distance;
    }

    public void setDistance(TextValue distance) {
        this.distance = distance;
    }

    public TextValue getDuration() {
        return duration;
    }

    public void setDuration(TextValue duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public LatLon getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LatLon end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public LatLon getStart_location() {
        return start_location;
    }

    public void setStart_location(LatLon start_location) {
        this.start_location = start_location;
    }

    // Getters and Setters
}
