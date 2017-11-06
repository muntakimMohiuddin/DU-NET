package com.example.aniomi.myapplication;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Book_list extends Fragment {
    ImageButton chooseButton, upButton;
    EditText filenameText;
    ListView lvDocuments;
    TextView emptyFileList;

    public static String fileName;
    public static final int FILE_REQUEST_CODE = 6667;
    public static Uri filePath;
    StorageReference storageReference;
    DatabaseReference databaseReference, downnLoadRef;

    long downloadReference = 0;

    File file;

    FileAdapter fileAdapter;
    //DownloadManager downloadManager;

    ProgressDialog progressDialogLoad;
    ArrayList<Document> docList = new ArrayList<>();

    public static String department;

    public Book_list() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);
        getActivity().setTitle("BOOKS");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        storageReference = FirebaseStorage.getInstance().getReference();
        department  =Students.current.dept;
        databaseReference = FirebaseDatabase.getInstance().getReference("notice").child(department);
        downnLoadRef = FirebaseDatabase.getInstance().getReference("notice").child(department);

        lvDocuments = (ListView) v.findViewById(R.id.doc_list);
        emptyFileList = (TextView) v.findViewById(R.id.tv_empty_case);
        docList = new ArrayList<>();

        lvDocuments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Document d = (Document) lvDocuments.getItemAtPosition(position);

                showDocument(d);
            }
        });

        progressDialogLoad = new ProgressDialog(getContext());
        progressDialogLoad.setMessage("Loading notices...");
        progressDialogLoad.show();

        loadLinks();

        return v;
    }

    void loadLinks() {
        downnLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot tempDataSnapshot : dataSnapshot.getChildren()) {
                        Document receivedDoc = tempDataSnapshot.getValue(Document.class);

                        docList.add(receivedDoc);
                    }

                    fileAdapter = new FileAdapter(getActivity(), R.layout.document_item, docList);
                    lvDocuments.setAdapter(fileAdapter);

                    emptyFileList.setText("");
                } else {
                    emptyFileList.setText("No Notice");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialogLoad.dismiss();
    }

    void showDocument(Document document) {
        DownloadData(Uri.parse(document.getUrl()), document.getTitle());
    }

    private void DownloadData(Uri uri, String title) {


        // Create request for android download manager
        final DownloadManager downloadManager;
        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle(title);

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, title);

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);
        DownloadManager.Query downloadQuery = new DownloadManager.Query();
        Cursor cursor = downloadManager.query(downloadQuery);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Download Compete", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, String.valueOf(downloadManager.getUriForDownloadedFile(downloadReference)), Toast.LENGTH_SHORT).show();
                file = new File(String.valueOf(downloadManager.getUriForDownloadedFile(downloadReference)));


                /*ShowNotice.file = file;
                Intent intent2 = new Intent(PdfArchive.this,ShowNotice.class);
                startActivity(intent2);*/

            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 6667)
        {
            Book_list.filePath = data.getData() ;
            File f = new File(filePath.getPath());
            Book_list.fileName = Book_list.filePath.getLastPathSegment();
           // filenameText.setText(f.getName());

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_pdf,menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        fileName = null;
        filePath = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View v = layoutInflater.inflate(R.layout.fragment_add_book, null);
        storageReference = FirebaseStorage.getInstance().getReference();
        department  =Students.current.dept;
        databaseReference = FirebaseDatabase.getInstance().getReference("notice").child(department);
        downnLoadRef = FirebaseDatabase.getInstance().getReference("notice").child(department);
        chooseButton = v.findViewById(R.id.choose_button);
        upButton = v.findViewById(R.id.up_button);
        filenameText = (EditText) v.findViewById(R.id.file_name_tv);
        filenameText.setText(fileName);
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
                    }
                }
        );

        builder2.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }
        );
        builder2.setView(v);
        AlertDialog alert11 = builder2.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
        return true;
    }

    void upload(){

        fileName = filenameText.getText().toString().trim();

        if(filePath!=null) {

            if (!TextUtils.isEmpty(fileName)) {
                fileName = fileName+".pdf";

                StorageReference newStorageRef = storageReference.child(department + "/" + fileName);

                final ProgressDialog[] progressDialog = {new ProgressDialog(getContext())};
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

                                Toast.makeText(getContext(), "File upload complete !", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Enter file name", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(),"Choose a file first",Toast.LENGTH_SHORT).show();
        }
    }

    void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select file"),FILE_REQUEST_CODE);
    }
}
