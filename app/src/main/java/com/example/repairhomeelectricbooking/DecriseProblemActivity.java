package com.example.repairhomeelectricbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repairhomeelectricbooking.dto.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DecriseProblemActivity extends AppCompatActivity {
    private Button btnBookingRepair;
    private TextView  tvInputThietBi,tvInputThietBiHangDaChon,tvInputThietBiLoaiDaChon;
    String strThietBi,strHang,strLoai;

    private TextView tvVanDeKhac,tvMotaVande,tvMotaVandechitiet;
    private  EditText edtMoTaVanDeKhac,edtVitri,edtInputVanDeChitiet;
    private boolean[] selectedProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrise_problem);
        tvInputThietBi=(TextView) findViewById(R.id.edtInputThietBiDaChon);
        tvInputThietBiHangDaChon=(TextView)findViewById(R.id.tvInputThietBiHangDaChon);
        tvInputThietBiLoaiDaChon=(TextView) findViewById(R.id.tvInputThietBiLoaiDaChon);
        btnBookingRepair=(Button) findViewById(R.id.btn_BookingRepair);
        tvMotaVande = (TextView) findViewById(R.id.spinnerMoTaVanDe);
        tvMotaVandechitiet=(TextView) findViewById(R.id.tvMoTaVanDe);
        edtInputVanDeChitiet=(EditText) findViewById(R.id.edtInputVanDe);
        edtVitri=(EditText) findViewById(R.id.edtAddressUser);

        //TextView=(EditText) view.findViewById(R.id)
        //getUserInfo();
        getDataIntent();
        tvInputThietBi.setText(strThietBi);
        tvInputThietBiHangDaChon.setText(strHang);
        tvInputThietBiLoaiDaChon.setText(strLoai);
        if(tvInputThietBi.getText().toString().equals("Máy giặt")){
                DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("item");
                mDatabase3.child("maygiat").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> areas = new ArrayList<String>();
                        List<Integer> problemList = new ArrayList<>();
                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                            String areaName = areaSnapshot.child("1").getValue(String.class);
                            if(areaName!=null){
                                areas.add(areaName);
                            }

                        }

                        String[] object= new String[areas.size()];
                        for(int i=0;i<areas.size();i++){
                            object[i]=areas.get(i);
                        }
                        selectedProblem= new boolean[areas.size()];
                        //tvMotaVande.setHint("Chọn vấn đề");
                        tvMotaVande.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //intitial dialog
                                AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
                                builder.setTitle("Lựa chọn vấn đề");
                                //set dialog non cancelable
                                builder.setCancelable(false);
                                builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                        if(b){
                                            //when checkbox selected
                                            //add position in day problem
                                            problemList.add(i);
                                            Collections.sort(problemList);
                                        }else{
                                            //when checkbox unselected
                                            //remove position from day list
                                            //
                                            for(int  j=0  ; j<problemList.size(); j++){
                                                if(problemList.get(j) == i){
                                                    problemList.remove(j);
                                                    break;
                                                }
                                            }
                                            // problemList.remove(i);

                                        }
                                    }
                                });
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Initialize string builder
                                        StringBuilder stringBuilder= new StringBuilder();
                                        //use for loop
                                        for(int j=0;j<problemList.size();j++){
                                            stringBuilder.append(object[problemList.get(j)]);
                                            //check condition
                                            if(j!= problemList.size()-1){
                                                stringBuilder.append(",");
                                            }
                                            if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
                                                tvVanDeKhac.setVisibility(view.VISIBLE);
                                                edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                                tvMotaVandechitiet.setVisibility(view.VISIBLE);
                                                edtInputVanDeChitiet.setVisibility(view.VISIBLE);
                                            } else{
                                                tvVanDeKhac.setVisibility(view.GONE);
                                                edtMoTaVanDeKhac.setVisibility(view.GONE);
                                                tvMotaVandechitiet.setVisibility(view.GONE);
                                                edtInputVanDeChitiet.setVisibility(view.GONE);
                                            }
                                        }
                                        if(stringBuilder.toString().length() > 100){
                                            tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
                                        }else{
                                            tvMotaVande.setText(stringBuilder.toString());
                                        }

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Use for loop
                                        for(int j=0;j<selectedProblem.length;j++){
                                            //remove all selection
                                            selectedProblem[j]=false;
                                            //clear prolem list
                                            problemList.clear();
                                            //clear text view value
                                            tvMotaVande.setText("");
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                            tvMotaVandechitiet.setVisibility(view.GONE);
                                            edtInputVanDeChitiet.setVisibility(view.GONE);
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }
        btnBookingRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String strThietBi = edtInputThietBi.getSelectedItem().toString();
