package com.example.whatsapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessageModel;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter {


    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_recevier,parent,false);
            return new RecieverViewHolder(view);

        }



    }

// this method beacuse everytime we use to take only one design in recycler view but now here we are taking two
    // here in this it is taking input position this tells us which design  we want to
    @Override
    public int getItemViewType(int position) {

          if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
          {
              return SENDER_VIEW_TYPE;
          }
          else
          {
              return RECEIVER_VIEW_TYPE;
          }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        MessageModel messageModel=messageModels.get(position);



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid()+recId;

                                database.getReference().child("chats").child(senderRoom).
                                        child(messageModel.getMessageId()).setValue(null);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                            }
                        }).show();
                return false;
            }
        });


        if(holder.getClass()==SenderViewHolder.class)
        {
                     ((SenderViewHolder) holder).senderMsg.setText(messageModel.getMessage());

            long timestamp=messageModels.get(position).getTimestamp();
            Date netDate=(new Date(timestamp));
            SimpleDateFormat sfd=new SimpleDateFormat("h:mm a");

            ((SenderViewHolder) holder).senderTime.setText(sfd.format(timestamp));




        }
        else
        {
            ( (RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
            long timestamp=messageModels.get(position).getTimestamp();
          Date netDate=(new Date(timestamp));
          SimpleDateFormat sfd=new SimpleDateFormat("h:mm a");


            ((RecieverViewHolder) holder).receiverTime.setText(sfd.format(timestamp));




        }




    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {
         TextView receiverMsg,receiverTime;


        public RecieverViewHolder(@NonNull View itemView) {




            super(itemView);


            receiverMsg=itemView.findViewById(R.id.recieiverText);
            receiverTime=itemView.findViewById(R.id.receiverTime);




        }
    }

 public class SenderViewHolder extends RecyclerView.ViewHolder
 {
     TextView senderMsg,senderTime;

     public SenderViewHolder(@NonNull View itemView) {
         super(itemView);

         senderMsg=itemView.findViewById(R.id.senderText);
         senderTime=itemView.findViewById(R.id.senderTime);



     }
 }





}
