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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {

    private EditText memail,mpwd;
    private Button msignup;
    private TextView mtv;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail=findViewById(R.id.email);
        mpwd=findViewById(R.id.pwd);
        msignup=findViewById(R.id.signup);
        mtv=findViewById(R.id.arli);

        mAuth=FirebaseAuth.getInstance();

        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,login.class));
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser(view);
            }
        });
    }

    private void checkUser(View view){
        mAuth.fetchSignInMethodsForEmail(memail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean check= !task.getResult().getSignInMethods().isEmpty();

                        if(check){
                            Toast.makeText(MainActivity.this,"User already exists. Please login to continue",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,login.class));
                            finish();
                        }
                        else{
                            createUser();
                        }


                    }
                });
    }
    private void createUser(){
        String email=memail.getText().toString();
        String password=mpwd.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty() && password.length()>6){
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,login.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Registration error",Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                mpwd.setError("Password length must be atleast 6 characters");
            }
        }else if(email.isEmpty()){
            memail.setError("Email can't be empty");
        }else{
            memail.setError("Please enter correct email");
        }

    }
}