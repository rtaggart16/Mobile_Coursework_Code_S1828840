/*
 * Name: Ross Taggart
 * ID: S1828840
 */

package com.example.s1828840_traffic_scotland.ui.detailed_view;

// Imports

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

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
 * Class: DetailedViewFragment
 * Description: Class to handle displaying the detailed view
 */
public class DetailedViewFragment extends Fragment implements OnMapReadyCallback {

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

    // _resultView - View that contains the list and map of the searched incident/roadwork
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

    // _detailedViewMap - Reference to the actual GoogleMap
    private static GoogleMap _detailedViewMap = null;

    // _detailedViewMapView - Reference to the MapView
    private static MapView _detailedViewMapView;

    // END: Google

    // Flags

    // _mapProcessed - Flag that signifies if the map has been fully processed
    private static boolean _mapProcessed = false;

    // _listProcessed - Flag that signifies if the list has been fully processed
    private static boolean _listProcessed = false;

    // END: Flags

    // General

    // _querySelector - The type of item to search for
    public static String _querySelector = "Incident";

    // _roadQuery - The road that is being searched for
    public static String _roadQuery = "";

    // END: General

    // END: Variables

    // END: Globals

    // Methods

    // Public Methods

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Display the current view
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        // Call this view's top-level initialisation method
        Initialise(root);

        return root;
    }

    /*
     * Name: Show_Result
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
     * Name: Populate_Result_Map
     * Description: Method to add any valid map markers to the result map and adjust the bounds accordingly
     * Params:
     *      - markers: A list of valid MarkerOptions. This can be empty
     * Returns: None
     */
    public static void Populate_Result_Map(ArrayList<MarkerOptions> markers) {
        try {
            // Clear any existing markers on the map
            _detailedViewMap.clear();

            // Check if any markers are present
            if (markers.size() > 0) {
                // Create new Bounds builder to encase markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                // Foreach marker, add to map and bounds
                for (MarkerOptions opt : markers) {
                    _detailedViewMap.addMarker(opt);
                    builder.include(opt.getPosition());
                }

                // Build the bounds
                LatLngBounds bounds = builder.build();

                // Move the camera to ensure that only the markers are shown
                _detailedViewMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));

                // Specify that the map is processed and check if the view should be displayed
                _mapProcessed = true;
                Show_Result();
            }
            // No markers present
            else {
                // Display a message to the user that says that no incidents/roadworks were found for their specified road
                _toastService.Display_Message("No " + _querySelector + "(s) found for " + _roadQuery);
                _progressView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ex) {
            String mess = ex.getMessage();
        }
    }

    /*
     * Name: Populate_Result_List
     * Description: Method to create an expandable list view for the result passed in
     * Params:
     *      - items: List of the individual incidents/roadworks fetched from the API
     * Returns: None
     */
    public static void Populate_Result_List(ProcessedList procList) {
        if (procList.getHeaders().size() > 0) {
            // Create a new ExpandableListView
            ExpandableListView expListView = (ExpandableListView) _root.findViewById(R.id.D_V_List_Result);

            // Create new adapter using the procList parameter
            ExpListAdapter listAdapter = new ExpListAdapter(_activity, procList.getHeaders(), procList.getSub_Items());

            // Setting list adapter
            expListView.setAdapter(listAdapter);

            // Specify the list as been processed and check if the view should be faded in
            _listProcessed = true;
            Show_Result();
        }
    }

    // Google Map Overwrites

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Populate the global GoogleMap
        _detailedViewMap = googleMap;

        // Move the camera to Scotland. This is temporary until the Bounds are changed to encase the roadworks
        LatLng scotland = new LatLng(56.5340424, -4.3669823);
        _detailedViewMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scotland, 5));
    }

    @Override
    public void onResume() {
        _detailedViewMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        _detailedViewMapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        _detailedViewMapView.onLowMemory();
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

        // Set the road query to noting
        _roadQuery = "";

        // Default search item back to incident
        _querySelector = "Incident";

        // Specify that neither the map or list is processed
        _mapProcessed = false;
        _listProcessed = false;
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
        _detailedViewMapView = root.findViewById(R.id.D_V_Map);
        _detailedViewMapView.onCreate(null);
        _detailedViewMapView.getMapAsync((OnMapReadyCallback) this);
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
        _mapViewButton = (Button) root.findViewById(R.id.D_V_Map_Btn);
        // Get the button that triggers the list display
        _listViewButton = (Button) root.findViewById(R.id.D_V_List_Btn);

        // Get the button that triggers the search process
        Button querySubmitButton = root.findViewById(R.id.D_V_Submit_Btn);

        // Get the query item filter
        Spinner dropdown = root.findViewById(R.id.D_V_Select);

        // Specify the items a user can select
        String[] items = new String[]{"Incident", "Current Roadwork", "Planned Roadwork"};

        // Specify the dropdown adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // Get the progress spinner view
        _progressView = root.findViewById(R.id.D_V_Progress);
        // Get the result view
        _resultView = root.findViewById(R.id.D_V_Result_View);

        // If the map and list button are present. They will not be present in the landscape version
        if (_mapViewButton != null && _listViewButton != null) {

            // Add an onclick to the map view button
            _mapViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View mapView = _root.findViewById(R.id.D_V_Map_View);
                    View listView = _root.findViewById(R.id.D_V_List_View);

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
                    View mapView = _root.findViewById(R.id.D_V_Map_View);
                    View listView = _root.findViewById(R.id.D_V_List_View);

                    // If the list view is not visible, toggle it on
                    if (listView.getVisibility() == View.INVISIBLE) {
                        _uiService.Toggle_View(mapView, listView);
                    }
                }
            });
        }

        // Add an onitemselected listener to the dropdown
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check the position of the item that is selected
                switch (position) {
                    case 0:
                        // User selected Incident
                        _querySelector = "Incident";
                        break;
                    case 1:
                        // User selected Current Roadwork
                        _querySelector = "Current Roadwork";
                        break;
                    case 2:
                        // User selected Planned Roadwork
                        _querySelector = "Planned Roadwork";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Display a warning if the user does not select anything
                _toastService.Display_Message("Select an item to search for");
            }
        });

        // Set an onclick on the submit button
        querySubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _uiService.Toggle_View(_resultView, _progressView);

                // Get the EditText the user used
                EditText road = _root.findViewById(R.id.D_V_Road_Input);

                // Extract the value of the editText
                String selectedRoad = road.getText().toString();

                // Populated the query road in the global variable
                _roadQuery = selectedRoad;

                // If the user has actually entered a value
                if (selectedRoad.length() > 0) {

                    // Check the item the user wants to search for
                    switch (_querySelector) {
                        case "Incident":
                            // Search for current incidents
                            _taskService.Get_Current_Incidents(null, ViewServiceEnums.C_I_Pop_Page, selectedRoad);
                            break;
                        case "Current Roadwork":
                            // Search for current roadworks
                            _taskService.Get_Current_Roadworks(null, ViewServiceEnums.C_R_Pop_Page, selectedRoad);
                            break;
                        case "Planned Roadwork":
                            // Search for planned roadworks
                            _taskService.Get_Planned_Roadworks(null, ViewServiceEnums.P_R_Pop_Page, selectedRoad);
                            break;
                    }
                }
                // The user has not entered a value
                else {
                    // Display a message saying the user must enter a value
                    _toastService.Display_Message("A road filter is required");
                    _progressView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    // END: Private Methods
}
