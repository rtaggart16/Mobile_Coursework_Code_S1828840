/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.parser.routing;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: TextValue
 * Description: Class that contains a text key and a double value. Used to store the user-friendly and numerical values of the distance and duration of a Google route
 */
public class TextValue {
    // Private Variables

    // text - The key of the item
    private String text;

    // value - The value of the item
    private Double value;

    // END: Variables

    // Getter and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    // END: Getters and Setters
}
