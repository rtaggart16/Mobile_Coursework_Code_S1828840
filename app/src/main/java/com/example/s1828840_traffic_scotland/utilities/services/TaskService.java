/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.content.Context;
import android.util.Log;

import com.example.s1828840_traffic_scotland.utilities.models.enums.LocalTaskEnums;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.LocalTask;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.RoutingTask;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.Task;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ITaskService
 * Description: Interface that contains method definitions for processing RSS feed fetches asynchronously
 */
interface ITaskService {
    /*
     * Name: Get_Current_Incidents
     * Description: Method to asynchronously fetch for current incidents from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
     */
    void Get_Current_Incidents(Date filterDate, ViewServiceEnums choice, String roadFilter);

    /*
     * Name: Get_Current_Roadworks
     * Description: Method to asynchronously fetch for current roadworks from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
     */
    void Get_Current_Roadworks(Date filterDate, ViewServiceEnums choice, String roadFilter);

    /*
     * Name: Get_Planned_Roadworks
     * Description: Method to asynchronously fetch for planned roadworks from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
     */
    void Get_Planned_Roadworks(Date filterDate, ViewServiceEnums choice, String roadFilter);

    /*
     * Name: Get_Directions
     * Description: Method to asynchronously fetch a journey
     * Params:
     *      - origin: Co-ordinates of the start of the journey
     *      - destination: Co-ordinates of the end of th journey
     *      - filterDate: Date of the journey
     * Returns: None
     */
    void Get_Directions(LatLng origin, LatLng destination, Date filterDate);

    /*
     * Name: Get_Local_Data
     * Description: Method to asynchronously fetch a local data from the assets folder
     * Params:
     *      - localTaskType: The type of local data to fetch
     *      - choice: Enum that specified the function to perform after processing
     *      - filterDate: Optional date for filtering
     *      - roadFilter: Optional road to filter by
     * Returns: None
     */
    void Get_Local_Data(LocalTaskEnums localTaskType, ViewServiceEnums choice, Date filterDate, String roadFilter);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: TaskService
 * Description: Class that contains method bodies for processing RSS feed fetches asynchronously
 */
public class TaskService implements ITaskService {
    // Private Variables

    // _context - The context of the application when the service is instantiated
    private Context _context;

    // END: Private Variables

    public TaskService(Context cont) {
        _context = cont;
    }

    /*
     * Name: Get_Current_Incidents
     * Description: Method to asynchronously fetch for current incidents from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
    */
    public void Get_Current_Incidents(Date filterDate, ViewServiceEnums choice, String roadFilter)  {
        // Specify the target URL as the current incidents RSS feed
        String url = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

        // Create a new Task with the options passed in
        Task task = new Task(_context, choice, filterDate, roadFilter);

        try
        {
            // Begin execution
            task.execute(new String[] { url });
        }
        catch(Exception ex) {
            Log.e("Error", ex.getMessage());
        }
    }

    /*
     * Name: Get_Current_Roadworks
     * Description: Method to asynchronously fetch for current roadworks from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
     */
    public void Get_Current_Roadworks(Date filterDate, ViewServiceEnums choice, String roadFilter) {
        // Specify the target URL as the current roadworks RSS feed
        String url = "https://trafficscotland.org/rss/feeds/roadworks.aspx";

        // Create a new Task with the options passed in
        Task task = new Task(_context, choice, filterDate, roadFilter);

        try
        {
            // Begin execution
            task.execute(new String[] { url });
        }
        catch(Exception ex) {
            Log.e("test", ex.getMessage());
        }
    }

    /*
     * Name: Get_Planned_Roadworks
     * Description: Method to asynchronously fetch for planned roadworks from the RSS feeds
     * Params:
     *      - filterDate: Optional date to filter results by
     *      - choice: Enum which refers to a function to perform after the process is complete
     *      - roadFilter: Optional road to filter results by
     * Returns: None
     */
    public void Get_Planned_Roadworks(Date filterDate, ViewServiceEnums choice, String roadFilter) {
        // Specify the target URL as the current roadworks RSS feed
        String url = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

        // Create a new Task with the options passed in
        Task task = new Task(_context, choice, filterDate, roadFilter);

        try
        {
            // Begin Task execution
            task.execute(new String[] { url });
        }
        catch(Exception ex) {
            Log.e("test", ex.getMessage());
        }
    }

    /*
     * Name: Get_Directions
     * Description: Method to asynchronously fetch a journey
     * Params:
     *      - origin: Co-ordinates of the start of the journey
     *      - destination: Co-ordinates of the end of th journey
     *      - filterDate: Date of the journey
     * Returns: None
     */
    public void Get_Directions(LatLng origin, LatLng destination, Date filterDate) {
        // Transform the origin and destination passed in as strings
        String originString = origin.latitude + "," + origin.longitude;
        String destinationString = destination.latitude + "," + destination.longitude;

        // Add the string co-ordinates to the URL, including API key
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + originString + "&destination=" + destinationString + "&key=AIzaSyBsLqT5OPZ3wAfwmZjUdy32zDZKmKCb0nw";

        // Create a new routing task
        RoutingTask rTask = new RoutingTask(filterDate, _context);

        try
        {
            // Begin execution
            rTask.execute(new String[] { url });
        }
        catch(Exception ex) {
            Log.e("test", ex.getMessage());
        }
    }

    /*
     * Name: Get_Local_Data
     * Description: Method to asynchronously fetch a local data from the assets folder
     * Params:
     *      - localTaskType: The type of local data to fetch
     *      - choice: Enum that specified the function to perform after processing
     *      - filterDate: Optional date for filtering
     *      - roadFilter: Optional road to filter by
     * Returns: None
     */
    public void Get_Local_Data(LocalTaskEnums localTaskType, ViewServiceEnums choice, Date filterDate, String roadFilter) {
        // Create a new LocalTask using the options passed in
        LocalTask lTask = new LocalTask(_context, choice, filterDate, roadFilter);

        try {
            // Begin Task execution
            lTask.execute(localTaskType);
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
    }
}
