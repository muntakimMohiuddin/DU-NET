package com.example.aniomi.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class Add_book extends AppCompatActivity {

    ImageButton chooseButton, upButton;
    EditText filenameText;
    ListView lvDocuments;
    TextView emptyFileList;

    public static String fileName = null;
    public static final int FILE_REQUEST_CODE = 6667;
    public static Uri filePath;
    StorageReference storageReference;
    DatabaseReference databaseReference,downnLoadRef;

    long downloadReference = 0;

    File file;

    FileAdapter fileAdapter;
    //DownloadManager downloadManager;

    ProgressDialog progressDialogLoad;
    ArrayList<Document> docList=new ArrayList<>();

    public static String department;

    /*public Add_book(Context context) {
        // Required empty public constructor
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_add_book);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.fragment_add_book, null);
        storageReference = FirebaseStorage.getInstance().getReference();
        department  =Students.current.dept;
        databaseReference = FirebaseDatabase.getInstance().getReference("notice").child(department);
        downnLoadRef = FirebaseDatabase.getInstance().getReference("notice").child(department);
        chooseButton = v.findViewById(R.id.choose_button);
        upButton = v.findViewById(R.id.up_button);
        filenameText = (EditText) v.findViewById(R.id.file_name_tv);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        builder2.setPositiveButton(
                "DONE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        upload();
                        finish();
                    }
                }
        );

        builder2.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }
        );
        builder2.setView(v);
        AlertDialog alert11 = builder2.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }

    void upload(){

        fileName = filenameText.getText().toString().trim();

        if(filePath!=null) {

            if (fileName != null) {
                fileName = fileName+".pdf";

                StorageReference newStorageRef = storageReference.child(department + "/" + fileName);

                final ProgressDialog[] progressDialog = {new ProgressDialog(this)};
                progressDialog[0].setTitle("Uploading "+fileName+"...");
                progressDialog[0].show();

                newStorageRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String docName = fileName ;
                                String fileUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();

                                Document doc = new Document(docName,fileUrl);
                                databaseReference.push().setValue(doc);

                                progressDialog[0].setMessage("DONE");
                                try{
                                    if((progressDialog[0] != null) && progressDialog[0].isShowing()){
                                        progressDialog[0].dismiss();
                                    }
                                }catch (final IllegalArgumentException e){
                                    //
                                }
                                catch (final Exception e){
                                    //
                                }finally {
                                    progressDialog[0] = null;
                                }

                                Toast.makeText(Add_book.this, "File upload complete !", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog[0].setMessage("Upload Failed !");
                                progressDialog[0].dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double percentage = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog[0].setMessage((int) percentage + "% uploaded");
                            }
                        });
            } else {
                Toast.makeText(this, "Enter file name", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"Choose a file first",Toast.LENGTH_SHORT).show();
        }
    }

    void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select file"),FILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Add_book.filePath = data.getData() ;
        Add_book.fileName = filePath.getLastPathSegment();
    }
}