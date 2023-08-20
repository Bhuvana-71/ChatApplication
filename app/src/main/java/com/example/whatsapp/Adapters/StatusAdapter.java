package com.example.whatsapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsapp.Models.Status;
import com.example.whatsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>  {
    private Context context;
    private ArrayList<Status> statusArrayList;
    public StatusAdapter(Context context, ArrayList<Status> status) {
        this.context = context;
        this.statusArrayList = status;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatusViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.status_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {


        Status status = statusArrayList.get(position);

        Glide.with(context)
                .load(status.getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.defaultimage))
                .into(holder.image);

       holder.tvName.setText(status.getName());

       long timestamp=status.getTime();
        Date netDate=(new Date(timestamp));
        SimpleDateFormat sfd=new SimpleDateFormat("h:mm a");




        holder.tvTime.setText("Today at " +sfd.format(timestamp));




    }

    @Override
    public int getItemCount() {
        if (statusArrayList != null) {
            return statusArrayList.size();
        }
        return 0;

    }

    class StatusViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView image;
        TextView tvName;
        TextView tvTime;


        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);


            image=itemView.findViewById(R.id.profile);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
        }





    }


}
