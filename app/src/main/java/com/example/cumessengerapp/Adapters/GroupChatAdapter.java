package com.example.cumessengerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cumessengerapp.R;
import com.example.cumessengerapp.models.GroupMessageModel;
import com.example.cumessengerapp.models.MessageModel;
import com.example.cumessengerapp.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupChatAdapter extends RecyclerView.Adapter {
    ArrayList<GroupMessageModel> messageModels;
    //ArrayList<Users> list;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;




    public GroupChatAdapter(ArrayList<GroupMessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public GroupChatAdapter(ArrayList<GroupMessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    /*public GroupChatAdapter(ArrayList<GroupMessageModel> messageModels, ArrayList<Users> list, Context context, String recId) {
        this.messageModels = messageModels;
        this.list = list;
        this.context = context;
        this.recId = recId;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.group_sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.group_sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupMessageModel messageModel=messageModels.get(position);
        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


                return false;
            }
        });*/




        if (holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());

            FirebaseDatabase.getInstance().getReference().child("Users").child(messageModel.getuId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                Users user=snapshot.getValue(Users.class);
                                ((SenderViewHolder)holder).senderName.setText(user.getUserName());
                                ((SenderViewHolder)holder).senderClgUid.setText(user.getClgUid());


                                //user.getUserName();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            Date date=new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
            String strDate=simpleDateFormat.format(date);
            ((SenderViewHolder)holder).senderTime.setText(strDate.toString());
        }
        else {
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());

            FirebaseDatabase.getInstance().getReference().child("Users").child(messageModel.getuId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                Users user=snapshot.getValue(Users.class);
                                ((RecieverViewHolder)holder).receiverName.setText(user.getUserName());
                                ((RecieverViewHolder)holder).receiverClgUid.setText(user.getClgUid());


                                //user.getUserName();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            //((RecieverViewHolder)holder).receiverName.setText(user.getUserId());
            //((RecieverViewHolder)holder).receiverClgUid.setText(user.getClgUid());
            Date date=new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
            String strDate=simpleDateFormat.format(date);
            ((RecieverViewHolder)holder).receiverTime.setText(strDate.toString());
        }
    }

    @Override
    public int getItemCount()
    {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg,receiverTime,receiverName,receiverClgUid;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg=itemView.findViewById(R.id.receiverText);
            receiverTime=itemView.findViewById(R.id.receiverTime);
            receiverName=itemView.findViewById(R.id.receiverName);
            receiverClgUid=itemView.findViewById(R.id.receiverClgUid);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,senderTime,senderName,senderClgUid;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
            senderName=itemView.findViewById(R.id.senderName);
            senderClgUid=itemView.findViewById(R.id.senderClgUid);
        }
    }
}
