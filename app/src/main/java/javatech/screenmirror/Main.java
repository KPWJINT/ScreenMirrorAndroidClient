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

    private BroadcastReceiver broadcastReceiver;
    private ClientThread clientThread = null;
    private String host;

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
        }else
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
        clientThread = new ClientThread(this, host);
        clientThread.start();
    }


    private void updateResults(byte[] screenshotInByte)
    {
        long startTime = System.currentTimeMillis();

        Bitmap bitmap = BitmapFactory.decodeByteArray(screenshotInByte, 0, screenshotInByte.length);
        imageViewScreenshot.setImageBitmap(bitmap);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
