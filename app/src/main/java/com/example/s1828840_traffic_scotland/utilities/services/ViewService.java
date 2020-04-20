/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports
import android.content.Context;
import android.graphics.Color;
import com.example.s1828840_traffic_scotland.ui.current_incidents.CurrentIncidentsFragment;
import com.example.s1828840_traffic_scotland.ui.current_roadworks.CurrentRoadworksFragment;
import com.example.s1828840_traffic_scotland.ui.detailed_view.DetailedViewFragment;
import com.example.s1828840_traffic_scotland.ui.journey_planner.JourneyPlannerFragment;
import com.example.s1828840_traffic_scotland.ui.planned_roadworks.PlannedRoadworksFragment;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.models.parser.ChannelItem;
import com.example.s1828840_traffic_scotland.utilities.models.parser.routing.RoutingResult;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ListHeader;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ProcessedList;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IViewService
 * Description: Interface that contains method definitions for manipulating Fragments
 */
interface IViewService {
    /*
     * Name: Handle_View_Service
     * Description: Method for handling the processing of fetched data
     * Params:
     *      - choice: Enum that represents the desired functionality to perform
     *      - data: The parsed Channel data
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     */
    void Handle_View_Service(ViewServiceEnums choice, Object data, Date filterDate, String roadFilter);
}

public class ViewService implements IViewService {

    // Private Variables

    // _stringService - Service for manipulating strings
    private StringService _stringService = new StringService();

    // _taskService - Service for performing asycnhronous data fetch
    private TaskService _taskService;

    // _dateService - Service for manipulating and parsing dates
    private DateService _dateService = new DateService();

    // END: Private Variables

    public ViewService(Context cont) {
        _taskService = new TaskService(cont);
    }

    /*
     * Name: Handle_View_Service
     * Description: Method for handling the processing of fetched data
     * Params:
     *      - choice: Enum that represents the desired functionality to perform
     *      - data: The parsed Channel data
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
    */
    public void Handle_View_Service(ViewServiceEnums choice, Object data, Date filterDate, String roadFilter) {
        // Check the desired functionality
        switch (choice) {
            // Current Incidents should be populated
            case C_I_Pop_Page:
                Populate_Current_Incidents_Page(data, filterDate, roadFilter);
                break;

            // Current Roadworks should be populated
            case C_R_Pop_Page:
                Populate_Current_Roadworks_Page(data, filterDate, roadFilter);
                break;

            // Planned roadworks should be populated
            case P_R_Pop_Page:
                Populate_Planned_Roadworks_Page(data, filterDate, roadFilter);
                break;

            // Incidents for journey planner should be populated
            case J_P_Pop_Incidents:
                Populate_Journey_Planner_Current_Incidents(data, filterDate);
                break;

            // Current Roadworks for journey planner should be populated
            case J_P_Pop_Current_Roadworks:
                Populate_Journey_Planner_Current_Roadworks(data, filterDate);
                break;

            // Planned Roadworks for journey planner should be populated
            case J_P_Pop_Planned_Roadworks:
                Populate_Journey_Planner_Planned_Roadworks(data, filterDate);
                break;
        }
    }

    /*
     * Name: Populate_Current_Incidents_Page
     * Description: Method to process channel data and display it on the current incidents page
     * Params:
     *      - data: The parsed Channel data
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     * Returns: None
    */
    public void Populate_Current_Incidents_Page(Object data, Date filterDate, String roadFilter) {
        Channel channel = (Channel) data;

        // Transform the channel items into a processed list
        ProcessedList procList = Process_List(channel.getItems(), filterDate, roadFilter, null);

        // Transform the channel items into markerOptions
        ArrayList<MarkerOptions> markers = Process_Map_Markers(channel.getItems(), filterDate, roadFilter);

        // If road filter is present, populate detailed view
        if (roadFilter != "") {
            DetailedViewFragment.Populate_Result_Map(markers);
            DetailedViewFragment.Populate_Result_List(procList);
        }
        // Populate Current Incidents
        else {
            CurrentIncidentsFragment.Populate_Map(markers);
            CurrentIncidentsFragment.Populate_List(procList);
        }
    }

