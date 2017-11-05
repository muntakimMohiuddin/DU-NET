package com.example.aniomi.myapplication;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

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
        super.onActivityResult(requestCode, resultCode, data);
        Book_list.filePath = data.getData() ;
        Book_list.fileName = filePath.getLastPathSegment();
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
}
