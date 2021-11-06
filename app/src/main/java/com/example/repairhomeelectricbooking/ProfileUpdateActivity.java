package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity {

    private DatabaseReference rootDatabaseref;
    private EditText edtCustomerName, edtCustomerPhone;
    private TextView tv_update, tv_chooseImgCustomer, tvUpdateImageCustomer;
    private CircleImageView imgAvatarCustomer;
    private ImageButton  imgBackToMainCustomer;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        edtCustomerName=findViewById(R.id.edtCustomerName);
        edtCustomerPhone=findViewById(R.id.edtCustomerPhone);
        tv_chooseImgCustomer=findViewById(R.id.tv_chooseImgCustomer);
        tvUpdateImageCustomer=findViewById(R.id.tvUpdateImageCustomer);
        imgAvatarCustomer=findViewById(R.id.imgAvatarCustomer);
        tv_update=findViewById(R.id.tv_update);
        imgBackToMainCustomer=findViewById(R.id.imgBackToMainCustomer);
        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblUser");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("tblUser");
        FirebaseDatabase md = FirebaseDatabase.getInstance();
        DatabaseReference mr = md.getReference("tblUser").child(user.getUid()).child("image");


        mr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(ProfileUpdateActivity.this).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(imgAvatarCustomer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tv_chooseImgCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 174);
            }
        });

        tvUpdateImageCustomer.setOnClickListener(v -> {
            if (uri!=null){
                StorageReference  demoRef = reference.child(user.getUid() + ".jpg");
                demoRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(ProfileUpdateActivity.this, "Đã cập nhật thành công ảnh!", Toast.LENGTH_SHORT).show();
                        demoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri DownloadUri) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference mref = database.getReference("tblUser");
                                mref.child(user.getUid()).child("image").setValue(DownloadUri.toString());

                            }
                        });

                    }
                });

            }

        });

        rootDatabaseref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("fullName").getValue().toString();
                String phone=snapshot.child("phone").getValue().toString();
                String s= "0"+ phone.substring(3,phone.length());
                edtCustomerName.setText(name);
                edtCustomerPhone.setText(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String customerName=edtCustomerName.getText().toString();
                String customerPhone=edtCustomerPhone.getText().toString();
                if(customerPhone.length() != 10 ){
                    Toast.makeText(ProfileUpdateActivity.this,"Vui lòng nhập đúng số điện thoại",Toast.LENGTH_SHORT).show();
                    System.out.println("Test: 135" );
                    return;
                }
                System.out.println("Test: 138" );
                if(!customerPhone.startsWith("0")){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đúng số điện thoại ",Toast.LENGTH_SHORT).show();
                    System.out.println("Test: 141" );
                    return;
                }

                String phone= "+84" + customerPhone.substring(1,10);
                HashMap hasMap= new HashMap();
                hasMap.put("fullName", customerName);
                hasMap.put("phone", phone);
//                hasMap.put("email", workerEmail);

                rootDatabaseref.child(user.getUid()).updateChildren(hasMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ProfileUpdateActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        imgBackToMainCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        rootDatabaseref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String link = snapshot.getValue(String.class);
//                Picasso.get().load(link).into(imgAvatarCustomer);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==174 && resultCode==RESULT_OK){
            uri = data.getData();
            if (uri!=null){
                Glide.with(ProfileUpdateActivity.this).load(uri).into(imgAvatarCustomer);

            }


        }


    }


}