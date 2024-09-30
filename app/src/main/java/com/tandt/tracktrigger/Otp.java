    package com.tandt.tracktrigger;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.gms.tasks.TaskExecutors;
    import com.google.firebase.FirebaseException;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseAuthUserCollisionException;
    import com.google.firebase.auth.PhoneAuthCredential;
    import com.google.firebase.auth.PhoneAuthOptions;
    import com.google.firebase.auth.PhoneAuthProvider;


    import java.util.concurrent.TimeUnit;


    public class Otp extends AppCompatActivity {

        String verificationCodeBySystem;
        private Button sub;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        private EditText phone;


        String PhoneNumber,EmailId,Password;
      //  ProgressBar progressBar;



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otp);

            sub = (Button) findViewById(R.id.button4);
            phone = (EditText) findViewById(R.id.editTextNumber) ;



            String ph= getIntent().getStringExtra("Phone");
            ph="+91"+ph.trim();

            PhoneNumber = ph.trim();
            EmailId = getIntent().getStringExtra("email");
            Password = getIntent().getStringExtra("Password");

            sendVerificationCodeToUser(ph);
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = phone.getText().toString();
                    verifyCode(code);
                }
            });

        }

        private void sendVerificationCodeToUser(String ph) {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(ph)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationCodeBySystem = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                if(code!=null)
                {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
            private void verifyCode(String codeByUSer)
            {
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUSer);
                signinTheUserByCredentials(credential);
            }

            private void signinTheUserByCredentials(PhoneAuthCredential credential)
            {

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(Otp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    mAuth.createUserWithEmailAndPassword(EmailId,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(Otp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                                intent.putExtra("email",EmailId);
                                                startActivity(intent);
                                            }
                                            else {
                                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                                {
                                                    Toast.makeText(Otp.this, "You are already registered", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(Otp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });

                                    //Intent intent= new Intent(getApplicationContext(),Dashboard.class);
                                    //{
                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                      //  startActivity(intent);
                                    //}
                                }
                                else
                                {
                                    Toast.makeText(Otp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
    }