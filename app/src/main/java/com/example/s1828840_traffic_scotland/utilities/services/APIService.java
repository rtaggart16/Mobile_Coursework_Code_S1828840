/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.s1828840_traffic_scotland.utilities.models.enums.LocalTaskEnums;
import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.models.parser.routing.RoutingResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IAPIService
 * Description: Interface that contains method definitions for fetching data
 */
interface IAPIService {
    /*
     * Name: Fetch_Channel_Data
     * Description: Method to fetch data from a specified TrafficScotland RSS feed
     * Params:
     *      - url: the URL of the RSS feed to fetch
     * Returns: Formatted Channel
     */
    Channel Fetch_Channel_Data(String url);

    /*
     * Name: Get_Route
     * Description: Method to fetch the result of a Google directions query for the passed in parameter
     * Params:
     *      - url: The pre-formatted URL that contains information such as start and end
     * Returns: Formatted RoutingResult
     */
    RoutingResult Get_Route(String url);

    /*
     * Name: Fetch_Local_Data
     * Description: Method to fetch local data from the assets folder when no RSS data is available
     * Params:
     *      - choice: Enum specifying the type of data that should be fetched
     * Returns: Formatted Channel
     */
    Channel Parse_Local_Data(LocalTaskEnums choice);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: APIService
 * Description: Class that contains method bodies for fetching data
 */
public class APIService implements IAPIService {
    // Globals

    // Services

    // _parseService - Service for handling the parsing of Streams and Strings
    private ParseService _parseService = new ParseService();

    // END: Services

    // Variables

    // _context - Context of the application when the service is instantiated
    private Context _context;

    // END: Variables

    // END: Globals

    // Constructors

    // Main constructor used to initialise private variables
    public APIService(Context cont) {
        _context = cont;
    }

    // END: Constructors

    /*
     * Name: Get_Route
     * Description: Method to fetch the result of a Google directions query for the passed in parameter
     * Params:
     *      - url: The pre-formatted URL that contains information such as start and end
     * Returns: Formatted RoutingResult
    */
    public RoutingResult Get_Route (String url) {
        // Variables to store the formatted URL and the connection to the service
        URL formattedUrl;
        URLConnection dirConnection;

        // Instantiate a new routing result
        RoutingResult rResult = new RoutingResult();

        // Wrap in try-catch to avoid any parsing errors
        try{
            // Format the url and open te connection
            formattedUrl = new URL(url);
            dirConnection = formattedUrl.openConnection();

            // Parse the InputStream returned
            String result = _parseService.Convert_Stream_To_String(dirConnection.getInputStream());

            // Create a new instance of the Gson library
            Gson gson = new Gson();

            // Specify that the result should be parsed as a RoutingResult class
            Type type = new TypeToken<RoutingResult>() {}.getType();

            // Parse the result string
            rResult = gson.fromJson(result, type);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }

        // Return the result
        return  rResult;
    }

    /*
     * Name: Fetch_Channel_Data
     * Description: Method to fetch data from a specified TrafficScotland RSS feed
     * Params:
     *      - url: the URL of the RSS feed to fetch
     * Returns: Formatted Channel
    */
    public Channel Fetch_Channel_Data(String url) {
        // Variables to store the formatted URL and the connection to the service
        URL formattedUrl;
        URLConnection channelConnection;

        // Instantiate new Channel
        Channel channel = new Channel();

        // Wrap in try-catch to ensure any errors when parsing are caught
        try
        {
            // Populate the formattedUrl and open the connection
            formattedUrl = new URL(url);
            channelConnection = formattedUrl.openConnection();

            // Parse the result from the RSS feed into a string
            String result = _parseService.Convert_Stream_To_String(channelConnection.getInputStream());

            // Parse the result string into a channel
            channel = _parseService.Convert_String_To_Channel(result);
        }
        catch (IOException ae)
        {
            Log.e("MyTag", "ioexception");
        }

        // Return the channel
        return channel;
    }

    /*
     * Name: Fetch_Local_Data
     * Description: Method to fetch local data from the assets folder when no RSS data is available
     * Params:
     *      - choice: Enum specifying the type of data that should be fetched
     * Returns: Formatted Channel
     */
    public Channel Parse_Local_Data(LocalTaskEnums choice) {
        String fileLocation = "";

        // Instantiate a new channel
        Channel channel= new Channel();

        // Check the choice option passed in
        switch(choice) {
            // Choice is current incidents
            case Current_Incidents:
                // Set the fileLocation to the currentincidents.xml file in the assets folder
                fileLocation = "currentincidents.xml";
                break;
        }

        // Fetch all assets
        AssetManager assetManager = _context.getAssets();

        try {
            // Parse the content of the processed file
            String result = _parseService.Convert_Stream_To_String(assetManager.open(fileLocation));

            // Format the result string as a channel
            channel = _parseService.Convert_String_To_Channel(result);
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        return channel;
    }
}
