/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Step
 * Description: Class that contains information on individual steps of a route
 */
public class Step {
    // Private Variables

    // distance - The distance of the current step in Kilometres
    private TextValue distance;

    // duration - The duration of the step in minutes
    private TextValue duration;

    // end_location - The co-ordinates of the end of the step
    private LatLon end_location;

    // html_instructions - HTML formatted instructions for the step
    private String html_instructions;

    // maneuver - The string representation of the maneuver required to complete the step
    private String maneuver;

    // polyline - Polyline that represents the current step
    private Polyline polyline;

    // start_location - The co-ordinates of the start of the step
    private LatLon start_location;

    // travel_mode - String representation of the mode of travel (is always set to driving)
    private String travel_mode;

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

    public LatLon getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LatLon end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public LatLon getStart_location() {
        return start_location;
    }

    public void setStart_location(LatLon start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    // END: Getters and Setters
}
