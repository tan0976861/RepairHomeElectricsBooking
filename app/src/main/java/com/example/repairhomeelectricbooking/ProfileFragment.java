package com.example.repairhomeelectricbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TextView tvChinhSuaTaiKhoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= (View) inflater.inflate(R.layout.fragment_profile, container, false);
        tvChinhSuaTaiKhoan = (TextView) view.findViewById(R.id.tv_chinhsuataikhoan);
        tvChinhSuaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),ProfileUpdateActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}