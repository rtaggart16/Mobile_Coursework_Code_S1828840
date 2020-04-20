/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.app.Activity;
import android.widget.Toast;

// Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IToastService
 * Description: Interface that contains method definitions for displaying toasts
 */
interface IToastService {
    /*
     * Name: Display_Message
     * Description: Method to display a toast which displays a message passe in
     * Params:
     *      - msg: Te message to display
     * Returns: None
     */
    void Display_Message(String msg);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IToastService
 * Description: Interface that contains method bodies for displaying toasts
 */
public class ToastService implements IToastService {
    // Private Variables

    // _act - The activity of the item instantiating the service
    private Activity _act;

    // END: Private Variables

    public ToastService(Activity act) {
        _act = act;
    }

    /*
     * Name: Display_Message
     * Description: Method to display a toast which displays a message passe in
     * Params:
     *      - msg: Te message to display
     * Returns: None
    */
    public void Display_Message(String msg) {
        // Display the message
        Toast.makeText(_act, msg, Toast.LENGTH_LONG).show();
    }
}
