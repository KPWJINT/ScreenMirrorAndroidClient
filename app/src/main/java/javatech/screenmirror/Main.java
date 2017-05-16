package javatech.screenmirror;


import android.app.Activity;

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


public class Main extends Activity {

    private static final int PORT =50268;
    private static final String HOST="192.168.0.59";
    private static final String DEBUG="debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void connectToPC(){
        new Thread() {
            public void run() {
                String messageToSend = "hi server\n";

                Socket client = null;
                try
                {
                    Log.i(DEBUG, "before connection");
                    client = new Socket(HOST, PORT);
                    Log.i(DEBUG, "after connection");

                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    bufferedWriter.write(messageToSend);
                    bufferedWriter.flush();

                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(client.getInputStream()));
                    Log.i(DEBUG, "message from server "+ bufferedReader.readLine());
                    System.out.println("message from server "+ bufferedReader.readLine());

                    Log.i(DEBUG, "client has ended");

                } catch (
                        IOException e)
                {
                    Log.e(DEBUG, e.getMessage());
                }

            }
        }.start();
    }
}
