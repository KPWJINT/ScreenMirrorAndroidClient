package javatech.screenmirror;


import android.app.Service;;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static javatech.screenmirror.Main.IS_CONNECTED;


public class ClientService extends Service{

    static final String SEND="send data";
    private static final int PORT =50268;
    private static final String HOST="192.168.0.72";
    private static final String DEBUG="debug";


    Socket clientSocket=null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        while(IS_CONNECTED) {
            connect();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
    }



    public void connect(){
        Log.i(DEBUG, "connecting!");
        ClientThread clientThread = new ClientThread();
        clientThread.setDaemon(true);
        clientThread.start();
//        new Thread(){
//            public void run(){
//                try {
//                    Log.i(DEBUG, "before connection");
//                    clientSocket = new Socket(HOST, PORT);
//                    Log.i(DEBUG, "after connection");
//                   // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                   // bufferedWriter.write("hi server\n");
//                   // bufferedWriter.flush();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                    Log.i(DEBUG, "message from server " + bufferedReader.readLine());
//
//                  //  bufferedWriter.close();
//                    bufferedReader.close();
//                    Thread.sleep(1000);
//                    /*
//                for(int i=0; i<10; i++){
//                    try{
//
//                        Thread.sleep(500);
//                        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//                        Intent intent= new Intent();
//                        intent.setAction(SEND);
//                        intent.putExtra("DATA_PASS", "counter "+i);
//
//                        sendBroadcast(intent);
//
//                    }catch(InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    stopSelf();
//                }
//*/
//                } catch ( IOException e){
//                System.out.println("IOException");
//            } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }


}