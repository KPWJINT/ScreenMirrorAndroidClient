package javatech.screenmirror;


import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Main extends Activity {

    @BindView(R.id.connect)
    Button connectButton;

    @BindView(R.id.message)
    TextView mess;

    MyReceiver myReceiver;

    static boolean IS_CONNECTED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setMyReceiver();
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
        Intent intent=new Intent(this, ClientService.class);
        startService(intent);
    }

    private void setMyReceiver(){
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ClientService.SEND);
        registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("DEBUG", "receiver got messs");
            String message=intent.getStringExtra("DATA_PASS");
            mess.setText(message);
        }
    }

}
