package com.example.repairhomeelectricbooking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
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
 * Use the {@link MainUserFragment#newInstance} factory method to
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
    private RelativeLayout layout_firstWorker;
    private DatabaseReference mDatabase;
    private EditText edtProblem,edtFee;
    private  Spinner spinnerMoTaVanDe;
    private Button btnNextToDecriseProblem;
    private Spinner  edtInputThietBi;
    private TextView tvChonHangDen,tvChonLoaiDen;
    private TextView tvrHangMayLanh,tvHangMayGiat,tvInputLoaiMayGiat,tvHangQuat,tvInputChonLoaiQuat;
    private  EditText edtMoTaVanDeKhac,edtVitri,edtInputVanDeChitiet;
    private boolean[] selectedProblem;
    private LinearLayout ly_inputQuat,ly_inputDen,ly_inputMayLanh,ly_inputMayGiat,ly_inputNoiCom,ly_inputLoNuong,ly_inputMayRuaChen,ly_inputTivi,ly_inputTuLanh;



    public MainUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainUserFragment newInstance(String param1, String param2) {
        MainUserFragment fragment = new MainUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
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
        tvChonHangDen=(TextView) view.findViewById(R.id.tvChonHangDen);
        tvChonLoaiDen=(TextView) view.findViewById(R.id.tvChonLoaiDen);
        tvHangQuat=(TextView) view.findViewById(R.id.spinnerHangQuat);
        tvInputChonLoaiQuat=(TextView) view.findViewById(R.id.tvInputChonLoaiQuat);
        tvHangMayGiat=(TextView) view.findViewById(R.id.spinnerHangMayGiat) ;
        tvInputLoaiMayGiat=(TextView)view.findViewById(R.id.tvInputLoaiMayGiat) ;
        // tvMotaVande = (TextView) view.findViewById(R.id.spinnerMoTaVanDe);
//        tvMotaVandechitiet=(TextView) view.findViewById(R.id.tvMoTaVanDe);
//        edtInputVanDeChitiet=(EditText) view.findViewById(R.id.edtInputVanDe);
        //edtVitri=(EditText) view.findViewById(R.id.edtAddressUser);
        //TextView=(EditText) view.findViewById(R.id)
        //getUserInfo();\



//        tvVanDeKhac = (TextView) view.findViewById(R.id.tvVanDeKhac);
//        edtMoTaVanDeKhac = (EditText) view.findViewById(R.id.edtInputVanDeKhac);
//        tvVanDeKhac.setVisibility(View.GONE);
//        edtMoTaVanDeKhac.setVisibility(View.GONE);


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
                                        dialog.getWindow().setLayout(750,1100);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(750,1100);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                                tvInputChonLoaiQuat.setText(adapter2.getItem(i));
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
                        tvHangMayGiat.setOnClickListener(new View.OnClickListener() {
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
                                        dialog.getWindow().setLayout(750,1100);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                                tvHangMayGiat.setText(adapter2.getItem(i));
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
                        tvInputLoaiMayGiat.setOnClickListener(new View.OnClickListener() {
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
                                        dialog.setContentView(R.layout.dialog_searchable_spinner);
                                        dialog.getWindow().setLayout(750,1100);
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                                tvInputLoaiMayGiat.setText(adapter2.getItem(i));
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
                    if (edtInputThietBi.getSelectedItem().toString().trim().equals("Máy giặt")) {
                        goToDescriseProblemMayGiat(edtInputThietBi.getSelectedItem().toString(), tvHangMayGiat.getText().toString(), tvInputLoaiMayGiat.getText().toString());
                    } else if (edtInputThietBi.getSelectedItem().toString().trim().equals("Quạt")) {
                        goToDescriseProblemQuat(edtInputThietBi.getSelectedItem().toString(), tvHangQuat.getText().toString(), tvInputChonLoaiQuat.getText().toString());
                    } else {
                        System.out.println("Test Function Success");
                    }
                }

            });




        // Inflate the layout for this fragment
        return view;
    }
    public void goToDescriseProblemMayGiat(String thietbi, String hang,String loai){
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

//    private List<Item> getListItem(){
//        List<Item> list = new ArrayList<>();
//
//        list.add(new Item(R.drawable.maygiat,"Máy giặt"));
//        list.add(new Item(R.drawable.tivi,"Tivi"));
//        list.add(new Item(R.drawable.den,"Đèn"));
//        list.add(new Item(R.drawable.loa,"Loa"));
//        list.add(new Item(R.drawable.maylanh,"Máy lạnh"));
//        list.add(new Item(R.drawable.lovisong,"Lò vi sóng"));
//        list.add(new Item(R.drawable.quat,"Quạt"));
//        list.add(new Item(R.drawable.binhnaunuoc,"Bình nấu nước"));
//        list.add(new Item(R.drawable.maynonglanh,"Máy nóng lạnh"));
//        list.add(new Item(R.drawable.ocam,"Ổ cắm"));
//        return list;
//    }
//    public void gotoSearchAnimation(String thietbi, String problem,String strLocation){
//        Intent intent = new Intent(getActivity(),AnimationSearchActivity.class);
//        intent.putExtra("thietbi",thietbi);
//        intent.putExtra("problem",problem);
//        intent.putExtra("locationUser",strLocation);
//        //intent.putExtra("price",price);
//        startActivity(intent);
//    }





}