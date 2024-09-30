package com.tandt.tracktrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    TextView dashboardContent;
    Button signOut1;
    Button ho,to,notes,brow;
    String emailid,regemail;

    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        emailid= getIntent().getStringExtra("EmailId");
        regemail= getIntent().getStringExtra("email");
        ho = (Button) findViewById(R.id.button7);
        to = (Button) findViewById(R.id.button6);
        notes = (Button) findViewById(R.id.button5);
        brow = (Button) findViewById(R.id.button9);
        signOut1 = (Button) findViewById(R.id.buttonSignOut);
        dashboardContent = (TextView) findViewById(R.id.textView);
        if(emailid==null)
        {
            dashboardContent.setText(dashboardContent.getText()+ regemail + " !" );
            emailid=regemail;
        }
        else
        {
            dashboardContent.setText(dashboardContent.getText()+ emailid + " !" );
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signOut1.setOnClickListener(this);
        ho.setOnClickListener(this);
        to.setOnClickListener(this);
        notes.setOnClickListener(this);
        brow.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonSignOut:
                signOut();
                break;
            case R.id.button7:
                openhome();
                break;
            case R.id.button6:
                opentodo();
                break;
            case R.id.button5:
                opennote();
                break;
            case R.id.button9:
                openbrow();


        }
    }
    private void openbrow() {
        Intent intent = new Intent(getApplicationContext(), Browser.class);
        startActivity(intent);
    }
    private void opennote() {
        Intent intent = new Intent(getApplicationContext(), NotesMainActivity.class);
        intent.putExtra("Emailid", emailid);
        startActivity(intent);
    }
    private void opentodo() {
        Intent intent = new Intent(getApplicationContext(), todolist.class);
        intent.putExtra("Emailid", emailid);
        startActivity(intent);
    }

    public void openhome()
    {
        Intent intent = new Intent(getApplicationContext(), HomeEss.class);
        intent.putExtra("Emailid", emailid);
        startActivity(intent);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
}