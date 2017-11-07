package com.example.aniomi.myapplication;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.aniomi.myapplication.Group_details.getContext;

public class post extends AppCompatActivity {
    static int a[]=new int[100];
    static HashMap<String,String> hm= new HashMap<>();
    int request_Code = 1 ;
    DatabaseReference ds;
    String postid;
    ImageView imageView;
    private static final  int PICK_IMAGE_REQUEST=234;
    ArrayList<String> dept=new ArrayList<>();
    ArrayList<String> picuris=new ArrayList<>();
    private Uri filePath;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
    static int taken = 0;
    static String choose="Any";
    private EditText et1, et2;
    SpinnerDialog deptdialog;
    ExpandableHeightGridView simpleList;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        dept.add("CSE");dept.add("EEE");dept.add("IT");dept.add("Physics");dept.add("Mechatronics");dept.add("Microbilogy");dept.add("Any");dept.add("Pharmacy");
        final ImageButton button = findViewById(R.id.bc);
        final ImageButton ch=findViewById(R.id.ba);
        final ImageButton bl=findViewById(R.id.bl);
        final ImageButton b1, b2,time,date;
        imageView=(ImageView) findViewById(R.id.imageView);
        simpleList = (ExpandableHeightGridView) findViewById(R.id.simpleGridView);
        //uploaded_images_adapter myAdapter=new uploaded_images_adapter(getContext(),R.layout.activity_gridview,picuris);
        //simpleList.setAdapter(myAdapter);
        prev=findViewById(R.id.prev);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        time = findViewById(R.id.bT);
        date = findViewById(R.id.bD);
        prev.setVisibility(View.GONE);
        et1 = findViewById(R.id.et1);
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference().child("Posts");
        ds = databaseUsers.push();

        deptdialog=new SpinnerDialog(this,dept,"Select Department");
        deptdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //Toast.makeText(getActivity(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                //cs.setText(item);
                choose=item;
            }
        });
        postid = "" + ds.getKey();
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder;
                builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(post.this);
                    startActivityForResult(intent,request_Code); // request code = 1;
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
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
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptdialog.showSpinerDialog();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                Date date1 = new Date();
                String d,t;
                d=(String)dateFormat.format(date);
                t=(String)dateFormat1.format(date1);
                posto temp=new posto(postid,taken+"",Students.current.uid,d,Students.current.getName(),et1.getText().toString(),choose);
                ds.setValue(temp);
                taken=0;
                choose="Any";

                et1.setText("");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(post.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(selectedHour > 12){
                            choosedTimeZone = "PM";
                            selectedHour = selectedHour - 12;
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }

                        if(selectedMinute < 10){
                            choosedMinute = "0"+selectedMinute;
                        }else{
                            choosedMinute= ""+selectedMinute;
                        }

                        et1.append(" "+choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dialog = new DatePickerDialog(
                        post.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener,
                        mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = day + "/" + month + "/" + year;
                et1.append(" "+date);
            }
        };


    }
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            taken++;
            StorageReference riversRef = storageReference.child("POSTimages/"+postid+(taken)+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(),"Reach",Toast.LENGTH_SHORT);

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
                picuris.clear();
                for (int i=1;i<=taken;i++)
                {
                    picuris.add(postid+i);
                }
                Toast.makeText(getApplicationContext(),"Reach",Toast.LENGTH_LONG);
                prev.setVisibility(View.VISIBLE);
                uploaded_images_adapter myAdapter=new uploaded_images_adapter(getApplicationContext(),R.layout.uploaded_images,picuris);
                simpleList.setAdapter(myAdapter);
                simpleList.setExpanded(true);

                //uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ///jkjlop
        }
        if(requestCode == request_Code && resultCode == RESULT_OK)
        {
            Place place = PlacePicker.getPlace(data,this);
            String name = (String) place.getName();
            String  address = (String) place.getAddress();
            et1 = (EditText) findViewById(R.id.et1);
            et1.append(name+"\n"+address);

        }
    }

}
