package com.example.aniomi.myapplication;


        import android.app.Activity;
        import android.app.DownloadManager;
        import android.app.ProgressDialog;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Environment;
        import android.os.StrictMode;
        import android.preference.PreferenceManager;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
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
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;

public class PdfArchive extends AppCompatActivity {

    ImageButton chooseButton, upButton;
    EditText filenameText;
    ListView lvDocuments;
    TextView emptyFileList;

    public static String fileName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_pdf_archive);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("notice").child(department);
        downnLoadRef = FirebaseDatabase.getInstance().getReference("notice").child(department);

        chooseButton = findViewById(R.id.choose_button);
        upButton = findViewById(R.id.up_button);
        lvDocuments = (ListView) findViewById(R.id.doc_list);

        filenameText = (EditText) findViewById(R.id.file_name_tv);
        emptyFileList = (TextView) findViewById(R.id.tv_empty_case);

        docList = new ArrayList<>();

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        lvDocuments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Document d = (Document) lvDocuments.getItemAtPosition(position);

                showDocument(d);
            }
        });

        progressDialogLoad = new ProgressDialog(this);
        progressDialogLoad.setMessage("Loading notices...");
        progressDialogLoad.show();

        loadLinks();
    }

    void loadLinks(){
        downnLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot tempDataSnapshot : dataSnapshot.getChildren()){
                        Document receivedDoc = tempDataSnapshot.getValue(Document.class);

                        docList.add(receivedDoc);
                    }

                    fileAdapter = new FileAdapter(PdfArchive.this,R.layout.document_item,docList);
                    lvDocuments.setAdapter(fileAdapter);

                    emptyFileList.setText("");
                }
                else {
                    emptyFileList.setText("No Notice");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialogLoad.dismiss();
    }

    void upload(){

        fileName = filenameText.getText().toString().trim();

        if(filePath!=null) {

            if (fileName != null) {

                StorageReference newStorageRef = storageReference.child(department + "/" + fileName);

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading "+fileName+"...");
                progressDialog.show();

                newStorageRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String docName = fileName ;
                                String fileUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();

                                Document doc = new Document(docName,fileUrl);
                                databaseReference.push().setValue(doc);

                                progressDialog.setMessage("DONE");
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "File upload complete !", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.setMessage("Upload Failed !");
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double percentage = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage((int) percentage + "% uploaded");
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Enter file name", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Choose a file first",Toast.LENGTH_SHORT).show();
        }
    }

    void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select file"),FILE_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PdfArchive.filePath = data.getData() ;
        PdfArchive.fileName = filePath.getLastPathSegment();
    }

    void showDocument(Document document){
        DownloadData( Uri.parse(document.getUrl()),document.getTitle());
    }

    private void DownloadData (Uri uri, String title) {



        // Create request for android download manager
        final DownloadManager downloadManager;
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle(title);

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalFilesDir(PdfArchive.this,
                Environment.DIRECTORY_DOWNLOADS,title);

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



        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }
}