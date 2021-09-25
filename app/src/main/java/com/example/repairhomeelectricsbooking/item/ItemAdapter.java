package com.example.repairhomeelectricsbooking.item;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repairhomeelectricsbooking.R;

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
    }

    @Override
    public int getItemCount() {
        if(mItem != null){
            return mItem.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.img_Item);
            tvItem = itemView.findViewById(R.id.tv_Item);
        }
    }
}
