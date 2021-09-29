package com.example.repairhomeelectricbooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private LinearLayout layoutChinhSuaTaiKhoan;
    private ImageView imgAvatar;
    private TextView tvName,tvEmail;
    private Button btnLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= (View) inflater.inflate(R.layout.fragment_profile, container, false);
        layoutChinhSuaTaiKhoan = (LinearLayout) view.findViewById(R.id.layout_chinhsuataikhoan);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        btnLogOut = (Button) view.findViewById(R.id.btn_Logout);
        showUserInformation(view);
        layoutChinhSuaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),ProfileUpdateActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
    private void showUserInformation(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        String strName = user.getDisplayName();
        String strEmail = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        tvName.setText(strName);
        tvEmail.setText(strEmail);
//        Glide.with(getActivity()).load(photoUrl).error(R.drawable.avatar_default).into(imgAvatar);
    }
}