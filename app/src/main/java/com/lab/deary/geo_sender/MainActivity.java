package com.lab.deary.geo_sender;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public MyTestReceiver receiverForTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupServiceReceiver();
        startTestService();
    }

    public void startTestService() {
        Intent i = new Intent(this, MyTestService.class);
        i.putExtra("foo", "bar");
        i.putExtra("receiver", receiverForTest);
        startService(i);
    }

    // Setup the callback for when data is received from the service
    public void setupServiceReceiver() {
        receiverForTest = new MyTestReceiver(new Handler());
        // This is where we specify what happens when data is received from the service
        receiverForTest.setReceiver(new MyTestReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    String resultValue = resultData.getString("resultValue");
                    Toast.makeText(MainActivity.this, resultValue, Toast.LENGTH_SHORT).show();

                    ProgressBar pbr = (ProgressBar) findViewById(R.id.progressBar);
                    pbr.setVisibility(View.INVISIBLE);

                    TextView tv = (TextView) findViewById(R.id.textFieldRequestState);
                    tv.setText(getString(R.string.startedService));
                    tv.setTextColor(getResources().getColor(R.color.successfulFetch));
                }
            }
        });
    }
}
