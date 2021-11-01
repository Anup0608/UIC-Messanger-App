package com.example.cumessengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cumessengerapp.databinding.ActivitySignUpBinding;
import com.example.cumessengerapp.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    TextView tv;
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[cuchd]+\\.+[in]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We Are Creating Your Account");
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //email password validation
                if(binding.etcuUid.getText().toString().isEmpty()) {
                    binding.etcuUid.setError("Enter your cu uid");
                }
                if(binding.etPassword.getText().toString().isEmpty()) {
                    binding.etPassword.setError("Enter your password");
                }
                if(binding.etEmail.getText().toString().isEmpty()) {
                    binding.etEmail.setError("Enter your email");
                }else {
                    progressDialog.show();
                    if (binding.etEmail.getText().toString().trim().matches(emailPattern)) {
                        auth.createUserWithEmailAndPassword(     //email password authentication
                                binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        progressDialog.dismiss();
                                        if (task.isSuccessful())
                                        {
                                            Users user =new Users(binding.etUserName.getText().toString(),binding.etcuUid.getText().toString(),
                                                    binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                                            String id=task.getResult().getUser().getUid();
                                            database.getReference().child("Users").child(id).setValue(user);
                                            Toast.makeText(SignUpActivity.this,"User Created Successfully",Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                        //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                }
          //      Toast.makeText(SignUpActivity.this,"code is working",Toast.LENGTH_LONG).show();

            }
        });
        binding.tvalreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });

    }


}