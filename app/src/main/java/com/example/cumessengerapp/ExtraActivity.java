package com.example.cumessengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cumessengerapp.databinding.ActivityExtraBinding;

public class ExtraActivity extends AppCompatActivity {
    ActivityExtraBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityExtraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.etMessage.setText("Contact Us: srivastavanup250@gmail.com\n" +
          //      "manishasrivastava2404@gmail.com");
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExtraActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}