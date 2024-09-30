package com.tandt.tracktrigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryItemCustomAdapter extends ArrayAdapter<GroceryItem> {
    public GroceryItemCustomAdapter(Context context, ArrayList<GroceryItem> groceryitems) {
        super(context, 0, groceryitems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GroceryItem groceryitems = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_grocery_item_list, parent, false);
        }

        // Lookup view for data population
        TextView Item_N = (TextView) convertView.findViewById(R.id.Item_Name);
        TextView Item_Q = (TextView) convertView.findViewById(R.id.Item_Quantity);
        // Populate the data into the template view using the data object
        String dnt;

        dnt = "Quantity: " + groceryitems.itemquantity ;

        //String dnt = "Date: " + tasks.date[0] + "/" + tasks.date[1] +"/" + tasks.date[2]  + "  " + "Time: " + tasks.time[0] + ":" + tasks.time[1] + " ";
        Item_N.setText(groceryitems.itemname);
        Item_Q.setText(dnt);
        // Return the completed view to render on screen
        return convertView;
    }
}
