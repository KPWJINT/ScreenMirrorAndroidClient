package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final int PORT =50243;
    private static final int CONNECTION_TIMEOUT = 1000;
    private static final int SO_TIMEOUT = 1000;

    private Context context;
    private String host;
    private Socket clientSocket =null;
    private DataInputStream dis;

    private boolean isActive = true;

    public ClientThread(Context context, String host)
    {
        this.context = context;
        this.host = host;
    }

    public void stopClient()
    {
        isActive = false;
    }

    @Override
    public void run() {
        while(isActive)
        {
            try {
            createSocket();
            createInputStream();
            broadcastDataToUI(getScreenshotFromInputStream());
            dis.close();
            clientSocket.close();
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    private void createSocket() throws IOException
    {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host,PORT),CONNECTION_TIMEOUT);
        clientSocket.setSoTimeout(SO_TIMEOUT);
    }

    private void createInputStream() throws IOException {
            dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    private byte[] getScreenshotFromInputStream() throws IOException
    {
        byte[] screenshotInByte = null;

        // read length of incoming message
        int length = dis.readInt();
        if(length>0)
        {
            // read the message
            screenshotInByte = new byte[length];
            dis.readFully(screenshotInByte, 0, screenshotInByte.length);
        }

        return screenshotInByte;
    }

    private void broadcastDataToUI(byte[] data)
    {
        if(data != null)
        {
            Intent intent = new Intent("com.javatech.screenshot");
            intent.putExtra("screenshot", data);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}
