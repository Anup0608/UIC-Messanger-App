package com.example.cumessengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cumessengerapp.databinding.ActivityAboutusBinding;

public class AboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityAboutusBinding binding;
        super.onCreate(savedInstanceState);
        binding=ActivityAboutusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutusActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}