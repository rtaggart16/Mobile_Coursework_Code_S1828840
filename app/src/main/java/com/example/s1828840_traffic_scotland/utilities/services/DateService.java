/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IDateService
 * Description: Interface that contains method definitions for handling dates
 */
interface IDateService {
    /*
     * Name: Parse_From_String
     * Description: Method to parse a string into a Date object
     * Params:
     *      - stringDate: The string representation of a potential date
     * Returns: A parsed Date object/null
     */
    Date Parse_From_String(String stringDate);

    /*
     * Name: Get_Today
     * Description: Shorthand method to fetch the current date
     * Params: None
     * Returns:
     *      - The current date
     */
    Date Get_Today();
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IDateService
 * Description: Interface that contains method bodies for handling dates
 */
public class DateService implements IDateService {
    // Public Methods

    /*
     * Name: Parse_From_String
     * Description: Method to parse a string into a Date object
     * Params:
     *      - stringDate: The string representation of a potential date
     * Returns: A parsed Date object/null
    */
    public Date Parse_From_String(String stringDate) {
        // Fetch the system wide parsing format
        DateFormat format = Get_Format();

        // Initialise the parsedDate as null
        Date parsedDate = null;

        // Wrap in try-catch to ensure any parse errors are caught
        try
        {
            parsedDate = format.parse(stringDate.trim());

            Calendar c = Calendar.getInstance();

            // Set the time of the parsed date to exactly midnight to avoid any comparison problems
            c.setTime(parsedDate);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            // Populate parsedDate with the value of the Calendar object
            parsedDate = c.getTime();
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        // Return the parsedDate
        return parsedDate;
    }

    /*
     * Name: Get_Today
     * Description: Shorthand method to fetch the current date
     * Params: None
     * Returns:
     *      - The current date
    */
    public Date Get_Today() {
        // Get a calendar instance
        Calendar c = Calendar.getInstance();

        // Set the tie of the Date to exactly midnight
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // Return today
        return c.getTime();
    }

    // END: Public Methods


    // Private Methods

    /*
     * Name: Get_Format
     * Description: Method to return the system-wide date format
     * Params: None
     * Returns: DateFormat
     */
    private DateFormat Get_Format() {
        DateFormat format = null;

        try
        {
            // Populate the DateFormat
            format = new SimpleDateFormat("E, d MMMM yyyy");
        }
        catch (Exception ex)
        {
            String Test = ex.getMessage();
        }

        // Return the format
        return format;
    }

    // END: Private Methods
}
