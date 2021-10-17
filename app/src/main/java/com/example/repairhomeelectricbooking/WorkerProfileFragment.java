package com.example.repairhomeelectricbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class WorkerProfileFragment extends Fragment {
    private Button btnLogOut;
    private ImageView imgViewUpdateWorker;
    private DatabaseReference rootDatabaseref;
    private TextView tv_name;
    private CircleImageView img_avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_worker_profile, container, false);
        btnLogOut = (Button) view.findViewById(R.id.btnLogoutWorker);
        imgViewUpdateWorker= (ImageView) view.findViewById(R.id.imgViewUpdateWorker);
        img_avatar=(CircleImageView) view.findViewById(R.id.img_avatar);
        tv_name=(TextView) view.findViewById(R.id.tv_name);
        imgViewUpdateWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),UpdateWorkerProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
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

        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblWorker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        rootDatabaseref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("fullName").getValue().toString();
                tv_name.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        rootDatabaseref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.child("image").getValue().toString();
                if(link.equals("")){
                    Glide.with(getActivity()).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(img_avatar);
                }else{
                    Picasso.get().load(link).into(img_avatar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}