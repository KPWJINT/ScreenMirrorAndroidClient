//package javatech.screenmirror;
//
//
//import android.app.Service;;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import static javatech.screenmirror.ClientThread.MESS_FROM_SERVER;
//import static javatech.screenmirror.Main.IS_CONNECTED;
//
//
//public class ClientService extends Service{
//
//    static final String SEND="send data";
//    private static final int SLEEP_TIME=5000;
//    ClientThread clientThread=null;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        while(IS_CONNECTED) {
//            connect();
//
//            try {
//                Thread.sleep(SLEEP_TIME);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            sendData();
//        }
//        return START_STICKY;
//    }
//
//    public void connect(){
//        clientThread = new ClientThread();
//        clientThread.start();
//    }
//
//    private void sendData(){
//        Log.i("DEBUG", "message from server to send to activity: "+MESS_FROM_SERVER);
//        Intent intent=new Intent();
//        intent.setAction(SEND);
//        intent.putExtra("DATA_PASS", MESS_FROM_SERVER);
//        sendBroadcast(intent);
//        stopSelf();
//    }
//}
