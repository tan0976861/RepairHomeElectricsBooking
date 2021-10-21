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
import com.example.repairhomeelectricbooking.dto.User;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HistoryWorkerAdapter extends RecyclerView.Adapter<HistoryWorkerAdapter.MyViewHolder> {

    Context context;

    ArrayList<Order> list;

    public HistoryWorkerAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_worker,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Order order = list.get(position);
        holder.date.setText(""+order.getCreateDate());
        holder.nameUser.setText(order.getUser().getFullName());
        NumberFormat format = new DecimalFormat("0.#");
        holder.fee.setText(""+format.format(order.getFee()));
        holder.thietbi.setText(order.getProblem());
        if(order.getStatus() == 2){
            holder.status.setText("Đã hoàn thành");
            holder.status.setTextColor(Color.GREEN);
        }else if(order.getStatus() == 1) {
            holder.status.setText("Đang tiến hành");
            holder.status.setTextColor(Color.GRAY);
        }else{
            holder.status.setText("Đã hủy");
            holder.status.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameUser, fee, thietbi,date,status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDateHistory);
            nameUser = itemView.findViewById(R.id.tvNameHistory);
            fee = itemView.findViewById(R.id.tvPriceHistory);
            thietbi = itemView.findViewById(R.id.tvThietBiSuaHistory);
            status = itemView.findViewById(R.id.tvStatusHistoryWorker);

        }
    }

}