package com.example.repairhomeelectricbooking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairhomeelectricbooking.R;

import java.util.ArrayList;

public class BillWorkerAdapter extends RecyclerView.Adapter<MyViewHolderBillWorker2> {

    ArrayList<String> list;

    public BillWorkerAdapter(ArrayList<String> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolderBillWorker2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bill_detail,parent,false);

        return new MyViewHolderBillWorker2(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderBillWorker2 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyViewHolderBillWorker2 extends RecyclerView.ViewHolder {
    ImageView img;

    private BillWorkerAdapter adapter;
    public MyViewHolderBillWorker2(@NonNull View itemView) {
        super(itemView);
        itemView.findViewById(R.id.image_removev).setOnClickListener(view -> {
            adapter.list.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });

    }
    public MyViewHolderBillWorker2 linkAdapter( BillWorkerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}