    /*
     * Name: Populate_Current_Roadworks_Page
     * Description: Method to process channel data and display it on the current roadworks page
     * Params:
     *      - data: The parsed Channel data
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     * Returns: None
     */
    public void Populate_Current_Roadworks_Page(Object data, Date filterDate, String roadFilter) {
        Channel channel = (Channel) data;

        // Transform the channel items into a processed list
        ProcessedList procList = Process_List(channel.getItems(), filterDate, roadFilter, null);

        // Transform the channel items into markerOptions
        ArrayList<MarkerOptions> markers = Process_Map_Markers(channel.getItems(), filterDate, roadFilter);

        // If road filter is present, populate detailed view
        if (roadFilter != "") {
            DetailedViewFragment.Populate_Result_Map(markers);
            DetailedViewFragment.Populate_Result_List(procList);
        }
        // Populate Current Roadworks
        else {
            CurrentRoadworksFragment.Populate_Map(markers);
            CurrentRoadworksFragment.Populate_List(procList);
        }
    }

    /*
     * Name: Populate_Planned_Roadworks_Page
     * Description: Method to process channel data and display it on the planned roadworks page
     * Params:
     *      - data: The parsed Channel data
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     * Returns: None
     */
    public void Populate_Planned_Roadworks_Page(Object data, Date filterDate, String roadFilter) {
        Channel channel = (Channel) data;

        // Transform the channel items into a processed list
        ProcessedList procList = Process_List(channel.getItems(), filterDate, roadFilter, null);

        // Transform the channel items into markerOptions
        ArrayList<MarkerOptions> markers = Process_Map_Markers(channel.getItems(), filterDate, roadFilter);

        // If road filter is present, populate detailed view
        if (roadFilter != "") {
            DetailedViewFragment.Populate_Result_Map(markers);
            DetailedViewFragment.Populate_Result_List(procList);
        }
        // Populate Planned Roadworks
        else {
            PlannedRoadworksFragment.Populate_Map(markers);
            PlannedRoadworksFragment.Populate_List(procList);
        }
    }

    /*
     * Name: Populate_Journey_Planner_Results
     * Description: Method to populate the journey information section of a journey planner result
     * Params:
     *      - result: RoutingResult
     *      - filterDate: Date of the journey
     * Returns: None
    */
    public void Populate_Journey_Planner_Results(Object result, Date filterDate) {
        RoutingResult routingResult = (RoutingResult) result;

        // Populate the journey info section using the first route
        JourneyPlannerFragment.Populate_Journey_Info(routingResult.getRoutes().get(0));

        // Get today
        Date today = _dateService.Get_Today();

        // If the date of the journey is today, fetch any potential incidents
        if (filterDate.compareTo(today) == 0) {
            _taskService.Get_Current_Incidents(today, ViewServiceEnums.J_P_Pop_Incidents, "");
        }
        // If the date of journey is not today, signify that the incidents have been processed
        else {
            JourneyPlannerFragment._incidentsProc = true;
        }

        // Fetch any current roadworks potentially on the route
        _taskService.Get_Current_Roadworks(filterDate, ViewServiceEnums.J_P_Pop_Current_Roadworks, "");

        // Fetch any planned roadworks potentially on the route
        _taskService.Get_Planned_Roadworks(filterDate, ViewServiceEnums.J_P_Pop_Planned_Roadworks, "");
    }

