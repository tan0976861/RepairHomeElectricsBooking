package com.example.repairhomeelectricbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateWorkerProfileActivity extends AppCompatActivity {


    private DatabaseReference rootDatabaseref;
    private EditText edtWorkerName, edtWorkerPhone;
    private TextView tv_worker_update, tv_chooseImgWorker, tvUpdateImageWorker;
    private CircleImageView imgAvatarWorker;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_worker_profile);
        edtWorkerName =findViewById(R.id.edtWorkerName);
        edtWorkerPhone =findViewById(R.id.edtWorkerPhone);
        tv_chooseImgWorker =findViewById(R.id.tv_chooseImgWorker);
        tvUpdateImageWorker =findViewById(R.id.tvUpdateImageWorker);
        imgAvatarWorker =findViewById(R.id.imgAvatarWorker);
        tv_worker_update =findViewById(R.id.tv_worker_update);
        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("tblWorker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("tblWorker");
        FirebaseDatabase md = FirebaseDatabase.getInstance();
        DatabaseReference mr = md.getReference("tblWorker").child(user.getUid()).child("image");

        mr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(UpdateWorkerProfileActivity.this).load(snapshot.getValue().toString()).error(R.drawable.avatar_default).into(imgAvatarWorker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tv_chooseImgWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 174);
            }
        });

        tvUpdateImageWorker.setOnClickListener(v -> {
            if (uri!=null){
                StorageReference  demoRef = reference.child(user.getUid() + ".jpg");
                demoRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(UpdateWorkerProfileActivity.this, "Đã cập nhật thành công ảnh!", Toast.LENGTH_SHORT).show();
                        demoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri DownloadUri) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference mref = database.getReference("tblWorker");
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
                edtWorkerName.setText(name);
                edtWorkerPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        tv_worker_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String customerName= edtWorkerName.getText().toString();
                String customerPhone= edtWorkerPhone.getText().toString();
//                String workerEmail= edtEmail.getText().toString();
                HashMap hasMap= new HashMap();
                hasMap.put("fullName", customerName);
                hasMap.put("phone", customerPhone);
//                hasMap.put("email", workerEmail);

                rootDatabaseref.child(user.getUid()).updateChildren(hasMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateWorkerProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                });
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
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==174 && resultCode==RESULT_OK){
            uri = data.getData();
            if (uri!=null){
                Glide.with(UpdateWorkerProfileActivity.this).load(uri).into(imgAvatarWorker);

            }


        }


    }

    public void clickToBackProfile(View view){
        finish();
    }
}