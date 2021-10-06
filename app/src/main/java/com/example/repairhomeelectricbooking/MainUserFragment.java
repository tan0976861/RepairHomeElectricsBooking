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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.repairhomeelectricbooking.item.Item;
import com.example.repairhomeelectricbooking.item.ItemAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private String mParam1;
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
     private EditText edtThietbi,edtProblem,edtFee;


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
        rcv_item = (RecyclerView) view.findViewById(R.id.rcv_item);
        itemAdapter = new ItemAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        rcv_item.setLayoutManager(linearLayoutManager);
        itemAdapter.setData(getListItem());
        rcv_item.setAdapter(itemAdapter);
        edtThietbi=(EditText) view.findViewById(R.id.edtInputThietBi);
        edtProblem=(EditText)  view.findViewById(R.id.edtInputVanDe);
        edtFee= (EditText)  view.findViewById(R.id.edtInputGiaTien);
        btnBookingRepair=(Button) view.findViewById(R.id.btn_BookingRepair);
        btnBookingRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strThietBi = edtThietbi.getText().toString();
                String strVanDe = edtProblem.getText().toString();
                String strFee = edtFee.getText().toString();
                gotoSearchAnimation(strThietBi,strVanDe,strFee);
            }
        });
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
    public void gotoSearchAnimation(String thietbi, String problem, String price ){
        Intent intent = new Intent(getActivity(),AnimationSearchActivity.class);
        intent.putExtra("thietbi",thietbi);
        intent.putExtra("problem",problem);
        intent.putExtra("price",price);
        startActivity(intent);
    }

}