    /*
     * Name: Populate_Journey_Planner_Current_Incidents
     * Description: Method to populate the current incidents section of a journey planner result
     * Params:
     *      - result: RoutingResult
     *      - filterDate: Date of the journey
     * Returns: None
     */
    public void Populate_Journey_Planner_Current_Incidents(Object result, Date filterDate) {
        Channel channel = (Channel) result;

        // If data was fetched
        if (channel.isData_Present()) {
            ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
            ArrayList<ChannelItem> items = new ArrayList<ChannelItem>();

            // Get the bounds of the route
            LatLngBounds bounds = JourneyPlannerFragment._mapBounds;

            // Iterate through each incident
            for (ChannelItem item : channel.Items) {
                LatLng incident = new LatLng(item.getPoint().getLat(), item.getPoint().getLon());

                // Check if the incident is within bounds i.e. could affect the route, add the item to List and Map
                if(bounds.contains(incident))
                {
                    markers.add(new MarkerOptions().position(incident).title(item.getTitle()).icon(BitmapDescriptorFactory.defaultMarker()));
                    items.add(item);
                }
            }

            JourneyPlannerFragment.Populate_Current_Incidents_Markers(markers);
            JourneyPlannerFragment.Populate_Current_Incidents_List(items);
        }

    }

    /*
     * Name: Populate_Journey_Planner_Current_Roadworks
     * Description: Method to populate the current roadworks section of a journey planner result
     * Params:
     *      - result: RoutingResult
     *      - filterDate: Date of the journey
     * Returns: None
     */
    public void Populate_Journey_Planner_Current_Roadworks(Object result, Date filterDate) {
        Channel channel = (Channel) result;

        // Transform the channel items into a list of map markers
        ArrayList<MarkerOptions> markers = Process_Map_Markers(channel.getItems(), filterDate, "");
        ArrayList<ChannelItem> items = new ArrayList<ChannelItem>();

        // Transform the channel items into a processed list
        ProcessedList procList = Process_List(channel.getItems(), filterDate, "", JourneyPlannerFragment._mapBounds);

        // Iterate through each top-level header and add to the items
        for (ListHeader header: procList.getHeaders()) {
            ChannelItem item = new ChannelItem();
            item.setTitle(header.getTitle());
            items.add(item);
        }

        JourneyPlannerFragment.Populate_Current_Roadworks_Markers(markers);
        JourneyPlannerFragment.Populate_Current_Roadworks_List(items);
    }

    /*
     * Name: Populate_Journey_Planner_Planned_Roadworks
     * Description: Method to populate the planned roadworks section of a journey planner result
     * Params:
     *      - result: RoutingResult
     *      - filterDate: Date of the journey
     * Returns: None
     */
    public void Populate_Journey_Planner_Planned_Roadworks(Object result, Date filterDate) {
        Channel channel = (Channel) result;

        // Transform the channel items into a list of map markers
        ArrayList<MarkerOptions> markers = Process_Map_Markers(channel.getItems(), filterDate, "");
        ArrayList<ChannelItem> items = new ArrayList<ChannelItem>();

        // Transform the channel items into a processed list
        ProcessedList procList = Process_List(channel.getItems(), filterDate, "", JourneyPlannerFragment._mapBounds);

        // Iterate through each top-level header and add to the items
        for (ListHeader header: procList.getHeaders()) {
            ChannelItem item = new ChannelItem();
            item.setTitle(header.getTitle());
            items.add(item);
        }

        JourneyPlannerFragment.Populate_Planned_Roadworks_Markers(markers);
        JourneyPlannerFragment.Populate_Planned_Roadworks_List(items);
    }

