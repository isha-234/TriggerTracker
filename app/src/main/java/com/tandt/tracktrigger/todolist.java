package com.tandt.tracktrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class todolist extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private ArrayList<Tasks> items;
    private CustomAdapter itemsAdapter;
    private ListView lvItems;
    FirebaseDatabase rootnode;
    DatabaseReference reff;
    DatabaseReference reff2;





    TextView textView;
    Button button;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    int [] date;
    int [] time;
    String emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<Tasks>();
        itemsAdapter = new CustomAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        rootnode = FirebaseDatabase.getInstance();

        emailid= getIntent().getStringExtra("Emailid");
        String email= emailid.split("\\@",2)[0];
        reff2 = rootnode.getReference("Users").child(email).child("Tasks");

        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) snapshot1.getValue();
                    String Tasktex = (String) map.get("Task Heading");
                    String date_d = (String) map.get("Date");
                    String time_d = (String) map.get("Time");

                    Tasks t1 = new Tasks(Tasktex,date_d,time_d);
                    items.add(t1);

                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textView = findViewById(R.id.Date_Time);
        button = findViewById(R.id.btn_set);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(todolist.this, todolist.this,year, month,day);
                datePickerDialog.show();
            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myMonth = month+1;
        myday = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(todolist.this, todolist.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        textView.setText("Date: " + myday + "/" + myMonth + "/" + myYear + "\n" + "Time: " + myHour +":" + myMinute + " ");
        date = new int[]{myday, myMonth, myYear};
        time = new int[]{myHour, myMinute};
    }



    public void onAddTask(View view) {
        EditText etNewItem =  findViewById(R.id.Task_text);
        String TaskText = etNewItem.getText().toString();
        Tasks t = new Tasks(TaskText,date,time);
        itemsAdapter.add(t);
        etNewItem.setText("");
        textView.setText("");
        Toast toast = Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT);
        toast.show();

        String sd = t.date[0] + "/" + t.date[1] +"/" + t.date[2];
        String st = t.time[0] + ":" + t.time[1];
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notifications", "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notifications")
                .setContentTitle("New Task Added")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentText("Task: " + t.task + " scheduled on date " + sd + " at time " + st)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
        HashMap <String,Object>map = new HashMap<>();
        map.put("Task Heading", t.task);
        map.put("Date", sd);
        map.put("Time", st);
        map.put("EmailId",emailid);
        String email= emailid.split("\\@",2)[0];
        rootnode = FirebaseDatabase.getInstance();
        reff = rootnode.getReference("Users").child(email).child("Tasks");
        //reff2 = reff.push().child(emailid).child("Tasks");
        reff.push().updateChildren(map);

    }



}

