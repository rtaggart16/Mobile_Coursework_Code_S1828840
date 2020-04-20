/*
 * Name: Ross Taggart
 * ID: S1828840
 */

package com.example.s1828840_traffic_scotland.ui.current_incidents;

// Imports

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.s1828840_traffic_scotland.R;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.ExpListAdapter;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ProcessedList;
import com.example.s1828840_traffic_scotland.utilities.services.TaskService;
import com.example.s1828840_traffic_scotland.utilities.services.UIService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: CurrentIncidentsFragment
 * Description: Class to handle displaying the current incidents view
 */
public class CurrentIncidentsFragment extends Fragment implements OnMapReadyCallback {

    // Globals


    // Services

    // _taskService - Service used to perform asynchronous operations
    private static TaskService _taskService;

    // _uiService - Service used to perform basic UI operations
    private static UIService _uiService = new UIService();

    // END: Services


    // Variables

    // UI

    // _resultView - View that contains the list and map of the current incidents
    private static View _resultView;

    // _progressView - View that contains the progress spinner
    private static View _progressView;

    // _mapViewButton - Button that toggles the map view to visible (Null in landscape)
    private Button _mapViewButton;

    // _listViewButton - Button that toggles the list view to visible (Null in landscape)
    private Button _listViewButton;

    // _root - Reference to this current view
    private static View _root;

    // _activity - Reference to the activity this view belongs to (MainActivity)
    private static Activity _activity;

    // END: UI

    // Google

    // _currentIncidentsMap - Reference to the actual GoogleMap
    private static GoogleMap _currentIncidentsMap = null;

    // _currentIncidentsMapView - Reference to the GoogleMap view
    private static MapView _currentIncidentsMapView;

    // END: Google

    // Flags

    // _mapLoaded - Flag that signifies if the map has been fully processed
    private static boolean _mapLoaded = false;

    // _listLoaded - Flag that signifies if the list has been fully processed
    private static boolean _listLoaded = false;

    // END: Flags

    // END: Variables


    // END: Globals


    // Methods


    // Public Methods

    /*
     * Name: onCreateView
     * Description: The methods called when the view has to be rendered
     * Params:
     *      - inflater: Used to inflate/render displays
     *      - container: The container the current view is in
     *      - savedInstanceState: Previous state of the view
     * Returns: None
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Display the current view
        View root = inflater.inflate(R.layout.fragment_current_incidents, container, false);

        // Call this view's top-level initialisation method
        Initialise(root);

        // Begin the current incidents fetch
        Fetch_Current_Incidents();

        return root;
    }

    /*
     * Name: Fetch_Current_Incidents
     * Description: Method to start the current incidents asynchronous request
     * Params: None
     * Returns: None
     */
    public void Fetch_Current_Incidents() {
        // Pass the default options to the task, no specific date or road filter
        _taskService.Get_Current_Incidents(null, ViewServiceEnums.C_I_Pop_Page, "");
    }

    /*
     * Name: Show_Incidents
     * Description: Method to toggle between the progress and the result view
     * Params: None
     * Returns: None
     */
    public static void Show_Incidents() {
        // If both the map and the list have been populated
        if (_mapLoaded && _listLoaded) {
            // Toggle the progress and the result view
            _uiService.Toggle_View(_progressView, _resultView);
        }
    }

    /*
     * Name: Populate_List
     * Description: Method to create an expandable list view for the incidents passed in
     * Params:
     *      - items: List of the individual roadworks fetched either from the API or the assets folder
     * Returns: None
     */
    public static void Populate_List(ProcessedList procList) {
        // Create a new ExpandableListView
        ExpandableListView expListView = (ExpandableListView) _root.findViewById(R.id.lvExp);

        // Create new adapter using the procList parameter
        ExpListAdapter listAdapter = new ExpListAdapter(_activity, procList.getHeaders(), procList.getSub_Items());

        // Setting list adapter
        expListView.setAdapter(listAdapter);

        // Specify the list as been processed and check if the view should be faded in
        _listLoaded = true;
        Show_Incidents();
    }

    /*
     * Name: Populate_Map
     * Description: Method to add any valid map markers to the current incidents map and adjust the bounds accordingly
     * Params:
     *      - markers: A list of valid MarkerOptions. This can be empty
     * Returns: None
    */
    public static void Populate_Map(ArrayList<MarkerOptions> markers) {
        try {
            // Checks if there are any markers to add
            if (markers.size() > 0) {

                // Create a Bounds builder that will be used to adjust the bounds of the map to only include the markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                // Foreach options, add to the map and include in bounds
                for (MarkerOptions opt : markers) {
                    _currentIncidentsMap.addMarker(opt);
                    builder.include(opt.getPosition());
                }

                // Build the new bounds
                LatLngBounds bounds = builder.build();

                // Move the camera to only the identified bounds
                _currentIncidentsMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

                // Specify the map has been processed and check if the view should be faded in
                _mapLoaded = true;
                Show_Incidents();
            }
        } catch (Exception ex) {
            String mess = ex.getMessage();
        }
    }

    // Google Map Overwrites

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Populate the global GoogleMap
        _currentIncidentsMap = googleMap;

        // Move the camera to Scotland. This is temporary until the Bounds are changed to encase the incidents
        LatLng scotland = new LatLng(56.5340424, -4.3669823);
        _currentIncidentsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scotland, 5));
    }

    @Override
    public void onResume() {
        _currentIncidentsMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        _currentIncidentsMapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        _currentIncidentsMapView.onLowMemory();
        super.onLowMemory();
    }

    // END: Google Map Overwrites

    // END: Public Methods


    // Private Methods

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
        _currentIncidentsMapView = root.findViewById(R.id.C_I_Map);
        _currentIncidentsMapView.onCreate(null);
        _currentIncidentsMapView.getMapAsync((OnMapReadyCallback) this);
    }

    /*
     * Name: Initialise_UI
     * Description: Method to initialise the UI elements of the view. Involves populating variables and adding listeners
     * Params:
     *      - root: The current View
     * Returns: None
    */
    private void Initialise_UI(View root) {
        // Get the button that triggers the map display
        _mapViewButton = (Button) root.findViewById(R.id.C_I_Map_Btn);
        // Get the button that triggers the list display
        _listViewButton = (Button) root.findViewById(R.id.C_I_List_Btn);

        // Get the progress spinner view
        _progressView = root.findViewById(R.id.C_I_Progress);
        // Get the result view
        _resultView = root.findViewById(R.id.C_I_Result_Display);

        // If the map and list button are present. They will not be present in the landscape version
        if (_mapViewButton != null && _listViewButton != null) {

            // Add an onclick to the map view button
            _mapViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View mapView = _root.findViewById(R.id.mapView);
                    View listView = _root.findViewById(R.id.listView);

                    // If the map view is not visible, toggle it on
                    if (mapView.getVisibility() == View.INVISIBLE) {
                        _uiService.Toggle_View(listView, mapView);
                    }
                }
            });

            // Add an onclick to the list button
            _listViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View mapView = _root.findViewById(R.id.mapView);
                    View listView = _root.findViewById(R.id.listView);

                    // If the list view is not visible, toggle it on
                    if (listView.getVisibility() == View.INVISIBLE) {
                        _uiService.Toggle_View(mapView, listView);
                    }
                }
            });
        }
    }

    // END: Private Methods

    // END: Methods
}
