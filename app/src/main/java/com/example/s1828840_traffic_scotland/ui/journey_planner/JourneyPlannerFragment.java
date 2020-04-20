/*
 * Name: Ross Taggart
 * ID: S1828840
 */

package com.example.s1828840_traffic_scotland.ui.journey_planner;

// Imports

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.s1828840_traffic_scotland.R;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.ExpListAdapter;
import com.example.s1828840_traffic_scotland.utilities.models.parser.ChannelItem;
import com.example.s1828840_traffic_scotland.utilities.models.parser.routing.Route;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ListHeader;
import com.example.s1828840_traffic_scotland.utilities.services.TaskService;
import com.example.s1828840_traffic_scotland.utilities.services.ToastService;
import com.example.s1828840_traffic_scotland.utilities.services.UIService;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: JourneyPlannerFragment
 * Description: Class to handle displaying the journey planner view
 */
public class JourneyPlannerFragment extends Fragment implements OnMapReadyCallback {

    // Globals

    // Services

    // _toastService - Service used to simplify the process of displaying a toast
    private static ToastService _toastService;

    // _taskService - Service used to perform asynchronous operations
    private static TaskService _taskService;

    // _uiService - Service used to perform basic UI operations
    private static UIService _uiService = new UIService();

    // END: Services

    // Variables

    // UI

    // _resultView - View that contains the list and map of the planned journey
    private static View _resultView;

    // _progressView - View that contains the progress spinner
    private static View _progressView;

    // _queryView - View that contains the query options
    private static View _queryView = null;

    // _mapDisplayView - View that contains the results of the route
    private static View _mapDisplayView = null;

    // _root - Reference to this current view
    private static View _root;

    // _activity - Reference to the activity this view belongs to (MainActivity)
    private static Activity _activity;

    // Google

    // _journeyPlannerMap - Reference to te actual GoogleMap
    private static GoogleMap _journeyPlannerMap = null;

    // _journeyPlannerMapView - Reference to the GoogleMapView
    private static MapView _journeyPlannerMapView;

    // END: Google

    // Flags

    // _journeyDetailsProc - Flag that signifies if the journey details of the route have been processed
    public static boolean _journeyDetailsProc = false;

    // _incidentsProc - Flag that signifies if the current incidents of the route have been processed
    public static boolean _incidentsProc = false;

    // _currentRoadworksProc - Flag that signifies if the current roadworks of the route have been processed
    public static boolean _currentRoadworksProc = false;

    // _plannedRoadworksProc - Flag that signifies if the planned roadworks of the route have been processed
    public static boolean _plannedRoadworksProc = false;

    // _mapLoaded - Flag that signifies if the map has been fully processed
    private static boolean _mapProcessed = false;

    // _listLoaded - Flag that signifies if the list has been fully processed
    private static boolean _listProcessed = false;

    // END: Flags

    // END: UI

    // General

    // _listHeaders - List of headers for expandable list
    private static List<ListHeader> _listHeaders = new LinkedList<ListHeader>();

    // _listItems - HashMap of sub-list items
    private static HashMap<String, List<String>> _listItems = new HashMap<String, List<String>>();

    // _start - The latitude and longitude of the route's start address
    private static LatLng _start = null;

    // _end - The latitude and longitude of the route's end address
    private static LatLng _end = null;

    // _travelDate - The Date of the route
    private static Date _travelDate = null;

    // _mapBounds - The bounds of the route map
    public static LatLngBounds _mapBounds = null;

    // _placesClient - Client for Google Autocomplete
    PlacesClient _placesClient = null;

    // END: General

    // END: Variables

    // END: Globals


    // Methods

    // Public Methods

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Display the current view
        View root = inflater.inflate(R.layout.fragment_journey_planner, container, false);

        // Call this view's top-level initialisation method
        Initialise(root);

