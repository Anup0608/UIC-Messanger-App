package com.example.cumessengerapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cumessengerapp.Adapters.StatusAdapter;
import com.example.cumessengerapp.R;
import com.example.cumessengerapp.SettingsActivity;
import com.example.cumessengerapp.databinding.FragmentStatusBinding;
import com.example.cumessengerapp.models.Status;
import com.example.cumessengerapp.models.UserStatus;
import com.example.cumessengerapp.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class StatusFragment extends Fragment {
    FragmentStatusBinding binding;
    ArrayList<UserStatus> userStatuses=new ArrayList<>();
    FirebaseDatabase database;
    ProgressDialog dialog;
    Users users;



    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Uploading Status...");
        dialog.setCancelable(false);
        binding=FragmentStatusBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        StatusAdapter adapter=new StatusAdapter(getContext(),userStatuses);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.statusList.setLayoutManager(layoutManager);
        binding.statusList.setAdapter(adapter);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users=snapshot.getValue(Users.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        database.getReference().child("stories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    userStatuses.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        UserStatus status=new UserStatus();
                        status.setName(snapshot1.child("name").getValue(String.class));
                        status.setClgUid(snapshot1.child("clgUid").getValue(String.class));
                        status.setProfileImage(snapshot1.child("profileImage").getValue(String.class));
                        status.setLastUpdated(snapshot1.child("lastUpdated").getValue(Long.class));

                        ArrayList<Status> statuses=new ArrayList<>();
                        for (DataSnapshot statusSnapshot:snapshot1.child("statuses").getChildren())
                        {
                            Status sampleStatus=statusSnapshot.getValue(Status.class);
                            statuses.add(sampleStatus);
                        }
                        status.setStatuses(statuses);
                        userStatuses.add(status);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null)
        {
            if (data.getData()!=null)
            {
                dialog.show();
                FirebaseStorage storage=FirebaseStorage.getInstance();
                Date date=new Date();
                StorageReference reference= storage.getReference().child("statuses").child(date.getTime()+"");
                reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserStatus userStatus=new UserStatus();
                                    userStatus.setName(users.getUserName());
                                    userStatus.setProfileImage(users.getProfilepic());
                                    userStatus.setLastUpdated(date.getTime());
                                    userStatus.setClgUid(users.getClgUid());

                                    HashMap<String,Object> obj=new HashMap<>();
                                    obj.put("name",userStatus.getName());
                                    obj.put("profileImage",userStatus.getProfileImage());
                                    obj.put("lastUpdated",userStatus.getLastUpdated());
                                    obj.put("clgUid",userStatus.getClgUid());
                                    String imageUrl=uri.toString();
                                    Status status=new Status(imageUrl,userStatus.getLastUpdated());

                                    database.getReference().child("stories")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .updateChildren(obj);

                                    database.getReference().child("stories")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("statuses")
                                            .push().setValue(status);

                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        }

    }

}