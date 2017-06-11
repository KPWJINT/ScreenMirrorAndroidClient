package javatech.screenmirror;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.BindView;

public class ScreenshotActivity extends Activity {

    @BindView(R.id.imageViewScreenshot)
    ImageView imageViewScreenshot;

    private BroadcastReceiver broadcastReceiver;
    private ClientThread clientThread = null;
    private String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        ButterKnife.bind(this);
        host = getIntent().getExtras().getString("HOST");

        broadcastReceiver = createBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("com.javatech.screenshot"));

        startClientThread(host);
    }

    private void startClientThread(String host)
    {
        clientThread = new ClientThread(this, host);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateResults(intent.getByteArrayExtra("result"));
            }
        };
    }

    private void updateResults(byte[] screenshotInByte)
    {
        long startTime = System.currentTimeMillis();

        Bitmap bitmap = BitmapFactory.decodeByteArray(screenshotInByte, 0, screenshotInByte.length);
        imageViewScreenshot.setImageBitmap(bitmap);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
        clientThread.stopClient();
        super.onDestroy();
    }
}
