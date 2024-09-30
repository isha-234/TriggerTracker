package com.tandt.tracktrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.util.ArrayList;
import android.os.StrictMode;

import java.util.Calendar;
import java.util.Map;
import java.io.File;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
        import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Grocery extends AppCompatActivity implements View.OnClickListener {

    Button additem;
    String emailid;

    private ListView GroceryItems;
    private ArrayList<GroceryItem> items;
    private GroceryItemCustomAdapter itemsAdapter;
    FirebaseDatabase rootnode;
    DatabaseReference reff;
    DatabaseReference reff2;
    String category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        additem = (Button) findViewById(R.id.additembutton);
        additem.setOnClickListener(this);


        emailid= getIntent().getStringExtra("Emailid");
        category= getIntent().getStringExtra("EssentialCategory");


        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        GroceryItems = (ListView) findViewById(R.id.GroceryList);

        items = new ArrayList<GroceryItem>();
        itemsAdapter = new GroceryItemCustomAdapter(this, items);
        GroceryItems.setAdapter(itemsAdapter);
        rootnode = FirebaseDatabase.getInstance();

        String email= emailid.split("\\@",2)[0];
        reff2 = rootnode.getReference("Users").child(email).child(category);

        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) snapshot1.getValue();
                    String n = (String) map.get("Item Name");
                    String q = (String) map.get("Item Quantity");
                    int qq = Integer.parseInt(q);

                    GroceryItem t1 = new GroceryItem(n,qq);
                    items.add(t1);

                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void ScreenshotButton(View view)
    {

        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);
       // String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
        //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        //File fileScreenshot = new File(getApplicationContext().getExternalCacheDir(),File.separator+ );
        File fileScreenshot = new File(getExternalFilesDir(null), "Trigger.jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareScreenshot(fileScreenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.additembutton:
                OpenAddItems();



        }
    }
    private void OpenAddItems() {
        Intent intent = new Intent(getApplicationContext(), ItemNameQuantity.class);
        intent.putExtra("Emailid", emailid);
        intent.putExtra("EssentialCategory", category);
        startActivity(intent);
    }


    private void shareScreenshot(File fileScreenshot) {
        Uri uri = Uri.fromFile(fileScreenshot);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
       // intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
       // intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      //  startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
        startActivity(Intent.createChooser(intent, "Share image via"));

    }


}