package com.rabor.threaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // responsible for updating UI
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            TextView msgText = (TextView) findViewById(R.id.msgTV);
            msgText.setText("Done Downloading");
        }
    };

    public void clickButton(View view) {

        // second threat that runs in the background
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 10000;
                while (System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {

                        }
                    }
                }

                // call your handler
                handler.sendEmptyMessage(0);
            }
        };

        // put all your code in the thread and start it
        Thread myThread = new Thread(r);
        myThread.start();
    }
}
