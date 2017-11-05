package com.example.aniomi.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class CreatEventsActivity extends AppCompatActivity {

    private EditText mName, mDate, mTime,mLoc;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView mDescription;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button mCreat;
    SpinnerDialog attrdialog, deptdialog;
    boolean creatButton = false;
    int request_Code = 1 ;

    ArrayList<String> attr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_events);
        setTitle("CREATE EVENT");
        mName = (EditText) findViewById(R.id.editText8);
        mDate = (EditText) findViewById(R.id.editText11);
        mTime = (EditText) findViewById(R.id.editText12);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layout02);
        mDescription = (TextView) findViewById(R.id.editText6);
        mLoc = (EditText) findViewById(R.id.Loc);

        mDescription.setCursorVisible(true);
        mDescription.setFocusableInTouchMode(true);
        mDescription.requestFocus();
        mDescription.setEnabled(true);
        mDescription.setMovementMethod(new ScrollingMovementMethod());
        mLoc.setMovementMethod(new ScrollingMovementMethod());

        mName.setText(EventDetails.tName);
        mTime.setText(EventDetails.tTime);
        mDate.setText(EventDetails.tDate);
        mLoc.setText(EventDetails.tLoc);
        mDescription.setText(EventDetails.tDescription);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dialog = new DatePickerDialog(
                        CreatEventsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener,
                        mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreatEventsActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                        mTime.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        mLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder;
                builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(CreatEventsActivity.this);
                    startActivityForResult(intent,request_Code); // request code = 1;
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = day + "/" + month + "/" + year;
                mDate.setText(date);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_done:
                EditText mName, mDate, mTime,mLoc;
                TextView mDescription;
                mName = (EditText) findViewById(R.id.editText8);
                mDate = (EditText) findViewById(R.id.editText11);
                mTime = (EditText) findViewById(R.id.editText12);
                mDescription = (TextView) findViewById(R.id.editText6);
                mLoc = (EditText) findViewById(R.id.Loc);

                final String name = mName.getText().toString();
                final String date = mDate.getText().toString();
                final String time = mTime.getText().toString();
                final String loc = mLoc.getText().toString();
                final String description = mDescription.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(loc) && creatButton == false) {
                    mDate.setError(null);
                    mName.setError(null);
                    mDescription.setError(null);
                    mTime.setError(null);
                    mLoc.setError(null);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatEventsActivity.this);
                    builder1.setMessage("Are You Sure ?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
                                    if(EventDetails.tEventID != null || EventDetails.tEventID != "")
                                    {
                                        EventDetails.tEventID = mDatabase.push().getKey();
                                    }
                                    final String openerID = Students.current.getUid();
                                    EventDetails event = new EventDetails(name, date, time, description,loc, EventDetails.tEventID,openerID);
                                    mDatabase.child(EventDetails.tEventID).setValue(event);
                                    creatButton = true;
                                    dialog.cancel();
                                    if (creatButton) {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(CreatEventsActivity.this);
                                        builder2.setMessage("Do you want to Invite ?");
                                        builder2.setCancelable(true);

                                        builder2.setPositiveButton(
                                                "Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                        EventDetails.tName = name;
                                                        EventDetails.tDate = date;
                                                        EventDetails.tTime = time;
                                                        EventDetails.tDescription = description;
                                                        EventDetails.tOpenerID = openerID;
                                                        EventDetails.tLoc = loc;
                                                        Intent intent = new Intent(CreatEventsActivity.this,InviteActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                        builder2.setNegativeButton(
                                                "NO",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder2.create();
                                        alert11.show();

                                    }
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {

                    if (TextUtils.isEmpty(name))
                        mName.setError("This field can not be blank");
                    if (TextUtils.isEmpty(date))
                        mDate.setError("This field can not be blank");
                    if (TextUtils.isEmpty(time))
                        mTime.setError("This field can not be blank");
                    if (TextUtils.isEmpty(description))
                        mDescription.setError("This field can not be blank");
                    if (TextUtils.isEmpty(loc))
                        mDescription.setError("This field can not be blank");

                }
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDescription = (TextView) findViewById(R.id.editText6);
        mDescription.setText(EventDetails.tDescription);
    }

    @Override
    protected void onActivityResult( int requestCode , int resultcode, Intent data)
    {
        if(requestCode == request_Code && resultcode == RESULT_OK)
        {
            Place place = PlacePicker.getPlace(data,this);
            String name = (String) place.getName();
            String  address = (String) place.getAddress();
            mLoc = (EditText) findViewById(R.id.Loc);
            mLoc.setText(name+"\n"+address);
        }
    }
}