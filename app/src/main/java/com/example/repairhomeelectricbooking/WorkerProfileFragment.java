package com.example.repairhomeelectricbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class WorkerProfileFragment extends Fragment {
    private Button btnLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_worker_profile, container, false);
        btnLogOut = (Button) view.findViewById(R.id.btnLogoutWorker);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finishAffinity();
            }
        });
        return view;
    }
}