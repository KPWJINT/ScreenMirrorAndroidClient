package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final int PORT =50268;
    private static final String HOST="172.16.11.83";
    private final int TIMEOUT = 1000;

    private Socket clientSocket =null;
    private Context context;

    private boolean isActive = true;

    public ClientThread(Context context)
    {
        this.context = context;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void stopClient()
    {
        isActive = false;
    }

    public void resumeClient()
    {
        isActive = true;
    }

    @Override
    public void run() {
        try {
            while(isActive)
                runClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runClientSocket() throws IOException, InterruptedException
    {
        clientSocket = new Socket(HOST, PORT);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String messageFromServer = bufferedReader.readLine();

        broadcastDataToUI(messageFromServer);

        bufferedReader.close();

        Thread.sleep(TIMEOUT);
    }

    private void broadcastDataToUI(String data)
    {
        Intent intent = new Intent("com.javatech.screenshot");
        intent.putExtra("result", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
