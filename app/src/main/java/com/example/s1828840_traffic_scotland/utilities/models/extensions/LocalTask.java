package com.example.s1828840_traffic_scotland.utilities.models.extensions;

// Imports

import android.content.Context;
import android.os.AsyncTask;

import com.example.s1828840_traffic_scotland.utilities.models.enums.LocalTaskEnums;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.services.APIService;
import com.example.s1828840_traffic_scotland.utilities.services.ViewService;

import java.util.Date;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: LocalTask
 * Description: Class that handles asynchronous fetching of local data
 */
public class LocalTask extends AsyncTask<LocalTaskEnums, Void, Channel>
{
    // Extension Options

    public interface AsyncResponse {
        void processFinish(Channel output);
    }

    public Task.AsyncResponse delegate = null;

    public LocalTask(Task.AsyncResponse delegate){
        this.delegate = delegate;
    }

    // END: Extension Options

    // Private Variables

    // _choice - Enum option specifying the option to perform after the LocalTask is complete
    private ViewServiceEnums _choice;

    // _filterDate - Date object to filter by
    private Date _filterDate;

    // _roadFilter - String road to filter by
    private String _roadFilter;

    // _context - Context of the application when calling the LocalTask
    private Context _context;

    // _viewService - Service that manipulates fragments
    private ViewService _viewService;

    // _apiService - Service that makes API requests
    private APIService _apiService;

    // END: Private Variables

    // Constructors

    // Main constructor used to populate private variables
    public LocalTask(Context cont, ViewServiceEnums choice_param, Date filterDate_param, String roadFilter_param) {
        _context = cont;
        _choice = choice_param;
        _filterDate = filterDate_param;
        _roadFilter = roadFilter_param;
        _apiService = new APIService(_context);
        _viewService = new ViewService(_context);
    }

    // END: Constructors

    // AsyncTask overwritten methods

    @Override
    protected Channel doInBackground(LocalTaskEnums... choice) {
        // Begin the local fetch
        Channel channel = _apiService.Parse_Local_Data(choice[0]);

        // Return the result
        return channel;
    }

    @Override
    protected void onPostExecute(Channel result) {
        // Pass options to the ViewService to manipulate the appropriate fragment
        _viewService.Handle_View_Service(_choice, result, _filterDate, _roadFilter);
    }

    // END: AsyncTask overwritten methods
}