        return root;
    }

    /*
     * Name: Set_Travel_Date
     * Description: Method to assign the global _travelDate
     * Params:
     *      - tDate: The date object to be used to assign the _travelDate
     */
    public static void Set_Travel_Date(Date tDate) {
        // Assign tDate to _travelDate
        _travelDate = tDate;
    }

    /*
     * Name: Toggle_View
     * Description: Quick shorthand method for toggling between the query and result views
     */
    public static void Toggle_View() {
        // Toggle the views
        _uiService.Toggle_View(_queryView, _mapDisplayView);
    }

    /*
     * Name: Show_Roadworks
     * Description: Method to fade-out the progress view and display the result
     * Params: None
     * Returns: None
     */
    public static void Show_Result() {
        if (_mapProcessed && _listProcessed) {
            _uiService.Toggle_View(_progressView, _resultView);
        }
    }

    /*
     * Name: Populate_Journey_Info
     * Description: Method to populate the map and list with details of the journey such as time and distance
     * Params:
     *      - route: The route object returned by the Google directions API
     * Returns: None
    */
    public static void Populate_Journey_Info(Route route) {
        // Add route details to the map
        Populate_Journey_Map(route);

        // Add route details to the list
        Populate_Journey_List(route);

        // Specify that journey details hae been processed
        _journeyDetailsProc = true;
    }

    /*
     * Name: Populate_List
     * Description: Method to populate the expandable list using the global variables
     * Params: None
     * Returns: None
     */
    public static void Populate_List() {
        // Check all sub items have been processed
        if (_journeyDetailsProc && _currentRoadworksProc && _incidentsProc && _plannedRoadworksProc) {

            // Create a new expandable list
            ExpandableListView expListView = (ExpandableListView) _root.findViewById(R.id.J_P_List_Result);

            // Create an adapter for the list
            ExpListAdapter listAdapter = new ExpListAdapter(_activity, _listHeaders, _listItems);

            // Setting list adapter
            expListView.setAdapter(listAdapter);

            // Specify that the map and list have been loaded and check if the view should be shown
            _listProcessed = true;
            _mapProcessed = true;
            Show_Result();
        }
    }

    /*
     * Name: Populate_Current_Incidents_Markers
     * Description: Method to add any current incidents identified on the route to the map
     * Params:
     *      - markers: The marker options for each incident
     * Returns: None
     */
    public static void Populate_Current_Incidents_Markers(ArrayList<MarkerOptions> markers) {
        // ArrayList to store all markers that are within the bounds
        ArrayList<MarkerOptions> procMarkers = new ArrayList<>();

        // Check if the map bounds have been specified
        if (_mapBounds != null) {

            // Foreach marker, check if the position is within the current map bounds i.e. could affect the route
            for (MarkerOptions opt : markers) {
                if (_mapBounds.contains(opt.getPosition())) {
                    procMarkers.add(opt);
                }
            }

            // If any markers are within bounds, add them to the map
            if (procMarkers.size() > 0) {
                for (MarkerOptions mOpt : procMarkers) {
                    _journeyPlannerMap.addMarker(mOpt);
                }
            }
        }
    }

    /*
     * Name: Populate_Current_Incidents_List
     * Description: Method to add any encountered incidents to the result list
     * Params:
     *      - items: List of incidents found on the route
     * Returns: None
    */
    public static void Populate_Current_Incidents_List(List<ChannelItem> items) {
        // Check the map bounds are not null
        if (_mapBounds != null) {

            // Create variables to track the header and subitems
            String headerText = "";
            List<String> subItems = new ArrayList<String>();

            // If no items are present
            if (items.size() == 0) {
                // Specify that there are no current incidents on the route
                headerText = "Current Incidents: 0";
                subItems.add("No Current Incidents on Route");
            }
            // There are items present
            else {
                // Create a counter to track the number of incidents encountered
                int itemCount = 0;

                // Foreach item, add to subitems and increase counter
                for (ChannelItem item : items) {
                    subItems.add(item.getTitle());
                    itemCount++;
                }

                // Ste the header text to include the number of incidents
                headerText = "Current Incidents: " + itemCount;
            }

            // Add the current incidents list items to the global values
            _listHeaders.add(new ListHeader(headerText, Color.BLACK));
            _listItems.put(headerText, subItems);

            // Signify current incidents have been processed and check if the view should be displayed
            _incidentsProc = true;
            Populate_List();
        }
    }

    /*
     * Name: Populate_Current_Roadworks_Markers
     * Description: Method to add any current roadworks identified on the route to the map
     * Params:
     *      - markers: The marker options for each roadwork
     * Returns: None
     */
    public static void Populate_Current_Roadworks_Markers(ArrayList<MarkerOptions> markers) {
        // ArrayList to store all markers that are within the bounds
        ArrayList<MarkerOptions> procMarkers = new ArrayList<>();

        // Check if the map bounds have been specified
        if (_mapBounds != null) {

            // Foreach marker, check if the position is within the current map bounds i.e. could affect the route
            for (MarkerOptions opt : markers) {
                if (_mapBounds.contains(opt.getPosition())) {
                    procMarkers.add(opt);
                }
            }

            // If any markers are within bounds, add them to the map
            if (procMarkers.size() > 0) {
                for (MarkerOptions mOpt : procMarkers) {
                    _journeyPlannerMap.addMarker(mOpt);
                }
            }
        }
    }

    /*
     * Name: Populate_Current_Roadworks_List
     * Description: Method to add any encountered roadworks to the result list
     * Params:
     *      - items: List of roadworks found on the route
     * Returns: None
     */
    public static void Populate_Current_Roadworks_List(List<ChannelItem> items) {
        // Check the map bounds are not null
        if (_mapBounds != null) {

            // Create variables to track the header and subitems
            String headerText = "";
            List<String> subItems = new ArrayList<String>();

            // If no items are present
            if (items.size() == 0) {
                // Specify that there are no current roadworks on the route
                headerText = "Current Roadworks: 0";
                subItems.add("No Current Roadworks on Route");
            }
            // There are items present
            else {
                // Create a counter to track the number of roadworks encountered
                int itemCount = 0;

                // Foreach item, add to subitems and increase counter
                for (ChannelItem item : items) {
                    subItems.add(item.getTitle());
                    itemCount++;
                }

                // Ste the header text to include the number of roadworks
                headerText = "Current Roadworks: " + itemCount;
            }

            // Add the current roadworks list items to the global values
            _listHeaders.add(new ListHeader(headerText, Color.BLACK));
            _listItems.put(headerText, subItems);

            // Signify current roadworks have been processed and check if the view should be displayed
            _currentRoadworksProc = true;
            Populate_List();
        }
    }

    /*
     * Name: Populate_Planned_Roadworks_Markers
     * Description: Method to add any planned roadworks identified on the route to the map
     * Params:
     *      - markers: The marker options for each roadwork
     * Returns: None
     */
    public static void Populate_Planned_Roadworks_Markers(ArrayList<MarkerOptions> markers) {
        // ArrayList to store all markers that are within the bounds
        ArrayList<MarkerOptions> procMarkers = new ArrayList<>();

        // Check if the map bounds have been specified
        if (_mapBounds != null) {

            // Foreach marker, check if the position is within the current map bounds i.e. could affect the route
            for (MarkerOptions opt : markers) {
                if (_mapBounds.contains(opt.getPosition())) {
                    procMarkers.add(opt);
                }
            }

            // If any markers are within bounds, add them to the map
            if (procMarkers.size() > 0) {
                for (MarkerOptions mOpt : procMarkers) {
                    _journeyPlannerMap.addMarker(mOpt);
                }
            }
        }
    }

    /*
     * Name: Populate_Planned_Roadworks_List
     * Description: Method to add any encountered roadworks to the result list
     * Params:
     *      - items: List of roadworks found on the route
     * Returns: None
     */
    public static void Populate_Planned_Roadworks_List(List<ChannelItem> items) {
        // Check the map bounds are not null
        if (_mapBounds != null) {

            // Create variables to track the header and subitems
            String headerText = "";
            List<String> subItems = new ArrayList<String>();

            // If no items are present
            if (items.size() == 0) {
                // Specify that there are no planned roadworks on the route
                headerText = "Planned Roadworks: 0";
                subItems.add("No Planned Roadworks on Route");
            }
            // There are items present
            else {
                // Create a counter to track the number of roadworks encountered
                int itemCount = 0;

                // Foreach item, add to subitems and increase counter
                for (ChannelItem item : items) {
                    subItems.add(item.getTitle());
                    itemCount++;
                }

                // Ste the header text to include the number of incidents
                headerText = "Planned Roadworks: " + itemCount;
            }

            // Add the planned roadworks list items to the global values
            _listHeaders.add(new ListHeader(headerText, Color.BLACK));
            _listItems.put(headerText, subItems);

            // Signify planned roadworks have been processed and check if the view should be displayed
            _plannedRoadworksProc = true;
            Populate_List();
        }
    }

    // Google Map Overwrites

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Populate the global GoogleMap
        _journeyPlannerMap = googleMap;

        // Move the camera to Scotland. This is temporary until the Bounds are changed to encase the route
        LatLng scotland = new LatLng(56.5340424, -4.3669823);
        _journeyPlannerMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scotland, 5));
    }

    @Override
    public void onResume() {
        _journeyPlannerMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        _journeyPlannerMapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        _journeyPlannerMapView.onLowMemory();
        super.onLowMemory();
    }

    // END: Google Map Overwrites

    // END: Public Methods

    // Private Methods

    /*
     * Name: Populate_Journey_Map
     * Description: Method to add details of the route to the map
     * Params:
     *      - route: The route item fetched from the Google directions API
     * Returns: None
     */
    private static void Populate_Journey_Map(Route route) {
        // Get the list of latitudes and longitudes given in the points of the route
        List<LatLng> latLngs = PolyUtil.decode(route.getOverview_polyline().getPoints());

        // Create a ne PolylineOptions
        PolylineOptions opt = new PolylineOptions();

        // Add all of the Lat/Lons to the polyline options
        opt.addAll(latLngs);

        // Add the polyline to the map
        _journeyPlannerMap.addPolyline(opt);

        // Create bounds builder to encase journey details
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Get bounds co-ordinates specified in the route
        LatLng latLon = new LatLng(route.getBounds().getNortheast().getLat(), route.getBounds().getNortheast().getLng());
        LatLng latLon2 = new LatLng(route.getBounds().getSouthwest().getLat(), route.getBounds().getSouthwest().getLng());

        // Add both co-ordinates set to the bounds
        builder.include(latLon);
        builder.include(latLon2);

        // Extract the co-ordinates of the EXACT start and end locations of the route
        LatLng start = new LatLng(route.getLegs().get(0).getStart_location().getLat(), route.getLegs().get(0).getStart_location().getLng());
        LatLng end = new LatLng(route.getLegs().get(0).getEnd_location().getLat(), route.getLegs().get(0).getEnd_location().getLng());

        // Add a marker for both the start and the end
        _journeyPlannerMap.addMarker(new MarkerOptions().position(start).title(route.getLegs().get(0).getStart_address()));
        _journeyPlannerMap.addMarker(new MarkerOptions().position(end).title(route.getLegs().get(0).getEnd_address()));

        // Build the new bounds
        LatLngBounds routeBounds = builder.build();

        // Store the new bounds globally
        _mapBounds = routeBounds;

        // Move the map camera to only display the new bounds
        _journeyPlannerMap.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 30));
    }

    /*
     * Name: Populate_Journey_List
     * Description: Method to add details of the route to the list
     * Params:
     *      - route: The route item fetched from the Google directions API
     * Returns: None
     */
    private static void Populate_Journey_List(Route route) {
        // Create a new list of sub-items
        List<String> subItems = new ArrayList<String>();

        // Add the start, end, distance and time taken to the sub items
        subItems.add("Start: " + route.getLegs().get(0).getStart_address());
        subItems.add("End: " + route.getLegs().get(0).getEnd_address());
        subItems.add("Distance: " + route.getLegs().get(0).getDistance().getText());
        subItems.add("Time: " + route.getLegs().get(0).getDuration().getText());

        // Add a new list header for journey details
        _listHeaders.add(new ListHeader("Journey Details", Color.BLACK));
        // Add the sub-items as children of the journey details
        _listItems.put("Journey Details", subItems);
    }

    /*
     * Name: Initialise
     * Description: Top-level initialisation method used to call sub-initialisation methods
     * Params:
     *      - root: The current View
     * Returns: None
     */
    private void Initialise(View root) {
        // Make sure global variables are processed properly
        Initialise_Variables(root);

        // Get the necessary UI elements and add any listeners
        Initialise_UI(root);

        // Initialise the GoogleMap
        Initialise_Map(root);
    }

    /*
     * Name: Initialise_Variables
     * Description: Initialise the top-level variables of this fragment. Mainly View related variables and services
     * Params:
     *      - root: The current View
     * Returns: None
     */
    private void Initialise_Variables(View root) {
        // Initialise this view's global variable
        _root = root;

        // Populate the activity variable
        _activity = this.getActivity();

        // Create a new instance of the TaskService
        _taskService = new TaskService(_root.getContext());

        // Create an instance of the ToastService
        _toastService = new ToastService(this.getActivity());

        // Clear the List items
        _listHeaders.clear();
        _listItems.clear();

        // Set all processing flags to false
        _journeyDetailsProc = false;
        _incidentsProc = false;
        _currentRoadworksProc = false;
        _plannedRoadworksProc = false;

        _mapProcessed = false;
        _listProcessed = false;

        // Nullify Travel options
        _start = null;
        _end = null;
        _travelDate = null;
    }

    /*
     * Name: Initialise_Map
     * Description: Method to initialise the GoogleMap
     * Params:
     *      - root: The current View
     * Returns: None
     */
    private void Initialise_Map(View root) {
        // Initialise the MapInitializer
        MapsInitializer.initialize(getContext());

        // Store the MapView in the global variable so that the Overwritten lifecycle methods can trigger
        _journeyPlannerMapView = root.findViewById(R.id.J_P_Map);
        _journeyPlannerMapView.onCreate(null);
        _journeyPlannerMapView.getMapAsync((OnMapReadyCallback) this);
    }

    /*
     * Name: Initialise_UI
     * Description: Method to initialise the UI elements of the view. Involves populating variables and adding listeners
     * Params:
     *      - root: The current View
     * Returns: None
     */
    private void Initialise_UI(View root) {
        // Get the progress spinner view
        _progressView = root.findViewById(R.id.J_P_Progress);
        // Get the result view
        _resultView = root.findViewById(R.id.J_P_Result_View);

        // Get the view that contains the query options
        _queryView = root.findViewById(R.id.J_P_Query_View);
        // Get the view that displays the result
        _mapDisplayView = root.findViewById(R.id.J_P_Map_View);

        // Initialize the places SDK
        // In production app, the API key should be hidden using proguard
        Places.initialize(_root.getContext(), "AIzaSyBsLqT5OPZ3wAfwmZjUdy32zDZKmKCb0nw");

        // Create a new Places client instance
        _placesClient = Places.createClient(this.getActivity());

        // Initialize the AutocompleteSupportFragment for start
        AutocompleteSupportFragment startAutoComplete = ((AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.start_autocomplete));

        // Specify the types of place data to return. This means only UK places will be suggested
        startAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)).setCountry("GB");
        startAutoComplete.setHint("Start");

        // Initialize the AutocompleteSupportFragment for end
        AutocompleteSupportFragment endAutoComplete = ((AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.end_autocomplete));

        // Specify the types of place data to return. This means only UK places will be suggested
        endAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)).setCountry("GB");
        endAutoComplete.setHint("End");

        // Get the button that submits the options
        Button submitButton = (Button) root.findViewById(R.id.J_P_Submit_Btn);

        // Add an onclick to the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If all of the options are specified
                if (_start != null && _end != null && _travelDate != null) {
                    // Begin the process of fetching the route and show the loader
                    _taskService.Get_Directions(_start, _end, _travelDate);
                    Toggle_View();
                }
                // Some options are not specified
                else {
                    // Handle displaying errors
                    String errorMsg = "";

                    // If _start isn't specified, add to error message
                    if (_start == null) {
                        errorMsg += "Start address must be specified. ";
                    }
                    // If _end isn't specified, add to error message
                    if (_end == null) {
                        errorMsg += "End address must be specified. ";
                    }
                    // If _travelDate isn't specified, add to error message
                    if (_travelDate == null) {
                        errorMsg += "Date of travel must be specified.";
                    }

                    // Display the error message to the user
                    _toastService.Display_Message(errorMsg);
                }
            }
        });

        // Set up a PlaceSelectionListener to handle the response.
        startAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Assign the latitude and longitude of the selected place to the _start variable
                _start = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // Capture any errors
                String error = status.getStatusMessage();
            }
        });

        // Set up a PlaceSelectionListener to handle the response.
        endAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Assign the latitude and longitude of the selected place to the _start variable
                _end = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // Capture any errors
                String error = status.getStatusMessage();
            }
        });
    }

    // END: Private Methods
}
