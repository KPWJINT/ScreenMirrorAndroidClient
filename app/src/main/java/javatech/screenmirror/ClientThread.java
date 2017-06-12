package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final int PORT =81;

    private Socket clientSocket =null;
    private Context context;
    private String host;

    private boolean isActive = true;

    public ClientThread(Context context, String host)
    {
        this.context = context;
        this.host = host;
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
            clientSocket = new Socket(host, PORT);
            while(isActive)
                runClientSocket();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
    }

    private void runClientSocket() throws IOException, InterruptedException
    {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

        byte[] screenshotInByte = null;
        int length = dis.readInt();                    // read length of incoming message
        if(length>0)
        {
            screenshotInByte = new byte[length];
            dis.readFully(screenshotInByte, 0, screenshotInByte.length); // read the message
        }
        if(screenshotInByte != null)
            broadcastDataToUI(screenshotInByte);
    }

    private void broadcastDataToUI(byte[] data)
    {
        Intent intent = new Intent("com.javatech.screenshot");
        intent.putExtra("result", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
