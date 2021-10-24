package com.example.repairhomeelectricbooking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairhomeelectricbooking.R;
import com.example.repairhomeelectricbooking.dto.Order;
import com.example.repairhomeelectricbooking.dto.Rating;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.MyViewHolderFeedbackWorker> {
    Context context;

    ArrayList<Rating> list;

    public FeedBackAdapter(Context context, ArrayList<Rating> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolderFeedbackWorker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.feedbacklist,parent,false);
        return  new MyViewHolderFeedbackWorker(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFeedbackWorker holder, int position) {

        Rating rating = list.get(position);
        holder.idUser.setText(rating.getCustomerId());
        holder.commentFeedback.setText(rating.getComment());
        holder.numberOfStarFeedback.setText(""+rating.getRatingPoint());
        holder.date.setText(rating.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderFeedbackWorker extends RecyclerView.ViewHolder{

        TextView idUser, commentFeedback, numberOfStarFeedback,date;

        public MyViewHolderFeedbackWorker(@NonNull View itemView) {
            super(itemView);

            idUser = itemView.findViewById(R.id.tvOrderIDFeedBack);
            commentFeedback = itemView.findViewById(R.id.tv_commentFeedback);
            numberOfStarFeedback = itemView.findViewById(R.id.tv_numberOfStarFeedback);
            date = itemView.findViewById(R.id.tvDateFeedbackReview);
        }
    }
}
