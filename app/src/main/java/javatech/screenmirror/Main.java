package javatech.screenmirror;


import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

    static boolean IS_CONNECTED;

    @BindView(R.id.connect)
    Button connectButton;

    @BindView(R.id.message)
    TextView mess;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        broadcastReceiver = createBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("com.javatech.screenshot"));
    }

    @OnClick(R.id.connect)
    public void connect(){
        IS_CONNECTED=true;
        startConnection();
    }

    @OnClick(R.id.disconnect)
    public void disconnect(){
        IS_CONNECTED=false;
    }

    private void startConnection(){
        ClientThread clientThread = new ClientThread(this);
        clientThread.start();
    }

    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateResults(intent.getStringExtra("result"));
            }
        };
    }

    private void updateResults(String text)
    {
        mess.setText(text);
        Log.i("DEBUG", "message from server to send to activity: "+text);
    }

    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
