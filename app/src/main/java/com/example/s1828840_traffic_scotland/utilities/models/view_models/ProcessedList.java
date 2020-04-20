/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.models.view_models;

// Imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ListHeader
 * Description: Class that is store processed list headers and sub-items for expandable lists
 */
public class ProcessedList {
    // Private Variables

    // Headers - List of listHeaders used to populate the top-level items of an expandablelist
    private List<ListHeader> Headers;

    // Sub_Items - HashMap of key and value. Key is the name of the ListHeader the subitems belong to. The value is the list of subitems themselves
    private HashMap<String, List<String>> Sub_Items;

    // END: Private Variables

    // Constructors

    public ProcessedList() {
        Headers = new ArrayList<ListHeader>();
        Sub_Items = new HashMap<String, List<String>>();
    }

    // END: Constructors

    // Getters and Setters

    public List<ListHeader> getHeaders() {
        return Headers;
    }

    public void setHeaders(List<ListHeader> headers) {
        Headers = headers;
    }

    public void addHeader(ListHeader header) { Headers.add(header); }

    public HashMap<String, List<String>> getSub_Items() {
        return Sub_Items;
    }

    public void setSub_Items(HashMap<String, List<String>> sub_Items) {
        Sub_Items = sub_Items;
    }

    public void addSubItem(String title, List<String> subItems) { Sub_Items.put(title, subItems); }

    // END: Getters and Setters
}
