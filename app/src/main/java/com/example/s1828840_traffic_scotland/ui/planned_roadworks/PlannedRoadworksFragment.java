/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.ui.planned_roadworks;

// Imports

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.s1828840_traffic_scotland.R;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.extensions.ExpListAdapter;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ProcessedList;
import com.example.s1828840_traffic_scotland.utilities.services.TaskService;
import com.example.s1828840_traffic_scotland.utilities.services.ToastService;
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
 * Class: PlannedRoadworksFragment
 * Description: Class to handle displaying the planned roadworks view
 */
public class PlannedRoadworksFragment extends Fragment implements OnMapReadyCallback {

    // Globals

    // Services

    // _taskService - Service used to perform asynchronous operations
    private static TaskService _taskService;

    // _toastService - Service used to simplify the process of displaying a toast
    private static ToastService _toastService;

    // _uiService - Service used to perform basic UI operations
    private static UIService _uiService = new UIService();

    // END: Services

    // Variables

    // UI

    // _resultView - View that contains the list and map of the current roadworks
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

    // _plannedRoadworksMap - Reference to the actual GoogleMap
    private static GoogleMap _plannedRoadworksMap = null;

    // _plannedRoadworksMapView - Reference to the MapView
    private static MapView _plannedRoadworksMapView;

    // END: Google

    // Flags

    // _mapLoaded - Flag that signifies if the map has been fully processed
    private static boolean _mapProcessed = false;

    // _listLoaded - Flag that signifies if the list has been fully processed
    private static boolean _listProcessed = false;

    // END: Flags

    // General

    // SHORT - The threshold for a short duration roadwork
    private static int SHORT = 5;

    // MEDIUM - The threshold for a medium duration roadwork
    private static int MEDIUM = 10;

    // LONG - The threshold for a long duration roadwork
    private static int LONG = 15;

    // END: General

    // END: Variables

    // END: Globals

    // Methods

    // Public Methods

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Display the current view
        View root = inflater.inflate(R.layout.fragment_planned_roadworks, container, false);

        // Call this view's top-level initialisation method
        Initialise(root);

        // Begin the planned roadworks fetch
        Fetch_Planned_Roadworks();

        return root;
    }

    /*
     * Name: Show_Roadworks
     * Description: Method to fade-out the progress view and display the result
     * Params: None
     * Returns: None
     */
    public static void Show_Roadworks() {
        if(_mapProcessed && _listProcessed) {
            _uiService.Toggle_View(_progressView, _resultView);
        }
    }

    /*
     * Name: Hide_Roadworks
     * Description: Method to fade-out the result view and display the progress view
     * Params: None
     * Returns: None
     */
    public static void Hide_Roadworks() {
        _uiService.Toggle_View(_resultView, _progressView);

        _mapProcessed = false;
        _listProcessed = false;
    }

    /*
     * Name: Populate_List
     * Description: Method to create an expandable list view for the roadworks passed in
     * Params:
     *      - items: List of the individual roadworks fetched either from the API
     * Returns: None
     */
    public static void Populate_List(ProcessedList procList)
    {
        if(procList.getHeaders().size() > 0) {
            // Create a new ExpandableListView
            ExpandableListView expListView = (ExpandableListView) _root.findViewById(R.id.lvExp);

            // Create new adapter using the procList parameter
            ExpListAdapter listAdapter = new ExpListAdapter(_activity, procList.getHeaders(), procList.getSub_Items());

            // Setting list adapter
            expListView.setAdapter(listAdapter);

            // Specify the list as been processed and check if the view should be faded in
            _listProcessed = true;
            Show_Roadworks();
        }

    }

    /*
     * Name: Fetch_Planned_Roadworks
     * Description: Method to start the planned roadworks asynchronous request
     * Params: None
     * Returns: None
     */
    public void Fetch_Planned_Roadworks() {
        _taskService.Get_Planned_Roadworks(null, ViewServiceEnums.P_R_Pop_Page, "");
    }

    /*
     * Name: Populate_Map
     * Description: Method to add any valid map markers to the current roadworks map and adjust the bounds accordingly
     * Params:
     *      - markers: A list of valid MarkerOptions. This can be empty
     * Returns: None
     */
    public static void Populate_Map(ArrayList<MarkerOptions> markers) {
        try
        {
            // Clear the map of any existing markers
            _plannedRoadworksMap.clear();

            // Check if there are any markers to add
            if(markers.size() > 0) {
                // Create a new Bounds builder to encase the roadworks
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                // Foreach option, add to map and bounds
                for (MarkerOptions opt : markers) {
                    _plannedRoadworksMap.addMarker(opt);
                    builder.include(opt.getPosition());
                }

                // Build the new bounds
                LatLngBounds bounds = builder.build();

                // Move the map camera so only the roadworks in the bounds are visible
                _plannedRoadworksMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));

                // Specify the map has been loaded and check if the view should be faded in
                _mapProcessed = true;
                Show_Roadworks();
            }
            // No markers present
            else
            {
                // Display a message to the user saying that no roadworks could be found. This happens when no roadworks exist for a date filter
                _toastService.Display_Message("No roadworks found. Reverting to all roadworks");

                // Ensure the date filter is set back to "All Roadworks"
                TextView filterDateDisplay = _root.findViewById(R.id.P_R_Date_Display);
                filterDateDisplay.setText("All Roadworks");

                // Fetch all current roadworks
                _taskService.Get_Planned_Roadworks(null, ViewServiceEnums.P_R_Pop_Page, "");
            }
        }
        catch (Exception ex)
        {
            String mess = ex.getMessage();
        }

    }

    // Google Map Overwrites

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Initialise global GoogleMap
        _plannedRoadworksMap = googleMap;

        // Move the camera to Scotland. This is temporary until the Bounds are changed to encase the roadworks
        LatLng scotland = new LatLng(56.5340424, -4.3669823);
        _plannedRoadworksMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scotland, 5));
    }

    @Override
    public void onResume() {
        _plannedRoadworksMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        _plannedRoadworksMapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        _plannedRoadworksMapView.onLowMemory();
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

        // Create an instance of the ToastService
        _toastService = new ToastService(this.getActivity());
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
        _plannedRoadworksMapView = root.findViewById(R.id.P_R_Map);
        _plannedRoadworksMapView.onCreate(null);
        _plannedRoadworksMapView.getMapAsync((OnMapReadyCallback) this);
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
        _mapViewButton = (Button) root.findViewById(R.id.P_R_Map_Btn);
        // Get the button that triggers the list display
        _listViewButton = (Button) root.findViewById(R.id.P_R_List_Btn);

        // Get the progress spinner view
        _progressView = root.findViewById(R.id.P_R_Progress);
        // Get the result view
        _resultView = root.findViewById(R.id.P_R_Result_Display);

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