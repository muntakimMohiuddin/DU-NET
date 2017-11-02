package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class EventDescriptionActivity extends AppCompatActivity {
    private TextView mDescription;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        // mSave = (Button) findViewById(R.id.button8);
        mDescription = (TextView) findViewById(R.id.mevent1);
        mDescription.setCursorVisible(true);
        mDescription.setFocusableInTouchMode(true);
        mDescription.requestFocus();
        mDescription.setEnabled(true);
        mDescription.setMovementMethod(new ScrollingMovementMethod());
        mDescription.setText(EventDetails.tDescription);

        // EventDetails.temporaryDescription = mDescription.getText().toString();

       /* mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDetails.temporaryDescription = mDescription.getText().toString();
               // Toast.makeText(getApplicationContext(),EventDetails.temporaryDescription,Toast.LENGTH_LONG).show();
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDescription = (TextView) findViewById(R.id.mevent1);
        EventDetails.tDescription = mDescription.getText().toString();
        this.finish();
    }
}
