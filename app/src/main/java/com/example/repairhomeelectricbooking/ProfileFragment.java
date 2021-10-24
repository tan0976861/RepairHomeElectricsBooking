package com.example.repairhomeelectricbooking;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TextView layoutChinhSuaTaiKhoan;
    private ImageView imgAvatar;
    private TextView tvName,tvEmail;
    private Button btnLogOut;
    private DatabaseReference rootDatabaseref;
    private TextView layoutHotrokhachhang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= (View) inflater.inflate(R.layout.fragment_profile, container, false);
        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblUser");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        layoutChinhSuaTaiKhoan = (TextView) view.findViewById(R.id.tv_chinhsuataikhoan);
        tvName = (TextView) view.findViewById(R.id.tv_name_user);
        layoutHotrokhachhang=(TextView) view.findViewById(R.id.layoutHotrokhachhanguser);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        btnLogOut = (Button) view.findViewById(R.id.btn_Logout);
        showUserInformation(view);
        layoutChinhSuaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),ProfileUpdateActivity.class);
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
        layoutHotrokhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHoTroKhachHang(Gravity.CENTER);
            }
        });

        rootDatabaseref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("fullName").getValue().toString();
                String phone=snapshot.child("email").getValue().toString();
                tvName.setText(name);
                tvEmail.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
    private void showUserInformation(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            return;
//        }
//        String strName = user.getDisplayName();
//        String strEmail = user.getEmail();
//        Uri photoUrl = user.getPhotoUrl();
//        tvName.setText(strName);
//        tvEmail.setText(strEmail);
//        Glide.with(getActivity()).load(photoUrl).error(R.drawable.avatar_default).into(imgAvatar);

        FirebaseDatabase md = FirebaseDatabase.getInstance();
        DatabaseReference mr = md.getReference("tblUser").child(user.getUid()).child("image");


        mr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(getActivity()).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(imgAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void openHoTroKhachHang(int gravity){
        final Dialog dialog= new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hotrokhachhang);

        Window window= dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.CENTER==gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        Button btn_khongcamon=dialog.findViewById(R.id.btn_khongcamon);
        Button btn_goi=dialog.findViewById(R.id.btn_goi);
        TextView tv_hotline=dialog.findViewById(R.id.tv_hotline);

        btn_khongcamon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = tv_hotline.getText().toString();
                Intent intent= new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        dialog.show();
    }
}