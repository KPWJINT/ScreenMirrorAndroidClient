package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
        clientSocket = new Socket(host, PORT);

        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        byte[] screenshotInByte = null;
        int length = dis.readInt();                    // read length of incoming message
        if(length>0)
        {
            screenshotInByte = new byte[length];
            dis.readFully(screenshotInByte, 0, screenshotInByte.length); // read the message
        }
        dis.close();

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
