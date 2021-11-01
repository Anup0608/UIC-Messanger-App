package com.example.cumessengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.example.cumessengerapp.databinding.ActivityDescriptionBinding;

public class DescriptionActivity extends AppCompatActivity {
    ActivityDescriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DescriptionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        binding.textView5.setMovementMethod(new ScrollingMovementMethod());
    }
}