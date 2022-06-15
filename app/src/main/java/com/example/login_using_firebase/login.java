package com.example.login_using_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText memail,mpwd;
    private Button mlogin;
    private TextView nrsu;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         memail=findViewById(R.id.email);
         mpwd=findViewById(R.id.pwd);
         mlogin=findViewById(R.id.login);
         nrsu=findViewById(R.id.su);

         mAuth=FirebaseAuth.getInstance();

         nrsu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                startActivity(new Intent(login.this,MainActivity.class));
             }
         });
         mlogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 loginUser();
             }
         });



    }
    private void loginUser(){
        String email=memail.getText().toString();
        String pwd=mpwd.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pwd.isEmpty() && pwd.length()>6){
                  mAuth.signInWithEmailAndPassword(email,pwd)
                          .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                              @Override
                              public void onSuccess(AuthResult authResult) {
                                  Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(login.this,welcome.class));
                                  finish();
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(login.this,"Login failed",Toast.LENGTH_SHORT).show();

                      }
                  });

            }else{
                mpwd.setError("Password length must be atleast 6 characters");
            }
        }else if(email.isEmpty()){
            memail.setError("Email can't be empty");
        }else{
            memail.setError("Please enter valid email");
        }
    }
}