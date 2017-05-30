package javatech.screenmirror;


import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

    @BindView(R.id.buttonConnect)
    Button buttonConnect;


    @BindView(R.id.buttonDisconnect)
    TextView buttonDisconnect;

    @BindView(R.id.imageViewScreenshot)
    ImageView imageViewScreenshot;

    BroadcastReceiver broadcastReceiver;
    ClientThread clientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        broadcastReceiver = createBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("com.javatech.screenshot"));

        //remove
        imageViewScreenshot.setImageResource(R.drawable.icon4);
    }

    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateResults(intent.getByteArrayExtra("result"));
            }
        };
    }

    @OnClick(R.id.buttonConnect)
    public void connect(){
        if(clientThread == null)
        {
            startConnection();
        }else if(!clientThread.isActive())
        {
            clientThread.resumeClient();
        }
    }

    @OnClick(R.id.buttonDisconnect)
    public void disconnect(){
        if(clientThread != null)
            clientThread.stopClient();
    }

    private void startConnection(){
        clientThread = new ClientThread(this);
        clientThread.start();
    }


    private void updateResults(byte[] screenshotInByte)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(screenshotInByte, 0, screenshotInByte.length);
        imageViewScreenshot.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
