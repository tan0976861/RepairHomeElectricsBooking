package com.example.repairhomeelectricbooking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repairhomeelectricbooking.adapter.HistoryWorkerAdapter;
import com.example.repairhomeelectricbooking.dto.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WorkerHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database,database2;
    HistoryWorkerAdapter myAdapter;
    ArrayList<Order> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_history, container, false);
        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.historyWorkerList);
        database = FirebaseDatabase.getInstance().getReference("tblOrder");
        database2 = FirebaseDatabase.getInstance().getReference("tblUser");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        list = new ArrayList<>();
        myAdapter = new HistoryWorkerAdapter(getActivity(),list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = dataSnapshot.getValue(Order.class);
                    if(order.getWorker().getWorkerID().equals(firebaseUser.getUid())){
                        list.add(order);
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}