package com.example.repairhomeelectricbooking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.Item;
import com.example.repairhomeelectricbooking.adapter.ItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainUserFragment} factory method to
 * create an instance of this fragment.
 */
public class MainUserFragment extends Fragment {

    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1,strUserName,strPhone;
    private String mParam2;
    private View view;

    private Context mContext;
    private Button btnNextToDecriseProblem;
    private Spinner  edtInputThietBi;
    private TextView tvChonHangDen,tvChonLoaiDen,tvHangKhacDen,tvLoaiKhacDen;
    private EditText edtHangKhacDen, edtLoaiKhacDen;
    private TextView tvChonHangTivi,tvChonKichThuocTivi,tvChonLoaiTivi,tvNamRaMat,tvHangKhacTivi,tvNamRaMatKhacTivi,tvLoaiKhacTivi;
    private EditText edtHangKhacKhacTivi,edtNamRaMatKhacTivi,edtLoaiKhacTivi;
    private TextView tvChonHangNoiCom,tvChonLoaiNoiCom,tvChonDungTichNoiCom,tvChonHangKhacNoiCom,tvLoaiKhacNoiCom,tvChonDungTichKhacNoiCom;
    private EditText edtChonHangKhacNoiCom,edtLoaiKhacNoiCom,edtChonDungTichKhacNoiCom;
    private TextView tvChonHangTuLanh,tvChonLoaiTuLanh,tvChonTheTichTuLanh,tvHangKhacTuLanh,tvChonTheTichKhacTuLanh;
    private EditText edtHangKhacTuLanh,edtChonTheTichKhacTuLanh;
    private TextView tvChonHangMayLanh,tvChonLoaiMayLanh,tvChonCongSuatMayLanh,tvHangKhacMayLanh,tvCongsuatKhacMayLanh,tvLoaiKhacMayLanh;
    private EditText edtChonHangKhacMayLanh,edtCongsuatKhacMayLanh,edtLoaiKhacMayLanh;
    private TextView tvHangQuat,tvInputChonLoaiQuat,tvHangKhacQuat,tvLoaiKhacQuat;
    private EditText edtHangKhacQuat,edtLoaiKhacQuat;
    private TextView tvChonHangMayGiat,tvChonLoaiMayGiat,tvChonKhoiLuongGiatMayGiat,tvHangKhacMayGiat, tvChonKhoiLuongKhacMayGiat;
    private EditText edtHangKhacMayGiat, edtChonKhoiLuongKhacMayGiat;
    private RadioGroup Rd_Congnghe_MayGiat;
    private RadioButton Rbtn_Congnghe_MayGiat_Co,Rbtn_Congnghe_MayGiat_Khong;
    private TextView tvChonHangLoNuong,tvChonLoaiLoNuong,tvChonDungTichLoNuong,tvChonHangKhacLoNuong;
    private EditText edtChonHangKhacLoNuong;
    private TextView tvChonHangMayRuaChen,tvChonLoaiMayRuaChen,tvChonDungTichMayRuaChen;
    private boolean[] selectedProblem;
    private LinearLayout ly_inputQuat,ly_inputDen,ly_inputMayLanh,ly_inputMayGiat,ly_inputNoiCom,ly_inputLoNuong,ly_inputMayRuaChen,ly_inputTivi,ly_inputTuLanh;
    String congNgheInverter;