    /*
     * Name: Process_List
     * Description: Method to transform a list of channel items into a processed list
     * Params:
     *      - items: List of channel items
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     *      - bounds: Optional map bounds to filter by
     * Returns: None
     */
    private ProcessedList Process_List(List<ChannelItem> items, Date filterDate, String roadFilter, LatLngBounds bounds) {
        // Create a new ProcessedList
        ProcessedList procList = new ProcessedList();

        // List that stores items valid to process
        List<ChannelItem> processedItems = new ArrayList<ChannelItem>();

        // If bounds have been specified
        if(bounds != null)
        {
            // Iterate through each iteam
            for (ChannelItem item: items) {
                LatLng itemLoc = new LatLng(item.getPoint().getLat(), item.getPoint().getLon());

                // If the item is within the bounds, its valid for processing
                if(bounds.contains(itemLoc))
                {
                    processedItems.add(item);
                }
            }
        }
        // If bounds have not been specified
        else
        {
            // Valid items are all items
            processedItems = items;
        }

        // Iterate through each valid item
        for (ChannelItem item : processedItems) {
            // Create a new ListHeader and a new list of sub items
            ListHeader header = new ListHeader();
            List<String> subItems = new ArrayList<String>();

            // Initialise variables for start, end, startDate, endDate, description and delay
            // These items are not always present, especially for current incidents

            String start = "";
            String end = "";

            Date startDate = null;
            Date endDate = null;

            String description = item.getDescription();
            String delay = "";

            // Wrap in try-catch to catch parse errors
            try {
                // Extract the start and end date from the description
                start = _stringService.Extract_Key(item.getDescription(), "Start Date", "<");
                end = _stringService.Extract_Key(item.getDescription(), "End Date", "<");

                delay = _stringService.Extract_Key(item.getDescription(), "Delay Information", "<");

                // If the start and end were present, attempt to parse
                if(start != "" && end != "") {
                    startDate = _dateService.Parse_From_String(start);
                    endDate = _dateService.Parse_From_String(end);
                }
            } catch (Exception e) {
                continue;
            }

            // If the startDate and endDate are present
            if (startDate != null && endDate != null) {
                // Initialise variable to track the procesed item
                ChannelItem validItem = null;

                // If the filterDate is present
                if (filterDate != null) {
                    // If the filterDate is within the start and end dates of the current item, it is valid
                    if (startDate.compareTo(filterDate) <= 0 && endDate.compareTo(filterDate) >= 0) {
                        validItem = item;
                    }
                }
                // If the filterDate is not present
                else {
                    // If roadFilter is present
                    if(roadFilter != "")
                    {
                        // If the current item's title contains the roadFilter, this item is valid
                        if (item.getTitle().toLowerCase().contains(roadFilter.toLowerCase())) {
                            validItem = item;
                        }
                    }
                    // If the road filter is not present
                    else
                    {
                        // This item is valid
                        validItem = item;
                    }
                }

                // If validItem is not null
                if (validItem != null) {
                    // Initialise variable to track the colour of the list header
                    int headerColour = 0;

                    // Get the difference between the start and the end date and convert to days
                    long difference = endDate.getTime() - startDate.getTime();
                    long duration = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

                    // Initialise variable to track the string representation of the duration
                    String stringDuration = "";

                    // If the item lasts for less than or equal to 5 days, its short
                    if ((int) duration <= 5) {
                        stringDuration = "Short";
                        headerColour = Color.GREEN;
                    }
                    // If the item lasts for more than 5 days but less than or equal to 15, its medium
                    else if ((int) duration > 5 && (int) duration <= 15) {
                        stringDuration = "Medium";
                        headerColour = Color.YELLOW;
                    }
                    // Otherwise its long
                    else {
                        stringDuration = "Long";
                        headerColour = Color.RED;
                    }

                    // Add the processed colour and the title to the header object
                    header.setTitle(validItem.getTitle());
                    header.setColour(headerColour);

                    // Add the start and end date to the sub items
                    subItems.add("Start: " + start);
                    subItems.add("End: " + end);

                    // Add the delay to the sub items if present
                    if (delay != "") {
                        subItems.add(delay);
                    }

                    // Add the duration to the sub-items
                    subItems.add("Duration: " + (int) duration + " (" + stringDuration + ")");

                    // Add the header and the sub-items to the processedlist
                    procList.addHeader(header);
                    procList.addSubItem(header.getTitle(), subItems);
                }
            } else {
                // If the roadFilter is present
                if(roadFilter != "") {
                    // If the item's title contains the roadFilter, process the item
                    if(item.getTitle().toLowerCase().contains(roadFilter.toLowerCase())) {
                        header.setTitle(item.getTitle());
                        header.setColour(Color.BLACK);

                        subItems.add(description);

                        procList.addHeader(header);
                        procList.addSubItem(header.getTitle(), subItems);
                    }
                }
                else {
                    // Process the item
                    header.setTitle(item.getTitle());
                    header.setColour(Color.BLACK);

                    subItems.add(description);

                    procList.addHeader(header);
                    procList.addSubItem(header.getTitle(), subItems);
                }
            }
        }

        // Return the processed list
        return procList;
    }