//                String strVanDe = tvMotaVande.getText().toString();
//                String StrLocation=edtVitri.getText().toString();
//                //String strFee = edtFee.getText().toString();
                gotoSearchAnimation(strThietBi,strHang,strLoai);
            }
        });
        tvVanDeKhac = (TextView) findViewById(R.id.tvVanDeKhac);
        edtMoTaVanDeKhac = (EditText) findViewById(R.id.edtInputVanDeKhac);
//        tvVanDeKhac.setVisibility(View.GONE);
//        edtMoTaVanDeKhac.setVisibility(View.GONE);
//        List<String> list = new ArrayList<>();
//        list.add(0,"Chọn thiết bị");
//        list.add("Quạt");
//        list.add("Đèn");
//        list.add("Máy lạnh");
//        list.add("Máy giặt");
//        list.add("Nồi cơm");
//        list.add("Lò nướng");
//        list.add("Máy rửa chén");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_spinner_item,list);
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//
//        edtInputThietBi.setAdapter(adapter);
//        edtInputThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                // Toast.makeText(getActivity(), edtInputThietBi.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                switch (edtInputThietBi.getSelectedItem().toString()){
//                    case 0:
//                        break;
//                    case 1:
//                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase.child("quat").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(getApplicationContext());
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
////                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
////                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
////                                spinnerMoTaVanDe.setAdapter(areasAdapter);
////                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                                    @Override
////                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
////                                            tvVanDeKhac.setVisibility(view.VISIBLE);
////                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
////                                        } else{
////                                            tvVanDeKhac.setVisibility(view.GONE);
////                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
////                                        }
////                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
////                                    }
////
////                                    @Override
////                                    public void onNothingSelected(AdapterView<?> adapterView) {
////
////                                    }
////                                });
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;
//
//                    case 2:
//                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase1.child("den").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;
//
//                    case 3:
//                        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase2.child("maylanh").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;

//                    case "Máy giặt":
//                        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase3.child("maygiat").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    if(areaName!=null){
//                                        areas.add(areaName);
//                                    }
//
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;

//                    case 5:
//                        DatabaseReference mDatabase4 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase4.child("noicom").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;
//
//                    case 6:
//                        DatabaseReference mDatabase5 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase5.child("lonuong").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        break;
//


//                    case 7:
//                        DatabaseReference mDatabase6 = FirebaseDatabase.getInstance().getReference("item");
//                        mDatabase6.child("mayruachen").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                final List<String> areas = new ArrayList<String>();
//                                List<Integer> problemList = new ArrayList<>();
//                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                    String areaName = areaSnapshot.child("1").getValue(String.class);
//                                    areas.add(areaName);
//                                }
//
//                                String[] object= new String[areas.size()];
//                                for(int i=0;i<areas.size();i++){
//                                    object[i]=areas.get(i);
//                                }
//                                selectedProblem= new boolean[areas.size()];
//                                //tvMotaVande.setHint("Chọn vấn đề");
//                                tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        //intitial dialog
//                                        AlertDialog.Builder builder= new AlertDialog.Builder(DecriseProblemActivity.this);
//                                        builder.setTitle("Lựa chọn vấn đề");
//                                        //set dialog non cancelable
//                                        builder.setCancelable(false);
//                                        builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                                                if(b){
//                                                    //when checkbox selected
//                                                    //add position in day problem
//                                                    problemList.add(i);
//                                                    Collections.sort(problemList);
//                                                }else{
//                                                    //when checkbox unselected
//                                                    //remove position from day list
//                                                    //
//                                                    for(int  j=0  ; j<problemList.size(); j++){
//                                                        if(problemList.get(j) == i){
//                                                            problemList.remove(j);
//                                                            break;
//                                                        }
//                                                    }
//                                                    // problemList.remove(i);
//
//                                                }
//                                            }
//                                        });
//                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Initialize string builder
//                                                StringBuilder stringBuilder= new StringBuilder();
//                                                //use for loop
//                                                for(int j=0;j<problemList.size();j++){
//                                                    stringBuilder.append(object[problemList.get(j)]);
//                                                    //check condition
//                                                    if(j!= problemList.size()-1){
//                                                        stringBuilder.append(",");
//                                                    }
//                                                    if(object[problemList.get(problemList.size()-1)].equals("Vấn đề khác...")){
//                                                        tvVanDeKhac.setVisibility(view.VISIBLE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                                                        tvMotaVandechitiet.setVisibility(view.VISIBLE);
//                                                        edtInputVanDeChitiet.setVisibility(view.VISIBLE);
//                                                    } else{
//                                                        tvVanDeKhac.setVisibility(view.GONE);
//                                                        edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                        tvMotaVandechitiet.setVisibility(view.GONE);
//                                                        edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                    }
//                                                }
//                                                if(stringBuilder.toString().length() > 100){
//                                                    tvMotaVande.setText(stringBuilder.toString().substring(0,80)+ "...");
//                                                }else{
//                                                    tvMotaVande.setText(stringBuilder.toString());
//                                                }
//
//
//                                            }
//                                        });
//                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.dismiss();
//                                            }
//                                        });
//                                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //Use for loop
//                                                for(int j=0;j<selectedProblem.length;j++){
//                                                    //remove all selection
//                                                    selectedProblem[j]=false;
//                                                    //clear prolem list
//                                                    problemList.clear();
//                                                    //clear text view value
//                                                    tvMotaVande.setText("");
//                                                    tvVanDeKhac.setVisibility(view.GONE);
//                                                    edtMoTaVanDeKhac.setVisibility(view.GONE);
//                                                    tvMotaVandechitiet.setVisibility(view.GONE);
//                                                    edtInputVanDeChitiet.setVisibility(view.GONE);
//                                                }
//                                            }
//                                        });
//                                        builder.show();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });

                        //get and search Brand of quat
