package com.soraohayou.study.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.soraohayou.study.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final NewThread newThread = new NewThread();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_main);
        newThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        newThread.sendMessage("123");

    }


    static class NewThread extends Thread {

        private Handler handler;

        Looper looper;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            looper = Looper.myLooper();
            assert looper != null;
            handler = new Handler(looper) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    Log.i(TAG, msg.obj.toString());
                }
            };
            Looper.loop();
        }

        public void sendMessage(String msg) {
            Message message = handler.obtainMessage();
            message.obj = msg;
            message.sendToTarget();
            message.recycle();
        }

        public void idleMission(){
            looper.getQueue().addIdleHandler(() -> {
                return false;
            });
        }

    }

}
