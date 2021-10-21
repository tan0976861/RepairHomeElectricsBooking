package com.example.repairhomeelectricbooking.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairhomeelectricbooking.R;
import com.example.repairhomeelectricbooking.SearchWorkerActivity;
import com.example.repairhomeelectricbooking.dto.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> mItem;

    public ItemAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Item> list){
        this.mItem = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diengiadung,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mItem.get(position);
        if(item == null){
            return;
        }
        holder.imgItem.setImageResource(item.getResourceID());
        holder.tvItem.setText(item.getTitle());

//        holder.cardView_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickGoToSearch(item);
//            }
//        });
    }

    private void onClickGoToSearch(Item item){
        Intent intent = new Intent(context, SearchWorkerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_item",item);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mItem != null){
            return mItem.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView_item;
        private ImageView imgItem;
        private TextView tvItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView_item = itemView.findViewById(R.id.cardView_item_diengiadung);
            imgItem = itemView.findViewById(R.id.img_Item);
            tvItem = itemView.findViewById(R.id.tv_Item);
        }
    }
}
