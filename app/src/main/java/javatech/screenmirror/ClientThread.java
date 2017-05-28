package javatech.screenmirror;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;


public class ClientThread extends Thread {

    private static final String DEBUG="debug";
    private static final int PORT =50268;
    private static final String HOST="192.168.0.72";
    private final int TIMEOUT = 1000;
    private Socket clientSocket =null;

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
        Log.i(DEBUG, "before connection");
        if(clientSocket==null || !clientSocket.isConnected())
            clientSocket = new Socket(HOST, PORT);
    }

    private void runClientSocket() throws IOException {
        if(!clientSocket.isClosed()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Log.i(DEBUG, "message from server " + bufferedReader.readLine());
            Log.i(DEBUG, "TUTAJ TESTUJE BO DEBUGGEREM NIE UMIEM");
            bufferedReader.close();
            Log.i(DEBUG, "after connection");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            bufferedWriter.write("hi server\n");
            bufferedWriter.flush();

            bufferedWriter.close();
            bufferedReader.close();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