    public MainUserFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_main_user, container, false);
        edtInputThietBi = (Spinner) view.findViewById(R.id.edtInputThietBi);
        btnNextToDecriseProblem = (Button) view.findViewById(R.id.btn_NextToDecriseProblem);
        ly_inputQuat=(LinearLayout) view.findViewById(R.id.ly_inputQuat);
        ly_inputDen=(LinearLayout) view.findViewById(R.id.ly_inputDen);
        ly_inputMayLanh=(LinearLayout) view.findViewById(R.id.ly_inputMayLanh);
        ly_inputMayGiat=(LinearLayout) view.findViewById(R.id.ly_inputMayGiat);
        ly_inputNoiCom=(LinearLayout) view.findViewById(R.id.ly_inputNoiCom);
        ly_inputLoNuong=(LinearLayout) view.findViewById(R.id.ly_inputLoNuong);
        ly_inputMayRuaChen=(LinearLayout) view.findViewById(R.id.ly_inputMayRuaChen);
        ly_inputTivi=(LinearLayout) view.findViewById(R.id.ly_inputTivi);
        ly_inputTuLanh=(LinearLayout) view.findViewById(R.id.ly_inputTuLanh);
        //spinnerHangMayLanh=(TextView) view.findViewById(R.id.spinnerHangMayLanh);
        //den
        tvChonHangDen=(TextView) view.findViewById(R.id.tvChonHangDen);
        tvChonLoaiDen=(TextView) view.findViewById(R.id.tvChonLoaiDen);
        tvHangKhacDen=(TextView)view.findViewById(R.id.tvHangKhacDen);
        edtHangKhacDen=(EditText)view.findViewById(R.id.edtHangKhacDen);
        tvLoaiKhacDen=(TextView)view.findViewById(R.id.tvLoaiKhacDen);
        edtLoaiKhacDen=(EditText) view.findViewById(R.id.edtLoaiKhacDen);
        //quat
        tvHangQuat=(TextView) view.findViewById(R.id.spinnerHangQuat);
        tvInputChonLoaiQuat=(TextView) view.findViewById(R.id.tvInputChonLoaiQuat);
        tvHangKhacQuat=(TextView)view.findViewById(R.id.tvHangKhacQuat);
        edtHangKhacQuat=(EditText)view.findViewById(R.id.edtHangKhacQuat);
        tvLoaiKhacQuat=(TextView)view.findViewById(R.id.tvLoaiKhacQuat);
        edtLoaiKhacQuat  =(EditText)view.findViewById(R.id.edtLoaiKhacQuat);
        //attribute Tivi
        tvChonHangTivi =(TextView) view.findViewById(R.id.tvChonHangTivi);
        tvChonKichThuocTivi=(TextView) view.findViewById(R.id.tvChonKichThuocTivi);
        tvChonLoaiTivi =(TextView) view.findViewById(R.id.tvChonLoaiTivi);
        tvNamRaMat=(TextView) view.findViewById(R.id.tvNamRaMat);
        tvHangKhacTivi=(TextView)view.findViewById(R.id.tvHangKhacTivi);
        edtHangKhacKhacTivi=(EditText)view.findViewById(R.id.edtHangKhacKhacTivi);
        tvNamRaMatKhacTivi=(TextView)view.findViewById(R.id.tvNamRaMatKhacTivi);
        edtNamRaMatKhacTivi=(EditText)view.findViewById(R.id.edtNamRaMatKhacTivi);
        tvLoaiKhacTivi=(TextView)view.findViewById(R.id.tvLoaiKhacTivi);
        edtLoaiKhacTivi=(EditText)view.findViewById(R.id.edtLoaiKhacTivi);
        //attribute Noi Com
        tvChonHangNoiCom=(TextView) view.findViewById(R.id.tvChonHangNoiCom);
        tvChonLoaiNoiCom=(TextView) view.findViewById(R.id.tvChonLoaiNoiCom);
        tvChonDungTichNoiCom=(TextView) view.findViewById(R.id.tvChonDungTichNoiCom);
        tvChonHangKhacNoiCom=(TextView)view.findViewById(R.id.tvChonHangKhacNoiCom);
        edtChonHangKhacNoiCom=(EditText)view.findViewById(R.id.edtChonHangKhacNoiCom);
        tvLoaiKhacNoiCom=(TextView)view.findViewById(R.id.tvLoaiKhacNoiCom);
        edtLoaiKhacNoiCom=(EditText)view.findViewById(R.id.edtLoaiKhacNoiCom);
        tvChonDungTichKhacNoiCom=(TextView)view.findViewById(R.id.tvChonDungTichKhacNoiCom);
        edtChonDungTichKhacNoiCom=(EditText)view.findViewById(R.id.edtChonDungTichKhacNoiCom);
        //attribute Tu Lanh
        tvChonHangTuLanh=(TextView) view.findViewById(R.id.tvChonHangTuLanh);
        tvChonLoaiTuLanh=(TextView) view.findViewById(R.id.tvChonLoaiTuLanh);
        tvChonTheTichTuLanh=(TextView) view.findViewById(R.id.tvChonTheTichTuLanh);
        tvHangKhacTuLanh=(TextView)view.findViewById(R.id.tvHangKhacTuLanh);
        edtHangKhacTuLanh=(EditText)view.findViewById(R.id.edtHangKhacTuLanh);
        tvChonTheTichKhacTuLanh=(TextView)view.findViewById(R.id.tvChonTheTichKhacTuLanh);
        edtChonTheTichKhacTuLanh=(EditText)view.findViewById(R.id.edtChonTheTichKhacTuLanh);
        //attribute May Lanh
        tvChonHangMayLanh=(TextView) view.findViewById(R.id.tvChonHangMayLanh);
        tvChonLoaiMayLanh=(TextView) view.findViewById(R.id.tvChonLoaiMayLanh);
        tvChonCongSuatMayLanh=(TextView) view.findViewById(R.id.tvChonCongSuatMayLanh);
        tvHangKhacMayLanh=(TextView) view.findViewById(R.id.tvHangKhacMayLanh);
        edtChonHangKhacMayLanh=(EditText) view.findViewById(R.id.edtChonHangKhacMayLanh);
        tvCongsuatKhacMayLanh=(TextView)view.findViewById(R.id.tvCongsuatKhacMayLanh);
        edtCongsuatKhacMayLanh=(EditText)view.findViewById(R.id.edtCongsuatKhacMayLanh);
        tvLoaiKhacMayLanh=(TextView)view.findViewById(R.id.tvLoaiKhacMayLanh);
        edtLoaiKhacMayLanh=(EditText)view.findViewById(R.id.edtLoaiKhacMayLanh);
        //May Giat
        tvChonHangMayGiat=(TextView) view.findViewById(R.id.tvChonHangMayGiat);
        tvChonLoaiMayGiat=(TextView) view.findViewById(R.id.tvChonLoaiMayGiat) ;
        tvChonKhoiLuongGiatMayGiat=(TextView)view.findViewById(R.id.tvChonKhoiLuongGiatMayGiat) ;
        tvHangKhacMayGiat=(TextView)view.findViewById(R.id.tvHangKhacMayGiat);
        edtHangKhacMayGiat=(EditText)view.findViewById(R.id.edtHangKhacMayGiat);
        tvChonKhoiLuongKhacMayGiat=(TextView)view.findViewById(R.id.tvChonKhoiLuongKhacMayGiat);
        edtChonKhoiLuongKhacMayGiat=(EditText)view.findViewById(R.id.edtChonKhoiLuongKhacMayGiat);
        Rd_Congnghe_MayGiat=(RadioGroup)  view.findViewById(R.id.Rd_Congnghe_MayGiat);
        Rbtn_Congnghe_MayGiat_Co=(RadioButton)  view.findViewById(R.id.Rbtn_Congnghe_MayGiat_Co);
        Rbtn_Congnghe_MayGiat_Khong=(RadioButton)  view.findViewById(R.id.Rbtn_Congnghe_MayGiat_Khong);

        // Lo nuong
        tvChonHangLoNuong=(TextView) view.findViewById(R.id.tvChonHangLoNuong);
        tvChonLoaiLoNuong=(TextView) view.findViewById(R.id.tvChonLoaiLoNuong) ;
        tvChonDungTichLoNuong=(TextView)view.findViewById(R.id.tvChonDungTichLoNuong) ;
        tvChonHangKhacLoNuong=(TextView)view.findViewById(R.id.tvChonHangKhacLoNuong);
        edtChonHangKhacLoNuong=(EditText) view.findViewById(R.id.edtChonHangKhacLoNuong);
        //May Rua Chen

        tvChonHangMayRuaChen=(TextView)view.findViewById(R.id.tvChonHangMayRuaChen) ;
        tvChonLoaiMayRuaChen=(TextView)view.findViewById(R.id.tvChonLoaiMayRuaChen) ;
        tvChonDungTichMayRuaChen=(TextView)view.findViewById(R.id.tvChonDungTichMayRuaChen) ;

        List<String> list = new ArrayList<>();
        list.add(0,"Chọn thiết bị");
        list.add("Đèn");
        list.add("Quạt");
        list.add("Ti Vi");
        list.add("Nồi cơm");
        list.add("Tủ lạnh");
        list.add("Máy lạnh");
        list.add("Máy giặt");
        list.add("Lò nướng");
        list.add("Máy rửa chén");

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        edtInputThietBi.setAdapter(adapter);
        edtInputThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(getActivity(), edtInputThietBi.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                switch (edtInputThietBi.getSelectedItemPosition()){
                    case 0:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        break;
                    case 1:
                        ly_inputDen.setVisibility(View.VISIBLE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        tvChonHangDen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("den").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1200);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangDen.setText(adapter2.getItem(i));
                                                if(tvChonHangDen.getText().toString().equals("Khác")){
                                                    tvHangKhacDen.setVisibility(View.VISIBLE);
                                                    edtHangKhacDen.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacDen.setVisibility(View.GONE);
                                                    edtHangKhacDen.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvChonLoaiDen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("den").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1200);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiDen.setText(adapter2.getItem(i));
                                                if(tvChonLoaiDen.getText().toString().equals("Khác")){
                                                    tvLoaiKhacDen.setVisibility(View.VISIBLE);
                                                    edtLoaiKhacDen.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvLoaiKhacDen.setVisibility(View.GONE);
                                                    edtLoaiKhacDen.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closenoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closenoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        //GET BRAND OF QUAT FROM DB AND SET IT TO TEXT VIEW IF CHOOSE


                        break;

                    case 2:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.VISIBLE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        tvHangQuat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("quat").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1400);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvHangQuat.setText(adapter2.getItem(i));
                                                if(tvHangQuat.getText().toString().equals("Khác")){
                                                    tvHangKhacQuat.setVisibility(View.VISIBLE);
                                                    edtHangKhacQuat.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacQuat.setVisibility(View.GONE);
                                                    edtHangKhacQuat.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvInputChonLoaiQuat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("quat").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvInputChonLoaiQuat.setText(adapter2.getItem(i));
                                                if(tvInputChonLoaiQuat.getText().toString().equals("Khác")){
                                                    tvLoaiKhacQuat.setVisibility(View.VISIBLE);
                                                    edtLoaiKhacQuat.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvLoaiKhacQuat.setVisibility(View.GONE);
                                                    edtLoaiKhacQuat.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });

                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });


                        break;

                    case 3:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.VISIBLE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);

                        tvChonHangTivi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tivi").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1400);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangTivi.setText(adapter2.getItem(i));
                                                if(tvChonHangTivi.getText().toString().equals("Khác")){
                                                    tvHangKhacTivi.setVisibility(View.VISIBLE);
                                                    edtHangKhacKhacTivi.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacTivi.setVisibility(View.GONE);
                                                    edtHangKhacKhacTivi.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvChonLoaiTivi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tivi").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiTivi.setText(adapter2.getItem(i));
                                                if(tvChonLoaiTivi.getText().toString().equals("Khác")){
                                                    tvLoaiKhacQuat.setVisibility(View.VISIBLE);
                                                    edtLoaiKhacTivi.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvLoaiKhacQuat.setVisibility(View.GONE);
                                                    edtLoaiKhacTivi.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonKichThuocTivi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tivi").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1200);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonKichThuocTivi.setText(adapter2.getItem(i));
                                                dialog.dismiss();
                                            }
                                        });

                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvNamRaMat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tivi").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("5").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvNamRaMat.setText(adapter2.getItem(i));
                                                if(tvNamRaMat.getText().toString().equals("Khác")){
                                                    tvNamRaMatKhacTivi.setVisibility(View.VISIBLE);
                                                    edtNamRaMatKhacTivi.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvNamRaMatKhacTivi.setVisibility(View.GONE);
                                                    edtNamRaMatKhacTivi.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        break;

                    case 4:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.VISIBLE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        tvChonHangNoiCom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("noicom").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1200);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangNoiCom.setText(adapter2.getItem(i));
                                                if(tvChonHangNoiCom.getText().toString().equals("Khác")){
                                                    tvChonHangKhacNoiCom.setVisibility(View.VISIBLE);
                                                    edtChonHangKhacNoiCom.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvChonHangKhacNoiCom.setVisibility(View.GONE);
                                                    edtChonHangKhacNoiCom.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvChonLoaiNoiCom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("noicom").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiNoiCom.setText(adapter2.getItem(i));
                                                if(tvChonLoaiNoiCom.getText().toString().equals("Khác")){
                                                    tvLoaiKhacNoiCom.setVisibility(View.VISIBLE);
                                                    edtLoaiKhacNoiCom.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvLoaiKhacNoiCom.setVisibility(View.GONE);
                                                    edtLoaiKhacNoiCom.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonDungTichNoiCom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("noicom").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonDungTichNoiCom.setText(adapter2.getItem(i));
                                                if(tvChonDungTichNoiCom.getText().toString().equals("Khác")){
                                                    tvChonDungTichKhacNoiCom.setVisibility(View.VISIBLE);
                                                    edtChonDungTichKhacNoiCom.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvChonDungTichKhacNoiCom.setVisibility(View.GONE);
                                                    edtChonDungTichKhacNoiCom.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        //GET BRAND OF MAY GIAT AND SET IT TO TEXTVIEW

                        break;

                    case 5:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.VISIBLE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);

                        tvChonHangTuLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tulanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1400);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangTuLanh.setText(adapter2.getItem(i));
                                                if(tvChonHangTuLanh.getText().toString().equals("Khác")){
                                                    tvHangKhacTuLanh.setVisibility(View.VISIBLE);
                                                    edtHangKhacTuLanh.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacTuLanh.setVisibility(View.GONE);
                                                    edtHangKhacTuLanh.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvChonLoaiTuLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tulanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiTuLanh.setText(adapter2.getItem(i));
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonTheTichTuLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("tulanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonTheTichTuLanh.setText(adapter2.getItem(i));
                                                if(tvChonTheTichTuLanh.getText().toString().equals("Khác")){
                                                    tvChonTheTichKhacTuLanh.setVisibility(View.VISIBLE);
                                                    edtChonTheTichKhacTuLanh.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvChonTheTichKhacTuLanh.setVisibility(View.GONE);
                                                    edtChonTheTichKhacTuLanh.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        break;

                    case 6:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.VISIBLE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);

                        tvChonHangMayLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maylanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1400);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangMayLanh.setText(adapter2.getItem(i));
                                                if(tvChonHangMayLanh.getText().toString().equals("Khác")){
                                                    tvHangKhacMayLanh.setVisibility(View.VISIBLE);
                                                    edtChonHangKhacMayLanh.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacMayLanh.setVisibility(View.GONE);
                                                    edtChonHangKhacMayLanh.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF QUAT AND SET IT TO TEXT VIEW
                        tvChonLoaiMayLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maylanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiMayLanh.setText(adapter2.getItem(i));
                                                if(tvChonLoaiMayLanh.getText().toString().equals("Khác")){
                                                    tvLoaiKhacMayLanh.setVisibility(View.VISIBLE);
                                                    edtLoaiKhacMayLanh.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvLoaiKhacMayLanh.setVisibility(View.GONE);
                                                    edtLoaiKhacMayLanh.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonCongSuatMayLanh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maylanh").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonCongSuatMayLanh.setText(adapter2.getItem(i));
                                                if(tvChonCongSuatMayLanh.getText().toString().equals("Khác")){
                                                    tvCongsuatKhacMayLanh.setVisibility(View.VISIBLE);
                                                    edtCongsuatKhacMayLanh.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvCongsuatKhacMayLanh.setVisibility(View.GONE);
                                                    edtCongsuatKhacMayLanh.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        break;

                    case 7:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.VISIBLE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        tvChonHangMayGiat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maygiat").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1400);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangMayGiat.setText(adapter2.getItem(i));
                                                if(tvChonHangMayGiat.getText().toString().equals("Khác")){
                                                    tvHangKhacMayGiat.setVisibility(View.VISIBLE);
                                                    edtHangKhacMayGiat.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvHangKhacMayGiat.setVisibility(View.GONE);
                                                    edtHangKhacMayGiat.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        //GET KIND OF MAY GIAT FROM DB AND SET TO TEXTVIEW
                        tvChonLoaiMayGiat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maygiat").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,900);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiMayGiat.setText(adapter2.getItem(i));
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        Rd_Congnghe_MayGiat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                switch (i){
                                    case R.id.Rbtn_Congnghe_MayGiat_Co:
                                        congNgheInverter="";
                                        congNgheInverter= congNgheInverter + Rbtn_Congnghe_MayGiat_Co.getText();
                                        break;
                                    case R.id.Rbtn_Congnghe_MayGiat_Khong:
                                        congNgheInverter="";
                                        congNgheInverter= ""+ Rbtn_Congnghe_MayGiat_Khong.getText();
                                        break;
                                }
                            }
                        });
                        tvChonKhoiLuongGiatMayGiat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("maygiat").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonKhoiLuongGiatMayGiat.setText(adapter2.getItem(i));
                                                if(tvChonKhoiLuongGiatMayGiat.getText().toString().equals("Khác")){
                                                    tvChonKhoiLuongKhacMayGiat.setVisibility(View.VISIBLE);
                                                    edtChonKhoiLuongKhacMayGiat.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvChonKhoiLuongKhacMayGiat.setVisibility(View.GONE);
                                                    edtChonKhoiLuongKhacMayGiat.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        break;
                    case 8:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.VISIBLE);
                        ly_inputMayRuaChen.setVisibility(View.GONE);
                        tvChonHangLoNuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("lonuong").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("2").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();
                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);
                                        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                adapter2.getFilter().filter(charSequence);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonHangLoNuong.setText(adapter2.getItem(i));
                                                if(tvChonHangLoNuong.getText().toString().equals("Khác")){
                                                    tvChonHangKhacLoNuong.setVisibility(View.VISIBLE);
                                                    edtChonHangKhacLoNuong.setVisibility(View.VISIBLE);
                                                }else{
                                                    tvChonHangKhacLoNuong.setVisibility(View.GONE);
                                                    edtChonHangKhacLoNuong.setVisibility(View.GONE);
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeSearchableSpinner=dialog.findViewById(R.id.closeSearchableSpinner);
                                        closeSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonLoaiLoNuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("lonuong").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("3").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,900);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonLoaiLoNuong.setText(adapter2.getItem(i));
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        tvChonDungTichLoNuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                                mDatabase.child("lonuong").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> list22 = new ArrayList<>();
                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                            String areaName = areaSnapshot.child("4").getValue(String.class);
                                            if(areaName != null){
                                                list22.add(areaName);
                                            }
                                        }
                                        List<String> listTest= new ArrayList<String>();
                                        for(String test: list22){
                                            listTest.add(test);
                                        }

                                        //intitial dialog
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.dialog_nosearchable_spinner);
                                        dialog.getWindow().setLayout(1000,1100);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listTest);
                                        listView.setAdapter(adapter2);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                tvChonDungTichLoNuong.setText(adapter2.getItem(i));
                                                dialog.dismiss();
                                            }
                                        });
                                        ImageView closeNoSearchableSpinner=dialog.findViewById(R.id.closeNoSearchableSpinner);
                                        closeNoSearchableSpinner.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        break;
                    case 9:
                        ly_inputDen.setVisibility(View.GONE);
                        ly_inputQuat.setVisibility(View.GONE);
                        ly_inputTivi.setVisibility(View.GONE);
                        ly_inputNoiCom.setVisibility(View.GONE);
                        ly_inputTuLanh.setVisibility(View.GONE);
                        ly_inputMayLanh.setVisibility(View.GONE);
                        ly_inputMayGiat.setVisibility(View.GONE);
                        ly_inputLoNuong.setVisibility(View.GONE);
                        ly_inputMayRuaChen.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


            //Depend on the input device, that create a function goToDescriseProblem + device
            btnNextToDecriseProblem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtInputThietBi.getSelectedItem().toString().trim().equals("Đèn")){
                        if(tvChonHangDen.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng đèn",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiDen.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại đèn",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemDen(edtInputThietBi.getSelectedItem().toString(), tvChonHangDen.getText().toString(), tvChonLoaiDen.getText().toString());
                    }else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Quạt")) {
                        if(tvHangQuat.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng quạt",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvInputChonLoaiQuat.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại loại",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemQuat(edtInputThietBi.getSelectedItem().toString(), tvHangQuat.getText().toString(), tvInputChonLoaiQuat.getText().toString());
                    }else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Ti Vi")) {
                        if(tvChonHangTivi.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Ti Vi",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiTivi.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Tivi",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemTivi(edtInputThietBi.getSelectedItem().toString(), tvChonHangTivi.getText().toString(), tvChonLoaiTivi.getText().toString(),tvChonKichThuocTivi.getText().toString().trim(), tvNamRaMat.getText().toString().trim());
                    }else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Nồi cơm")) {
                        if(tvChonHangNoiCom.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Nồi Cơm ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiNoiCom.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Nồi Cơm",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemNoiCom(edtInputThietBi.getSelectedItem().toString(), tvChonHangNoiCom.getText().toString(), tvChonLoaiNoiCom.getText().toString(),tvChonDungTichNoiCom.getText().toString());
                    }else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Tủ lạnh")) {
                        if(tvChonHangTuLanh.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Tủ lạnh",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiTuLanh.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Tủ lạnh",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemTuLanh(edtInputThietBi.getSelectedItem().toString(), tvChonHangTuLanh.getText().toString(), tvChonLoaiTuLanh.getText().toString(),tvChonTheTichTuLanh.getText().toString());
                    }else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Máy lạnh")) {
                        if(tvChonHangMayLanh.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Máy lạnh",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiMayLanh.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Máy lạnh",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemMayLanh(edtInputThietBi.getSelectedItem().toString(), tvChonHangMayLanh.getText().toString(), tvChonLoaiMayLanh.getText().toString(),tvChonCongSuatMayLanh.getText().toString());
                    } else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Máy giặt")) {
                        if(tvChonHangMayGiat.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Máy giặt",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiMayGiat.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Máy giặt",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemMayGiat(edtInputThietBi.getSelectedItem().toString(), tvChonHangMayGiat.getText().toString(), tvChonLoaiMayGiat.getText().toString(),tvChonKhoiLuongGiatMayGiat.getText().toString(),congNgheInverter);
                    } else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Lò nướng")) {
                        if(tvChonHangLoNuong.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Lò nướng",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiLoNuong.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Lò nướng",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemLoNuong(edtInputThietBi.getSelectedItem().toString(), tvChonHangLoNuong.getText().toString(), tvChonLoaiLoNuong.getText().toString(),tvChonDungTichLoNuong.getText().toString());
                    }
                    else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Máy rửa chén")) {
                        if(tvChonHangMayRuaChen.getText().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn hãng Máy rửa chén",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tvChonLoaiMayRuaChen.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Vui lòng chọn loại Máy rửa chén",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goToDescriseProblemMayRuaChen(edtInputThietBi.getSelectedItem().toString(), tvChonHangMayRuaChen.getText().toString(), tvChonLoaiMayRuaChen.getText().toString(),tvChonDungTichMayRuaChen.getText().toString());
                    }
                    else {
                        Toast.makeText(getActivity(), "Vui lòng chọn thiết bị",Toast.LENGTH_SHORT).show();
                    }
                }

            });




        // Inflate the layout for this fragment
        return view;
    }

    public void goToDescriseProblemDen(String thietbi, String hang,String loai){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        //intent.putExtra("price",price);
        startActivity(intent);
    }
    public void goToDescriseProblemQuat(String thietbi, String hang,String loai){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        //intent.putExtra("price",price);
        startActivity(intent);
    }
    public void goToDescriseProblemTivi(String thietbi, String hang,String loai,String kichThuoc,String namRaMat){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("kichthuoc",kichThuoc);
        intent.putExtra("namramat",namRaMat);
        startActivity(intent);
    }
    public void goToDescriseProblemNoiCom(String thietbi, String hang,String loai,String dungtich){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("dungtich",dungtich);
        startActivity(intent);
    }
    public void goToDescriseProblemTuLanh(String thietbi, String hang,String loai,String thetich){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("thetich",thetich);
        startActivity(intent);
    }
    public void goToDescriseProblemMayLanh(String thietbi, String hang,String loai,String congsuat){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("congsuat",congsuat);
        startActivity(intent);
    }
    public void goToDescriseProblemMayGiat(String thietbi, String hang,String loai,String khoiluong, String congngheinverter){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("congngheinverter",congngheinverter);
        intent.putExtra("khoiluong",khoiluong);
        startActivity(intent);
    }
    public void goToDescriseProblemLoNuong(String thietbi, String hang,String loai,String dungtich){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("dungtich",dungtich);
        startActivity(intent);
    }
    public void goToDescriseProblemMayRuaChen(String thietbi, String hang,String loai,String dungtich){
        Intent intent = new Intent(getActivity(),DecriseProblemActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("hang",hang);
        intent.putExtra("loai",loai);
        intent.putExtra("dungtich",dungtich);
        startActivity(intent);
    }
}