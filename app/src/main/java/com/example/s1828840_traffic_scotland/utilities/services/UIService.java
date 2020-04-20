/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.view.View;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IUIService
 * Description: Interface that contains method definitions for manipulating UI elements
 */
interface IUIService {
    /*
     * Name: Toggle_View
     * Description: Method to toggle the current view invisible and another visible
     * Params:
     *      - visible: The view that is currently visible
     *      - invisible: The view that is currently invisible
     */
    void Toggle_View(View visible, View invisible);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: UIService
 * Description: Interface that contains method bodies for manipulating UI elements
 */
public class UIService implements IUIService {
    /*
     * Name: Toggle_View
     * Description: Method to toggle the current view invisible and another visible
     * Params:
     *      - visible: The view that is currently visible
     *      - invisible: The view that is currently invisible
    */
    public void Toggle_View(View visible, View invisible) {
        // If neither of the parameters are null
        if(visible != null && invisible != null) {
            // Hide the visible view and display the invisible view
            visible.setVisibility(View.INVISIBLE);
            invisible.setVisibility(View.VISIBLE);
        }
    }
}
