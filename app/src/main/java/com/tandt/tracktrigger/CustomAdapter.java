package com.tandt.tracktrigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Tasks>{

    public CustomAdapter(Context context, ArrayList<Tasks> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tasks tasks = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
        }

        // Lookup view for data population
        TextView Task_H = (TextView) convertView.findViewById(R.id.Task_Heading);
        TextView DatenTime = (TextView) convertView.findViewById(R.id.DatenTime);
        // Populate the data into the template view using the data object
        String dnt;
        if(tasks.date==null){
            dnt = "Date: " + tasks.date_d + "  " + "Time: " + tasks.time_d + " ";
        }
        else {
            dnt = "Date: " + tasks.date[0] + "/" + tasks.date[1] + "/" + tasks.date[2] + "  " + "Time: " + tasks.time[0] + ":" + tasks.time[1] + " ";
        }
        //String dnt = "Date: " + tasks.date[0] + "/" + tasks.date[1] +"/" + tasks.date[2]  + "  " + "Time: " + tasks.time[0] + ":" + tasks.time[1] + " ";
        Task_H.setText(tasks.task);
        DatenTime.setText(dnt);
        // Return the completed view to render on screen
        return convertView;
    }
}
