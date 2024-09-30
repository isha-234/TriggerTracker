package com.tandt.tracktrigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemNameQuantity extends AppCompatActivity implements View.OnClickListener{

    Button addthisitem;
    EditText name,quantity;
    String emailid;
    String category;

    FirebaseDatabase rootnode;
    DatabaseReference reff;
    DatabaseReference reff2;

    private ArrayList<GroceryItem> items;
    private GroceryItemCustomAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_name_quantity);
        name = (EditText) findViewById(R.id.itemname);
        quantity = (EditText) findViewById(R.id.itemquantity);
        emailid= getIntent().getStringExtra("Emailid");
        category= getIntent().getStringExtra("EssentialCategory");


        addthisitem = (Button) findViewById(R.id.addthisitembutton);
        addthisitem.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addthisitembutton:
                AddThisItem();
                break;
        }
    }


    private void AddThisItem() {
        String n = name.getText().toString();
        String q = quantity.getText().toString();
        name.setText("");
        quantity.setText("");

        HashMap<String,Object> map = new HashMap<>();
        map.put("Item Name", n);
        map.put("Item Quantity", q);
        map.put("EmailId",emailid);
        String email= emailid.split("\\@",2)[0];
        rootnode = FirebaseDatabase.getInstance();
        reff = rootnode.getReference("Users").child(email).child(category);

        reff.push().updateChildren(map);
        Toast toast = Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT);
        toast.show();
    }


}