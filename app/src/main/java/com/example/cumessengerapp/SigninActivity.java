package com.example.cumessengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cumessengerapp.databinding.ActivitySigninBinding;
import com.example.cumessengerapp.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(SigninActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login To Your Account");
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //email validation
                if(binding.etemail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();

                        auth.signInWithEmailAndPassword(     //email password authentication
                                binding.etemail.getText().toString(),binding.etpassword.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful())
                                        {
                                            Intent intent=new Intent(SigninActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(SigninActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                        //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();

                    }
                }


        });
        binding.tvclicksignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        if (auth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(SigninActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }



}