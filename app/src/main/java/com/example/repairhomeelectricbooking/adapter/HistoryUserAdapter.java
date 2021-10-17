package com.example.repairhomeelectricbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairhomeelectricbooking.R;
import com.example.repairhomeelectricbooking.dto.Order;

import java.util.ArrayList;

public class HistoryUserAdapter extends RecyclerView.Adapter<HistoryUserAdapter.MyViewHolderUser> {
    Context context;

    ArrayList<Order> list;

    public HistoryUserAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_user,parent,false);
        return  new MyViewHolderUser(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUser holder, int position) {

        Order order = list.get(position);
        holder.date.setText(""+order.getCreateDate());
        holder.nameUser.setText(order.getUser().getFullName());
        holder.fee.setText(""+order.getFee());
        holder.thietbi.setText(order.getProblem());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderUser extends RecyclerView.ViewHolder{

        TextView nameUser, fee, thietbi,date;

        public MyViewHolderUser(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDateHistoryUser);
            nameUser = itemView.findViewById(R.id.tvNameHistoryUser);
            fee = itemView.findViewById(R.id.tvPriceHistoryUser);
            thietbi = itemView.findViewById(R.id.tvThietBiSuaHistoryUser);

        }
    }
}
