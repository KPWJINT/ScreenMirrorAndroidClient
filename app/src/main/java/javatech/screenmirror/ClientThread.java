package javatech.screenmirror;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread extends Thread {

    private static final String DEBUG="debug";
    private static final int PORT =50268;
    private static final String HOST="192.168.0.10";
    private final int TIMEOUT = 1000;
    private Socket clientSocket =null;
    static String MESS_FROM_SERVER=null;

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

    private void closeClient() throws IOException {
            clientSocket.close();
    }

    private void createClientSocket(int timeout) throws IOException {
        if(clientSocket==null) {
            clientSocket = new Socket(HOST, PORT);
        }
    }

    private void runClientSocket() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        MESS_FROM_SERVER = bufferedReader.readLine();
        bufferedReader.close();

    }
}
