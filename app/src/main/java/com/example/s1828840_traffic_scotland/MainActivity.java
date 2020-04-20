package com.example.s1828840_traffic_scotland;

// Imports

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.s1828840_traffic_scotland.utilities.dialogs.DatePickerDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

// END: Imports

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Specify the ID of each navigation item in the menu
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_current_incidents,
                R.id.navigation_current_roadworks,
                R.id.navigation_planned_roadworks,
                R.id.navigation_journey_planner,
                R.id.navigation_detailed_view)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /*
     * Name: showDatePickerDialog
     * Description: Method to display a DatePickerDialog
     * Params:
     *      - v: The view that called the method
     * Returns: None
    */
    public void showDatePickerDialog(View v) {
        // Get the Id of the caller view
        int id = v.getId();

        // Initialise a variable to store the reference to the DatePicker's associated TextView
        TextView tv = null;

        // Check the ID of the view
        switch(id) {
            // View is current roadworks
            case R.id.C_R_Date_Picker_Btn:
                tv = findViewById(R.id.C_R_Date_Display);
                break;

            // View is planned roadworks
            case R.id.P_R_Date_Picker_Btn:
                tv = findViewById(R.id.P_R_Date_Display);
                break;

            // View is journey planner
            case R.id.J_P_Date_Picker_Btn:
                tv = findViewById(R.id.J_P_Date_Display);
                break;
        }

        // Create a new DatePickerDialog with the view id and the TextView reference
        DialogFragment newFragment = new DatePickerDialog(id, tv);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
