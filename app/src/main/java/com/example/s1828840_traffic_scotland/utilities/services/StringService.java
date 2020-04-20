/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

import android.util.Log;

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IStringService
 * Description: Interface that contains method definitions for manipulating strings
 */
interface IStringService {
    String Extract_Key(String base, String key, String sep);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: StringService
 * Description: Interface that contains method bodies for manipulating dates
 */
public class StringService implements IStringService {

    /*
     * Name: Extract_Key
     * Description: Method to extract portions of text from a string
     * Params:
     *      - base: The full string to check against
     *      - key: The string to search for such as "Start Date"
     *      - sep: Separator
    */
    public String Extract_Key(String base, String key, String sep)
    {
        // Variable used to contain the extracted string
        String extract = "";

        // Wrap in try-catch to avoid crashes when nothin gis found
        try
        {
            // Get the index ofthe key from within the base string, if it exists
            int keyOccurrence = base.indexOf(key);

            // If the index is not -1 (not found)
            if(keyOccurrence != -1)
            {
                // Create a substring of the base from the occurrence of the key
                String partialSub = base.substring(keyOccurrence);

                // If the partial substring contains the separation string
                if(partialSub.contains(sep))
                {
                    // Perform another substring from the partialsub to the next occurrence of the separator
                    extract = partialSub.substring(partialSub.indexOf(':') + 1, partialSub.indexOf(sep));
                }
                // The partial substring does not contain the separator
                else
                {
                    extract = partialSub.substring(partialSub.indexOf(':') + 1);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }

        // Return the extracted string
        return extract;
    }
}
