package com.example.android.ds;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import static android.hardware.Camera.ACTION_NEW_PICTURE;
import static com.example.android.ds.TcpThread.RESULT;

public class SocketService extends Service {
    private TcpThread tcpThread;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tcpThread = new TcpThread();
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
        startThread();
        try {
            tcpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (RESULT == 1){
            captureImage();
        }


        return START_STICKY;
    }

    private void captureImage() {

        Intent captureImage = new Intent(SocketService.this,ImageCaptureActivity.class);
        captureImage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(captureImage);

    }

    private void startThread() {
        tcpThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }
}