//                        tvMotaVande.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
//                                mDatabase.child("quat").addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        final List<String> list22 = new ArrayList<>();
//                                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                                            String areaName = areaSnapshot.child("2").getValue(String.class);
//                                            if(areaName != null){
//                                                list22.add(areaName);
//                                            }
//                                        }
//                                        List<String> listTest= new ArrayList<String>();
//                                        for(String test: list22){
//                                            listTest.add(test);
//                                        }
//
//                                        //intitial dialog
//                                        Dialog dialog = new Dialog(getApplicationContext());
//                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
//                                        dialog.getWindow().setLayout(750,1100);
////                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                        dialog.show();
//                                        EditText editText = dialog.findViewById(R.id.edt_dialog_searchable_spinner);
//                                        ListView listView = dialog.findViewById(R.id.list_view_dialog_searchable_spinner);
//                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listTest);
//                                            listView.setAdapter(adapter2);
//                                            editText.addTextChangedListener(new TextWatcher() {
//                                                @Override
//                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                                }
//
//                                                @Override
//                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                                                    adapter2.getFilter().filter(charSequence);
//                                                }
//
//                                                @Override
//                                                public void afterTextChanged(Editable editable) {
//
//                                                }
//                                            });
//
//                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                                @Override
//                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                                    tvMotaVande.setText(adapter2.getItem(i));
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        });
//                        break;
//             }
//          }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        // Inflate the layout for this fragment
    }
    private void getDataIntent()
    {
        strThietBi = getIntent().getStringExtra("thietbi");
        strHang = getIntent().getStringExtra("hang");
        strLoai = getIntent().getStringExtra("loai");
    }
    private List<Item> getListItem(){
        List<Item> list = new ArrayList<>();

        list.add(new Item(R.drawable.maygiat,"Máy giặt"));
        list.add(new Item(R.drawable.tivi,"Tivi"));
        list.add(new Item(R.drawable.den,"Đèn"));
        list.add(new Item(R.drawable.loa,"Loa"));
        list.add(new Item(R.drawable.maylanh,"Máy lạnh"));
        list.add(new Item(R.drawable.lovisong,"Lò vi sóng"));
        list.add(new Item(R.drawable.quat,"Quạt"));
        list.add(new Item(R.drawable.binhnaunuoc,"Bình nấu nước"));
        list.add(new Item(R.drawable.maynonglanh,"Máy nóng lạnh"));
        list.add(new Item(R.drawable.ocam,"Ổ cắm"));
        return list;
    }
    public void gotoSearchAnimation(String thietbi, String problem,String strLocation){
        Intent intent = new Intent(DecriseProblemActivity.this,AnimationSearchActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("problem",problem);
        intent.putExtra("locationUser",strLocation);
        //intent.putExtra("price",price);
        startActivity(intent);
    }

}
