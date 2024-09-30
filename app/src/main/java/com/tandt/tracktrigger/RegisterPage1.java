package com.tandt.tracktrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class RegisterPage1 extends AppCompatActivity implements View.OnClickListener {
    private EditText uname,pas,mail,ph;
    private Button registeruser;

    public String pho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);


        registeruser=(Button) findViewById(R.id.button3) ;
        registeruser.setOnClickListener(this);

        uname=(EditText) findViewById(R.id.editTextTextPersonName2) ;
        pas=(EditText) findViewById(R.id.editTextTextPassword2) ;
        ph=(EditText) findViewById(R.id.editTextPhone) ;
        mail=(EditText) findViewById(R.id.editTextTextEmailAddress2) ;
        pho=ph.getText().toString().trim();






    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button3:
                registeruser();
                break;



        }

    }

    private void registeruser()
    {
        String Username=uname.getText().toString().trim();
        String email=mail.getText().toString().trim();
        String Phone=ph.getText().toString().trim();
        String Password=pas.getText().toString().trim();

        if(Username.isEmpty())
        {
            uname.setError("Username required");
            uname.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            mail.setError("Email required");
            mail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mail.setError("Please provide valid email");
            return;
        }
        if(Phone.isEmpty())
        {
            ph.setError("Phone required");
            ph.requestFocus();
            return;
        }
        if(Password.isEmpty())
        {
            pas.setError("Password required");
            pas.requestFocus();
            return;
        }
        if(Password.length()<6)
        {
            pas.setError("Password should have more than 6 characters");
            pas.requestFocus();
            return;
        }

        Intent intent= new  Intent(getApplicationContext(),Otp.class);
        intent.putExtra("Phone",Phone);
        intent.putExtra("email",email);
        intent.putExtra("Password",Password);
        startActivity(intent);
        //aastha code
       // SafetyNet.getClient(this).verifyWithRecaptcha("6LdZ198ZAAAAAOGLsTDWjYA7YWQS9GAOrGHMARgJ")
              //  .addOnSuccessListener(
                     /*   new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                                String userResponseToken = recaptchaTokenResult.getstatus();
                                if (!userResponseToken.isEmpty()) {
                                    // Validate the user response token using the
                                    // reCAPTCHA siteverify API.
                                    Intent intent= new  Intent(getApplicationContext(),Otp.class);
                                    intent.putExtra("Phone",Phone);
                                    startActivity(intent);
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            // An error occurred when communicating with the
                            // reCAPTCHA service. Refer to the status code to
                            // handle the error appropriately.
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();

                        } else {
                            // A different, unknown type of error occurred.

                        }
                    }
                });*/



    }
}