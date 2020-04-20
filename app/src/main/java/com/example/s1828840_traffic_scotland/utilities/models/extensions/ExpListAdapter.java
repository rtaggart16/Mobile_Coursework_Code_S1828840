/*
 * Name: Ross Taggart
 * ID: S1828840
*/

package com.example.s1828840_traffic_scotland.utilities.models.extensions;

// Imports

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.s1828840_traffic_scotland.R;
import com.example.s1828840_traffic_scotland.utilities.models.view_models.ListHeader;

import java.util.HashMap;
import java.util.List;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ExpListAdapter
 * Description: Class that handles creation and interaction of an expandable list
 */
public class ExpListAdapter extends BaseExpandableListAdapter {
    // Private Variables

    // _context - The current context of the list
    private Context _context;

    // _listDataHeader - List of header items
    private List<ListHeader> _listDataHeader;

    // _listDataChild - HashMap of Key Values. Key is the name of the related header, value is the list of string subitems
    private HashMap<String, List<String>> _listDataChild;

    // END: Private Variables


    // Constructors

    // Main constructor used to populate global private variables
    public ExpListAdapter(Context context, List<ListHeader> listDataHeader, HashMap<String, List<String>> listChildData) {
        // Populate private variables
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    // END: Constructors

    // Overwrite ExpandableList methods

    // Method to fetch an individual sub-item of a top-level list header
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle())
                .get(childPosititon);
    }

    // Method to fetch the ID of an individual subitem
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Method to display the subitems of a top-level list header
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    // Method to count the subitems of a top-level item
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle())
                .size();
    }

    // Method to get a top-level header by id
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    // Method to count he number of top-level headers
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    // Method to get the ID of a top-level header
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Method to display a top-level header
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        ListHeader header = (ListHeader) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(header.getTitle());
        lblListHeader.setTextColor(header.getColour());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
