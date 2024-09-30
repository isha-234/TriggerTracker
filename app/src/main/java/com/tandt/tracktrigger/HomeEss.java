package com.tandt.tracktrigger;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

import android.os.Bundle;



public class HomeEss extends AppCompatActivity {
    private Button Gro;
    private Button ElectricalApp, Stationery, Others;
    String emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ess);

        Gro = (Button) findViewById(R.id.homeEssbutton1);
        ElectricalApp = (Button) findViewById(R.id.homeEssbutton2);
        Stationery = (Button)findViewById(R.id.homeEssbutton4);
        Others = (Button)findViewById(R.id.homeEssbutton5);
        emailid= getIntent().getStringExtra("Emailid");


        Gro.setOnClickListener(this::onClick);
        ElectricalApp.setOnClickListener(this::onClick);
        Stationery.setOnClickListener(this::onClick);
        Others.setOnClickListener(this::onClick);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeEssbutton1:
                openGrocery();
                break;
            case R.id.homeEssbutton2:
                openElectrical();
                break;
            case R.id.homeEssbutton4:
                openStationery();
                break;
            case R.id.homeEssbutton5:
                openOthers();
                break;


        }
    }

    public void openGrocery() {
        Intent intent1 = new Intent(getApplicationContext(), Grocery.class);
        intent1.putExtra("Emailid", emailid);
        intent1.putExtra("EssentialCategory", "Groceries");
        startActivity(intent1);
    }

    public void openElectrical() {
        Intent intent1 = new Intent(getApplicationContext(), Grocery.class);
        intent1.putExtra("Emailid", emailid);
        intent1.putExtra("EssentialCategory", "Electrical");
        startActivity(intent1);
    }

    public void openStationery() {
        Intent intent1 = new Intent(getApplicationContext(), Grocery.class);
        intent1.putExtra("Emailid", emailid);
        intent1.putExtra("EssentialCategory", "Stationery");
        startActivity(intent1);
    }

    public void openOthers() {
        Intent intent1 = new Intent(getApplicationContext(), Grocery.class);
        intent1.putExtra("Emailid", emailid);
        intent1.putExtra("EssentialCategory", "Others");
        startActivity(intent1);
    }

    /*public void openHomeEss2() {
        Intent intent2 = new Intent(HomeEss.this, HomeEssAct2.class);
        intent2.putExtra("Emailid", emailid);

        startActivity(intent2);
    }

    public void openHomeEss3() {
        Intent intent3 = new Intent(HomeEss.this, HomeEssAct3.class);
        intent3.putExtra("Emailid", emailid);

        startActivity(intent3);
    }

    public void openHomeEss4() {
        Intent intent4 = new Intent(HomeEss.this, HomeEssAct4.class);
        intent4.putExtra("Emailid", emailid);

        startActivity(intent4);
    }

    public void openHomeEss5() {
        Intent intent5 = new Intent(HomeEss.this, HomeEssAct5.class);
        intent5.putExtra("Emailid", emailid);

        startActivity(intent5);
    }
*/

}