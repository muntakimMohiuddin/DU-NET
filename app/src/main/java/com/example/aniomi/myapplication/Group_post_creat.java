package com.example.aniomi.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Group_post_creat extends AppCompatActivity {

    static int a[]=new int[100];
    static HashMap<String,String> hm= new HashMap<>();
    int request_Code = 1 ;
    DatabaseReference ds;
    String postid;
    ImageView imageView;
    private static final  int PICK_IMAGE_REQUEST=234;
    ArrayList<String> dept=new ArrayList<>();
    private Uri filePath;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
    static int taken = 0;
    static String choose="Any";
    private EditText et1, et2;
    SpinnerDialog deptdialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post_creat);

        final ImageButton button = findViewById(R.id.bc);
        final ImageButton b1, b2,time,date;
        imageView=(ImageView) findViewById(R.id.imageView);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        et1 = findViewById(R.id.et1);
        et1.setText(posto.ttext);
        posto.ttext = "";
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference().child("Group_post").child(Group_details.tgroupID);
        ds = databaseUsers.push();

        postid = "" + ds.getKey();

        if(Group_details.edit == true)
        {
            et1.setText(Group_details.tabout);
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference forestRef = storageRef.child("GROUPimages/"+Group_details.tgroupID+".jpg");
            Glide.with(this).using(new FirebaseImageLoader())
                    .load(forestRef)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posto temp=new posto(postid,taken+"",Students.current.getName(),Students.current.getUid(),et1.getText().toString());
                ds.setValue(temp);
                taken=0;
                et1.setText("");
                Toast.makeText(Group_post_creat.this,"Posted",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            taken++;
            StorageReference riversRef = storageReference.child("Group_post_image/"+postid+(taken)+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                //uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
