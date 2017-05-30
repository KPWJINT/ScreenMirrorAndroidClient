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
            createClientSocket(TIMEOUT);
            runClientSocket();
            closeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createClientSocket(int timeout) throws IOException {
        if(clientSocket==null) {
            clientSocket = new Socket(HOST, PORT);
        }
    }

    private void runClientSocket() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        for(int i = 0; i < 50; i++)
        {
            String messegeFromServer = bufferedReader.readLine();

            Intent intent = new Intent("com.javatech.screenshot");
            intent.putExtra("result", messegeFromServer);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bufferedReader.close();
    }

    private void closeClient() throws IOException {
        clientSocket.close();
    }


}
