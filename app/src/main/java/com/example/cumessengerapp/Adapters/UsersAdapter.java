package com.example.cumessengerapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cumessengerapp.ChatDetailedActivity;
import com.example.cumessengerapp.R;
import com.example.cumessengerapp.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {
    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users user=list.get(position);
        Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.avatar).into(holder.image);
        holder.userName.setText(user.getUserName());
        holder.clgUid.setText(user.getClgUid());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatDetailedActivity.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("profilePic",user.getProfilepic());
                intent.putExtra("userName",user.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView userName,lastMessage,clgUid;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profile_image);
            userName=itemView.findViewById(R.id.userNamelist);
            lastMessage=itemView.findViewById(R.id.lastMessage);
            clgUid=itemView.findViewById(R.id.userClgUid);
        }
    }
}
