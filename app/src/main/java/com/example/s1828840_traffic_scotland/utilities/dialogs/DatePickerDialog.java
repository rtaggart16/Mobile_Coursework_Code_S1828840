/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.dialogs;

// Imports

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.s1828840_traffic_scotland.R;
import com.example.s1828840_traffic_scotland.ui.current_roadworks.CurrentRoadworksFragment;
import com.example.s1828840_traffic_scotland.ui.journey_planner.JourneyPlannerFragment;
import com.example.s1828840_traffic_scotland.ui.planned_roadworks.PlannedRoadworksFragment;
import com.example.s1828840_traffic_scotland.utilities.models.enums.ViewServiceEnums;
import com.example.s1828840_traffic_scotland.utilities.services.TaskService;

import java.util.Calendar;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: DatePickerDialog
 * Description: Class to handle displaying and processing inputs to a DatePicker dialog
*/
public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    // Private Variables

    // target_fragment - The fragment that the dialog as called from
    private int _target_fragment;

    // date_display - The associated TextView that displays the selected date
    private TextView _date_display;

    // _taskService - Service used to perform asynchronous operations
    private static TaskService _taskService;

    // END: Private Variables


    // Constructors

    // Main constructor called which populates the private variables
    public DatePickerDialog(int targetFragment, TextView dateDisplay) {
        _target_fragment = targetFragment;
        _date_display = dateDisplay;
        _taskService = new TaskService(this.getContext());
    }

    // END: Constructors


    // Implement Methods

    // Overwritten method called to create a dialog using the current date
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        android.app.DatePickerDialog dpDialog = new android.app.DatePickerDialog(getActivity(), this, year, month, day);

        // If the current view is planned roadworks or journey planner, only allow future dates
        if(_target_fragment == R.id.P_R_Date_Picker_Btn || _target_fragment == R.id.J_P_Date_Picker_Btn) {
            dpDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }

        return dpDialog;
    }

    // Method that is called when user selects a date
    public void onDateSet(DatePicker view, int year, int month, int day) {

        // Format the selected date as a string and overwrite the date display with it
        String dateString = day + "/" + (month + 1) + "/" + year;
        _date_display.setText(dateString);

        // Get the day that the user selected
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        switch(_target_fragment) {
            // Current page is Current Roadworks
            case R.id.C_R_Date_Picker_Btn:
                // Hide the displayed roadworks
                CurrentRoadworksFragment.Hide_Roadworks();

                // Begin the current roadworks asynchronous request with the selected date as a parameter
                _taskService.Get_Current_Roadworks(c.getTime(), ViewServiceEnums.C_R_Pop_Page, "");
                break;

            // Current page is Planned Roadworks
            case R.id.P_R_Date_Picker_Btn:
                // Hide the displayed roadworks
                PlannedRoadworksFragment.Hide_Roadworks();

                // Begin the planned roadworks asynchronous request with the selected date as a parameter
                _taskService.Get_Planned_Roadworks(c.getTime(), ViewServiceEnums.P_R_Pop_Page, "");
                break;

            // Current page is Journey Planner
            case R.id.J_P_Date_Picker_Btn:
                // Set the travel dat of the journey
                JourneyPlannerFragment.Set_Travel_Date(c.getTime());
                break;
        }
    }

    // END: Implement Methods
}