    /*
     * Name: Process_Map_Markers
     * Description: Method to transform a list of channel items into a list of map markers
     * Params:
     *      - items: List of channel items
     *      - filterDate: Optional date to filter by
     *      - roadFilter: Optional road to filter by
     * Returns: None
     */
    private ArrayList<MarkerOptions> Process_Map_Markers(List<ChannelItem> items, Date filterDate, String roadFilter) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        // Iterate through each chanel item
        for (ChannelItem item: items) {

            // Initialise the string representations of the start and end date
            String start = "";
            String end = "";

            // Initialise the Date representation of the strt and end date
            Date startDate = null;
            Date endDate = null;

            try {
                // Fetch the start and end date from the description, if present
                start = _stringService.Extract_Key(item.getDescription(), "Start Date", "<");
                end = _stringService.Extract_Key(item.getDescription(), "End Date", "<");

                // If the start and end dates were extracted, parse the dates
                if(start != "" && end != "") {
                    startDate = _dateService.Parse_From_String(start);
                    endDate = _dateService.Parse_From_String(end);
                }
            }
            catch (Exception ex) {
                continue;
            }

            // Get the co-ordinates of the item
            LatLng incident = new LatLng(item.getPoint().getLat(), item.getPoint().getLon());

            // If the start and end dates were parsed successfully
            if (startDate != null && endDate != null) {
                // Initialise a Channel item to track the current item
                ChannelItem validItem = null;

                // If filterDate is present
                if (filterDate != null) {
                    // If the filterDate is between the start and the end date of the item, the item is valid
                    if (startDate.compareTo(filterDate) <= 0 && endDate.compareTo(filterDate) >= 0) {
                        validItem = item;
                    }
                }
                // The filter date is not present
                else {
                    // If the roadFilter is present
                    if(roadFilter != "")
                    {
                        // If the item's title contains the roadFilter, the item is valid
                        if (item.getTitle().toLowerCase().contains(roadFilter.toLowerCase())) {
                            validItem = item;
                        }
                    }
                    else
                    {
                        // The item is valid
                        validItem = item;
                    }
                }

                // If the item is valid
                if (validItem != null) {
                    // Initialise variable to track the colour of the map marker
                    float headerColour = 0f;

                    // Get the difference between the start and the end date and convert to days
                    long difference = endDate.getTime() - startDate.getTime();
                    long duration = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

                    // If the item lasts for less than or equal to 5 days, its short
                    if ((int) duration <= 5) {
                        headerColour = BitmapDescriptorFactory.HUE_GREEN;
                    }
                    // If the item lasts for more than 5 days but less than or equal to 15, its medium
                    else if ((int) duration > 5 && (int) duration <= 15) {
                        headerColour = BitmapDescriptorFactory.HUE_ORANGE;
                    }
                    // Otherwise its long
                    else {
                        headerColour = BitmapDescriptorFactory.HUE_RED;
                    }

                    markers.add(new MarkerOptions().position(incident).title(item.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(headerColour)));
                }
            } else {
                // If the roadFilter is present
                if(roadFilter != "")
                {
                    // If the item's title contains the road filter, process
                    if(item.getTitle().toLowerCase().contains(roadFilter.toLowerCase()))
                    {
                        markers.add(new MarkerOptions().position(incident).title(item.getTitle()).icon(BitmapDescriptorFactory.defaultMarker()));
                    }
                }
                else
                {
                    // Process the item
                    markers.add(new MarkerOptions().position(incident).title(item.getTitle()).icon(BitmapDescriptorFactory.defaultMarker()));
                }
            }
        }

        return markers;
    }
}


