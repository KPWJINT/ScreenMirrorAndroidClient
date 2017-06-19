package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread {

    private static final int PORT =50243;

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
//            createSocket();
//            createInputStream();
            while(isActive)
            {
                createSocket();
                createInputStream();
                broadcastDataToUI(getScreenshotFromInputStream());
                clientSocket.close();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
            }
        } catch (SocketException e) {
            System.out.println("run - SocketException");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createSocket() throws Exception
    {
            clientSocket = new Socket(host, PORT);
    }

    private void createInputStream() throws IOException {
        try {
            dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        }catch(Exception e)
        {
            clientSocket.close();
        }

    }

    private byte[] getScreenshotFromInputStream()
    {
        byte[] screenshotInByte = null;
        try
        {
            int length = dis.readInt();                                     // read length of incoming message
            if(length>0)
            {
                screenshotInByte = new byte[length];
                dis.readFully(screenshotInByte, 0, screenshotInByte.length); // read the message

            }
        }catch (IOException e)
        {
            System.out.println("getScreenshotFromInputStream - IOExcpetion");
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
