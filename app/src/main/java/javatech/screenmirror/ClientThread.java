package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread extends Thread {

    private static final String DEBUG="debug";
    private static final int PORT =50268;
    private static final String HOST="172.16.11.83";
    private final int TIMEOUT = 1000;
    private Socket clientSocket =null;
    private Context context;

    public ClientThread(Context context)
    {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            runClientSocket();
            closeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runClientSocket() throws IOException {

        for(int i = 0; i < 50; i++)
        {
                clientSocket = new Socket(HOST, PORT);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String messegeFromServer = bufferedReader.readLine();

                Intent intent = new Intent("com.javatech.screenshot");
                intent.putExtra("result", messegeFromServer);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                bufferedReader.close();
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void closeClient() throws IOException {
        clientSocket.close();
    }
}
