/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.extensions;

// Imports

import android.content.Context;
import android.os.AsyncTask;

import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.services.APIService;
import com.example.s1828840_traffic_scotland.utilities.services.ViewService;
import java.util.Date;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: RoutingTask
 * Description: Class that handles asynchronous fetching of routing data
 */
public class RoutingTask extends AsyncTask<String, Void, Object>
{
    // Extension Options

    public interface AsyncResponse {
        void processFinish(Channel output);
    }

    public Task.AsyncResponse delegate = null;

    public RoutingTask(Task.AsyncResponse delegate){
        this.delegate = delegate;
    }

    // END: Extension Options

    // Private Variables

    // _filterDate - Date object to filter by
    private Date _filterDate = null;

    // _viewService - Service to manipulate fragments
    private ViewService _viewService;

    // _apiService - Service to make API requests
    private APIService _apiService;

    // END: Private Variables

    // Constructors

    // Main constructor to populate the private variables
    public RoutingTask(Date date, Context cont) {
        _filterDate = date;
        _viewService = new ViewService(cont);
        _apiService = new APIService(cont);
    }

    // END: Constructors

    // AsyncTask overwritten methods

    @Override
    protected Object doInBackground(String... url) {
        // Begin the route fetch
        Object data = _apiService.Get_Route(url[0]);

        // Return the result
        return data;
    }

    @Override
    protected void onPostExecute(Object result) {
        // Pass the parsed route to the ViewService
        _viewService.Populate_Journey_Planner_Results(result, _filterDate);
    }

    // END: AsyncTask overwritten methods
}