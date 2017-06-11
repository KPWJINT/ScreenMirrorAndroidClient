package javatech.screenmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final int PORT =81;

    private DatagramSocket clientSocket =null;
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
        String sendMessege = "Hello server";
        byte[] sendMessegeInByte =sendMessege.getBytes();
        InetAddress IPAddress = InetAddress.getByName(host);
        clientSocket = new DatagramSocket();

        DatagramPacket sendPacket = new DatagramPacket(sendMessegeInByte, sendMessegeInByte.length, IPAddress,PORT);

        clientSocket.send(sendPacket);


        byte[] receiveMessege = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveMessege, receiveMessege.length);
        clientSocket.receive(receivePacket);

        Log.d("Tag" , new String(receiveMessege, 0, receiveMessege.length));

        clientSocket.close();
//        byte[] receiveData = new byte[1024];
//        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//
//        clientSocket.receive(receivePacket);
//        byte[] screenshotInByte = receivePacket.getData();
//
//        if(screenshotInByte != null)
//            broadcastDataToUI(screenshotInByte);
//
//        clientSocket.close();
    }

    private void broadcastDataToUI(byte[] data)
    {
        Intent intent = new Intent("com.javatech.screenshot");
        intent.putExtra("result", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
