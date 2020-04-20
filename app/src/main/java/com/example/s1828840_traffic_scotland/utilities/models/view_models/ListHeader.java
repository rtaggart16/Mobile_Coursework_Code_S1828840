/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.view_models;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ListHeader
 * Description: Class that is used for headers of expandablelists. Allows list headers to be colour-coded
 */
public class ListHeader {
    // Private Variables

    // Title - The title of the list header
    private String Title;

    // Colour - The desired colour of the list header
    private Integer Colour;

    // END: Private Variables

    // Constructors

    public ListHeader(String title, Integer colour) {
        Title = title;
        Colour = colour;
    }

    public ListHeader() {}

    // END: Constructors

    // Getters and Setters

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getColour() {
        return Colour;
    }

    public void setColour(Integer colour) {
        Colour = colour;
    }

    // END: Getters and Setters
}
