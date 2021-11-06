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
    private TextView  tvInputThietBi;
    String strThietBi,strHang,strLoai,strDungTich= "",strTheTich= "",strKhoiLuong= "",strCongXuat= "",strKichThuoc= "",strNamSanXuat= "",strInverter= "";
    private  EditText edtAddressUser;
    private TextView tvVanDeKhac,tvMotaVande,tvMotaVandechitiet;
    private  EditText edtMoTaVanDeKhac,edtVitri,edtInputVanDeChitiet;
    private boolean[] selectedProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrise_problem);
        tvInputThietBi=(TextView) findViewById(R.id.edtInputThietBiDaChon);
        btnBookingRepair=(Button) findViewById(R.id.btn_BookingRepair);
        tvMotaVande = (TextView) findViewById(R.id.spinnerMoTaVanDe);
        tvMotaVandechitiet=(TextView) findViewById(R.id.tvMoTaVanDeThemChiTiet);
        edtInputVanDeChitiet=(EditText) findViewById(R.id.edtInputVanDeChitiet);
        edtVitri=(EditText) findViewById(R.id.edtAddressUser);
        tvVanDeKhac = (TextView) findViewById(R.id.tvVanDeKhac);
        edtMoTaVanDeKhac = (EditText) findViewById(R.id.edtInputVanDeKhac);
        //TextView=(EditText) view.findViewById(R.id)
        //getUserInfo();
        getDataIntent();
        String inputThietBi= strThietBi+ "-" + strHang + "\n" + "Loại: "+ strLoai  ;

        if(!strDungTich.equals("") ){
            strDungTich = "Dung tích: " + strDungTich ;
            inputThietBi += "\n" +strDungTich;
        }
        if(!strTheTich.equals("")  ){
            strTheTich = "Thể tích: " + strTheTich;
            inputThietBi += "\n" + strTheTich;
        }
        if(!strInverter.equals("")  ){
            strInverter = "Công nghệ Inverter: " + strInverter;
            inputThietBi += "\n" + strInverter;
        }
        if(!strKhoiLuong.equals("") ){
            strKhoiLuong = "Khối lượng: " + strKhoiLuong;
            inputThietBi += "\n" + strKhoiLuong;
        }
        if(!strCongXuat.equals("")  ){
            strCongXuat = "Công xuất: " + strCongXuat;
            inputThietBi += "\n" + strCongXuat;
        }
        if(!strKichThuoc.equals("")  ){
            strKichThuoc = "Kích thước: " + strKichThuoc;
            inputThietBi += "\n" + strKichThuoc;
        }
        if(!strNamSanXuat.equals("") ){
            strNamSanXuat = "Năm sản xuất: " + strNamSanXuat;
            inputThietBi += "\n" + strNamSanXuat;
        }

        tvInputThietBi.setText(inputThietBi);
        if(tvInputThietBi.getText().toString().contains("Máy giặt")){
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

        }else if(tvInputThietBi.getText().toString().contains("Máy lạnh")){
            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase2.child("maylanh").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Quạt")){
            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase2.child("quat").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Đèn")){
            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase1.child("den").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Nồi cơm")){
            DatabaseReference mDatabase4 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase4.child("noicom").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Lò nướng")){
            DatabaseReference mDatabase5 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase5.child("lonuong").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Máy rửa chén")){
            DatabaseReference mDatabase6 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase6.child("mayruachen").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Tủ lạnh")){
            DatabaseReference mDatabase6 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase6.child("tulanh").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Ti vi")){
            DatabaseReference mDatabase6 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase6.child("tivi").addValueEventListener(new ValueEventListener() {
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
        }else if(tvInputThietBi.getText().toString().contains("Ti Vi")) {
            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("item");
            mDatabase2.child("tivi").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> areas = new ArrayList<String>();
                    List<Integer> problemList = new ArrayList<>();
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        String areaName = areaSnapshot.child("1").getValue(String.class);
                        if (areaName != null) {
                            areas.add(areaName);
                        }
                    }

                    String[] object = new String[areas.size()];
                    for (int i = 0; i < areas.size(); i++) {
                        object[i] = areas.get(i);
                    }
                    selectedProblem = new boolean[areas.size()];
                    //tvMotaVande.setHint("Chọn vấn đề");
                    tvMotaVande.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //intitial dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(DecriseProblemActivity.this);
                            builder.setTitle("Lựa chọn vấn đề");
                            //set dialog non cancelable
                            builder.setCancelable(false);
                            builder.setMultiChoiceItems(object, selectedProblem, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                    if (b) {
                                        //when checkbox selected
                                        //add position in day problem
                                        problemList.add(i);
                                        Collections.sort(problemList);
                                    } else {
                                        //when checkbox unselected
                                        //remove position from day list
                                        //
                                        for (int j = 0; j < problemList.size(); j++) {
                                            if (problemList.get(j) == i) {
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
                                    StringBuilder stringBuilder = new StringBuilder();
                                    //use for loop
                                    for (int j = 0; j < problemList.size(); j++) {
                                        stringBuilder.append(object[problemList.get(j)]);
                                        //check condition
                                        if (j != problemList.size() - 1) {
                                            stringBuilder.append(",");
                                        }
                                        if (object[problemList.get(problemList.size() - 1)].equals("Vấn đề khác...")) {
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                            tvMotaVandechitiet.setVisibility(view.VISIBLE);
                                            edtInputVanDeChitiet.setVisibility(view.VISIBLE);
                                        } else {
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                            tvMotaVandechitiet.setVisibility(view.GONE);
                                            edtInputVanDeChitiet.setVisibility(view.GONE);
                                        }
                                    }
                                    if (stringBuilder.toString().length() > 100) {
                                        tvMotaVande.setText(stringBuilder.toString().substring(0, 80) + "...");
                                    } else {
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
                                    for (int j = 0; j < selectedProblem.length; j++) {
                                        //remove all selection
                                        selectedProblem[j] = false;
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
                if(tvMotaVande.getText().toString().equals("")){
                    Toast.makeText(DecriseProblemActivity.this,"Xin vui lòng chọn vấn đề",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println("test edt van de khac: " + edtMoTaVanDeKhac.getText().toString());
                if(!edtMoTaVanDeKhac.getText().toString().equals("")){
                    if(!edtInputVanDeChitiet.getText().toString().equals("")){
                        gotoSearchAnimation2(tvInputThietBi.getText().toString(),tvMotaVande.getText().toString()+ ":" + edtMoTaVanDeKhac.getText().toString() + "-" + edtInputVanDeChitiet.getText().toString(),edtVitri.getText().toString());
                        return;
                    }
                    gotoSearchAnimation2(tvInputThietBi.getText().toString(),tvMotaVande.getText().toString()+ ":" + edtMoTaVanDeKhac.getText().toString(),edtVitri.getText().toString());
                    return;
                }
                gotoSearchAnimation(tvInputThietBi.getText().toString(),tvMotaVande.getText().toString(),edtVitri.getText().toString());
            }
        });

    }
    private void getDataIntent()
    {
        strThietBi = getIntent().getStringExtra("thietbi");
        strHang = getIntent().getStringExtra("hang");
        strLoai = getIntent().getStringExtra("loai");
        strDungTich=getIntent().getStringExtra("dungtich");
        System.out.println("dung tich:12121"+ strDungTich+ "dsa");
        strTheTich=getIntent().getStringExtra("thetich");
        strKhoiLuong=getIntent().getStringExtra("khoiluong");
        strCongXuat=getIntent().getStringExtra("congsuat");
        strKichThuoc=getIntent().getStringExtra("kichthuoc");
        System.out.println("Kích thước:12121"+ strKichThuoc+ "dsa");
        strNamSanXuat=getIntent().getStringExtra("namramat");
        strInverter=getIntent().getStringExtra("congngheinverter");

        if(strDungTich ==null ){
            strDungTich = "";
        }
        if(strTheTich ==null ){
            strTheTich = "";

        }
        if(strInverter ==null ){
            strInverter = "";

        }
        if(strKhoiLuong ==null){
            strKhoiLuong ="";

        }
        if(strCongXuat ==null ){
            strCongXuat = "";

        }
        if(strKichThuoc  ==null ){
            strKichThuoc = "";

        }
        if(strNamSanXuat ==null ){
            strNamSanXuat = "";
        }
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
        finish();
    }
    public void gotoSearchAnimation2(String thietbi, String problemKhac,String strLocation){
        Intent intent = new Intent(DecriseProblemActivity.this,AnimationSearchActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("problem",problemKhac);
        intent.putExtra("locationUser",strLocation);
        //intent.putExtra("price",price);
        startActivity(intent);
        finish();
    }
    public void gotoSearchAnimation3(String thietbi, String problemKhac,String prolemKhacDetails,String strLocation){
        Intent intent = new Intent(DecriseProblemActivity.this,AnimationSearchActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("problem",problemKhac);
        intent.putExtra("problemDetail",prolemKhacDetails);
        intent.putExtra("locationUser",strLocation);
        //intent.putExtra("price",price);
        startActivity(intent);
        finish();
    }

}
