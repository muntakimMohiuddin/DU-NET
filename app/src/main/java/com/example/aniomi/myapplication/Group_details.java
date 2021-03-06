package com.example.aniomi.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asif on 11/3/17.
 */

public class Group_details implements Serializable{
    public  String name , about ,operner,adminPass ,userPass , groupID;
    public  static String tname , tabout ,toperner,tadminPass ,tuserPass , tgroupID;
    private static Context context;
    public static Uri tfilePath;
    public static List<Group_details> staticList;
    public static boolean edit ;
    static boolean groupPost ;

    Group_details(String name , String about , String  operner ,String adminPass ,String userPass,String groupID)
    {
        this.name = name;
        this.about = about;
        this.operner = operner;
        this.adminPass = adminPass;
        this.userPass = userPass;
        this.groupID = groupID;
    }

    Group_details()
    {
        name = about = operner= adminPass = userPass = groupID = "ASIF";
    }

    static void Creat_Group(Context cont)
    {
        context = cont;
        if (tfilePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            //taken++;
            StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
            StorageReference riversRef = storageReference.child("GROUPimages/"+tgroupID+".jpg");
            riversRef.putFile(tfilePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast

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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are You Sure ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth mAuth;
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
                        final String openerID = Students.current.getUid();
                        Group_details group_details = new Group_details(tname,tabout,openerID,tadminPass,tuserPass,tgroupID);
                        mDatabase.child(tgroupID).setValue(group_details);
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("MyGroup").child(Students.current.getUid());
                        mDatabase.child(tgroupID).setValue(group_details);

                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Group_Admin").child(tgroupID);
                        mDatabase.child(Students.current.getUid()).setValue(Students.current);
                        Toast.makeText(context, group_details.getAdminPass(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        /*if (creatButton) {
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

                        }*/
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getOperner() {
        return operner;
    }

    public void setOperner(String operner) {
        this.operner = operner;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public static String getTname() {
        return tname;
    }

    public static void setTname(String tname) {
        Group_details.tname = tname;
    }

    public static String getTabout() {
        return tabout;
    }

    public static void setTabout(String tabout) {
        Group_details.tabout = tabout;
    }

    public static String getToperner() {
        return toperner;
    }

    public static void setToperner(String toperner) {
        Group_details.toperner = toperner;
    }

    public static String getTadminPass() {
        return tadminPass;
    }

    public static void setTadminPass(String tadminPass) {
        Group_details.tadminPass = tadminPass;
    }

    public static String getTuserPass() {
        return tuserPass;
    }

    public static void setTuserPass(String tuserPass) {
        Group_details.tuserPass = tuserPass;
    }

    public static String getTgroupID() {
        return tgroupID;
    }

    public static void setTgroupID(String tgroupID) {
        Group_details.tgroupID = tgroupID;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Group_details.context = context;
    }
}
