/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.extensions;

// Imports

import android.content.Context;
import android.os.AsyncTask;

import com.example.s1828840_traffic_scotland.utilities.models.enums.LocalTaskEnums;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.services.APIService;
import com.example.s1828840_traffic_scotland.utilities.services.TaskService;
import com.example.s1828840_traffic_scotland.utilities.services.ViewService;

import java.util.Date;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: Task
 * Description: Class that handles asynchronous fetching of TrafficScotland data
 */
public class Task extends AsyncTask<String, Void, Channel>
{
    // Extension Options

    public interface AsyncResponse {
        void processFinish(Channel output);
    }

    public AsyncResponse delegate = null;

    public Task(AsyncResponse delegate){
        this.delegate = delegate;
    }

    // END: Extension Options

    // Private Variables

    // _choice - The option to manipulate fragments
    private ViewServiceEnums _choice;

    // _filterDate - The Date object to filter by
    private Date _filterDate;

    // _roadFilter - The string road to filter by
    private String _roadFilter;

    // _context - The context of the application when calling the Task
    private Context _context;

    // _url - URL of the TrafficScotland RSS feed
    private String _url;

    // _viewService - Service to manipulate fragments
    private ViewService _viewService;

    // _apiService - Service to make API requests
    private APIService _apiService;

    // _taskService - Service used to perform asynchronous operations
    private TaskService _taskService;

    // END: Private Variables

    // Constructors

    // Main constructor used to populate the private variables
    public Task(Context cont, ViewServiceEnums choice_param, Date filterDate_param, String roadFilter_param) {
        _context = cont;
        _choice = choice_param;
        _filterDate = filterDate_param;
        _roadFilter = roadFilter_param;
        _apiService = new APIService(_context);
        _viewService = new ViewService(_context);
        _taskService = new TaskService(_context);
    }

    // END: Constructors

    // AsyncTask overwritten methods

    @Override
    protected Channel doInBackground(String... url) {
        // Extract the target URL from the parameter
        _url = url[0];

        // Begin the RSS feed fetch
        Channel channel = _apiService.Fetch_Channel_Data(url[0]);

        return channel;
    }

    @Override
    protected void onPostExecute(Channel result) {

        // If data was fetched
        if(result.isData_Present())
        {
            // Pass the private variables to the ViewService to manipulate the correct fragment
            _viewService.Handle_View_Service(_choice, result, _filterDate, _roadFilter);
        }
        // No data was fetched
        else
        {
            // Create a variable to specify the type of local data to be fetched
            LocalTaskEnums localChoice = null;

            // Check the URL passed in
            switch (_url) {
                // Current Inicidents
                case "https://trafficscotland.org/rss/feeds/currentincidents.aspx":
                    localChoice = LocalTaskEnums.Current_Incidents;
                    break;
            }

            // Begin a local fetch
            _taskService.Get_Local_Data(localChoice, _choice, _filterDate, _roadFilter);
        }
    }

    // END: AsyncTask overwritten methods
}
