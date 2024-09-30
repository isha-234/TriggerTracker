package com.tandt.tracktrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private Button loginButton;
    private EditText editTextEmail, editTextPassword;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleSignInButton;
    private int RC_SIGN_IN=9001;
    FirebaseDatabase rootnode;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(Button) findViewById(R.id.button2) ;
        loginButton=(Button) findViewById(R.id.button) ;
        editTextEmail=(EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword=(EditText) findViewById(R.id.editTextTextPassword);
        googleSignInButton=(SignInButton) findViewById(R.id.google_sign_in) ;
        register.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        googleSignInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            String e = acct.getEmail();
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            intent.putExtra("EmailId",e);
            startActivity(intent);
            //handleSignInResult(task);
        }

    }

    //private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        //try {
            //GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            //Toast.makeText(getApplicationContext(), "Logged-in successfully", Toast.LENGTH_SHORT).show();
      //      Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        //    startActivity(intent);

        //}
        //catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
          //  Toast.makeText(getApplicationContext(), completedTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
        //}
    //}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                startActivity(new Intent(this, RegisterPage1.class));
                break;
            case R.id.button:
                userLogin();
                break;
            case R.id.google_sign_in:
                signIn();
                break;

        }
    }

    public void userLogin()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String Email=editTextEmail.getText().toString().trim();
        String Password=editTextPassword.getText().toString().trim();

        if(Email.isEmpty())
        {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            editTextEmail.setError("Please provide valid email");
            return;
        }

        if(Password.isEmpty())
        {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }
        if(Password.length()<6)
        {
            editTextPassword.setError("Password should have more than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Logged-in successfully", Toast.LENGTH_SHORT).show();
                    rootnode = FirebaseDatabase.getInstance();
                    reff = rootnode.getReference("Users");
                    reff.push().setValue(Email);
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("EmailId", Email);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}