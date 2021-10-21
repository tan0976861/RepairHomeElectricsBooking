package com.example.repairhomeelectricbooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private Button btnSearch;
    private Button btnBookingRepair;
    private ScrollView scrollViewMainUser;
    private RecyclerView rcv_item;
    private ItemAdapter itemAdapter;
    private Context mContext;
    private RelativeLayout layout_firstWorker;
    private DatabaseReference mDatabase;
    private EditText edtProblem,edtFee;
    private Spinner  edtInputThietBi;
    private  Spinner spinnerMoTaVanDe;
    private TextView tvVanDeKhac;
    private  EditText edtMoTaVanDeKhac,edtVitri;


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
//        btnSearch = (Button) view.findViewById(R.id.btnSearch);
//        layout_firstWorker= (RelativeLayout) view.findViewById(R.id.layout_firstWorker);
//        scrollViewMainUser = (ScrollView) view.findViewById(R.id.scrollViewMainUser);
//        rcv_item = (RecyclerView) view.findViewById(R.id.rcv_item);
//        itemAdapter = new ItemAdapter(getActivity());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
//        rcv_item.setLayoutManager(linearLayoutManager);
//        itemAdapter.setData(getListItem());
//        rcv_item.setAdapter(itemAdapter);
        //edtThietbi=(EditText) view.findViewById(R.id.edtInputThietBi);
//        edtProblem=(EditText)  view.findViewById(R.id.edtInputVanDe);
        //edtFee= (EditText)  view.findViewById(R.id.edtInputGiaTien);
        edtInputThietBi=(Spinner) view.findViewById(R.id.edtInputThietBi);
        btnBookingRepair=(Button) view.findViewById(R.id.btn_BookingRepair);
        spinnerMoTaVanDe = (Spinner) view.findViewById(R.id.spinnerMoTaVanDe);
        edtVitri=(EditText) view.findViewById(R.id.edtAddressUser);
        //TextView=(EditText) view.findViewById(R.id)
        //getUserInfo();
        btnBookingRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strThietBi = edtInputThietBi.getSelectedItem().toString();
                String strVanDe = spinnerMoTaVanDe.getSelectedItem().toString();
                String StrLocation=edtVitri.getText().toString();
                //String strFee = edtFee.getText().toString();
                gotoSearchAnimation(strThietBi,strVanDe,StrLocation);
            }
        });
        tvVanDeKhac = (TextView) view.findViewById(R.id.tvVanDeKhac);
        edtMoTaVanDeKhac = (EditText) view.findViewById(R.id.edtInputVanDeKhac);
        tvVanDeKhac.setVisibility(View.GONE);
        edtMoTaVanDeKhac.setVisibility(View.GONE);


        List<String> list = new ArrayList<>();
        list.add(0,"Chọn thiết bị");
        list.add("Quạt");
        list.add("Đèn");
        list.add("Máy lạnh");
        list.add("Máy giặt");
        list.add("Nồi cơm");
        list.add("Lò nướng");
        list.add("Máy rửa chén");

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        edtInputThietBi.setAdapter(adapter);
        edtInputThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), edtInputThietBi.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                switch (edtInputThietBi.getSelectedItemPosition()){
                    case 0:
                        break;
                    case 1:
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase.child("quat").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");

                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 2:
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase1.child("den").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");
                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 3:
                        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase2.child("maylanh").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");
                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 4:
                        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase3.child("maygiat").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");

                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 5:
                        DatabaseReference mDatabase4 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase4.child("noicom").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");

                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 6:
                        DatabaseReference mDatabase5 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase5.child("lonuong").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");

                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;

                    case 7:
                        DatabaseReference mDatabase6 = FirebaseDatabase.getInstance().getReference("item");
                        mDatabase6.child("mayruachen").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final List<String> areas = new ArrayList<String>();
                                areas.add("Chọn vấn đề");

                                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                    String areaName = areaSnapshot.child("1").getValue(String.class);
                                    areas.add(areaName);
                                }

                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                spinnerMoTaVanDe.setAdapter(areasAdapter);
                                spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if(spinnerMoTaVanDe.getSelectedItem().toString() == areas.get(areas.size()-1)){
                                            tvVanDeKhac.setVisibility(view.VISIBLE);
                                            edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
                                        } else{
                                            tvVanDeKhac.setVisibility(view.GONE);
                                            edtMoTaVanDeKhac.setVisibility(view.GONE);
                                        }
                                        Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        List<String> listProblem = new ArrayList<>();
//        listProblem.add("Máy lạnh rò rỉ nước");
//        listProblem.add("Vệ sinh máy lạnh");
//        listProblem.add("Tủ lạnh không đủ lạnh");
//        listProblem.add("Máy giặt không quay được");
//        listProblem.add("Ti vi không lên hình");
//        listProblem.add("Vấn đề khác...");
//
//        ArrayAdapter<String> adapterProblem = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listProblem);
//        adapterProblem.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//
//        spinnerMoTaVanDe.setAdapter(adapterProblem);
//        spinnerMoTaVanDe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(spinnerMoTaVanDe.getSelectedItem().toString() == listProblem.get(listProblem.size()-1)){
//                    tvMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                    edtMoTaVanDeKhac.setVisibility(view.VISIBLE);
//                } else{
//                    tvMoTaVanDeKhac.setVisibility(view.INVISIBLE);
//                    edtMoTaVanDeKhac.setVisibility(view.INVISIBLE);
//                }
//                Toast.makeText(getActivity(), spinnerMoTaVanDe.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),SearchWorkerActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
//        layout_firstWorker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),DetailWorkerActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
//        scrollViewMainUser.setOnTouchListener(new TranslateAnimationUtil(getActivity(),btnSearch));
        // Inflate the layout for this fragment
        return view;
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
        Intent intent = new Intent(getActivity(),AnimationSearchActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("problem",problem);
        intent.putExtra("locationUser",strLocation);
        //intent.putExtra("price",price);
        startActivity(intent);
    }
//    public void getUserInfo(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        mDatabase=  FirebaseDatabase.getInstance().getReference("tblUser").child(user.getUid());
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                strUserName=snapshot.child("fullName").getValue().toString();
//                strPhone=snapshot.child("phone").getValue().toString();
//
//                Intent intent = new Intent(getActivity(),AnimationSearchActivity.class);
//                intent.putExtra("userName",strUserName);
//                intent.putExtra("phoneUser",strPhone);
//                //edtVitri.setText(strPhone);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}