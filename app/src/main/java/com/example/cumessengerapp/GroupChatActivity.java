package com.example.cumessengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cumessengerapp.Adapters.GroupChatAdapter;

import com.example.cumessengerapp.databinding.ActivityGroupChatBinding;
import com.example.cumessengerapp.models.GroupMessageModel;


import com.example.cumessengerapp.models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        final String senderId=auth.getUid();
        String receivedId=getIntent().getStringExtra("userId");

        String userName=getIntent().getStringExtra("userName");
        binding.userName.setText("Group Chat");

        String profilePic=getIntent().getStringExtra("profilePic");
        //Toast.makeText(this,"group Chat",Toast.LENGTH_LONG).show();
        //break;
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<GroupMessageModel> messageModels=new ArrayList<>();
        final GroupChatAdapter adapter=new GroupChatAdapter(messageModels,this,receivedId);
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etMessage.getText().toString().isEmpty()) {
                    binding.etMessage.setError("Enter message here");
                }
                final String message=binding.etMessage.getText().toString();
                final GroupMessageModel model=new GroupMessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");
                database.getReference().child("Group Chats").push().setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }
                        });

            }
        });
        database.getReference().child("Group Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    GroupMessageModel model=snapshot1.getValue(GroupMessageModel.class);
                    model.setMessageId(snapshot1.getKey());
                    messageModels.add(model);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}