package com.example.cumessengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cumessengerapp.databinding.ActivityGroupChatBinding;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}