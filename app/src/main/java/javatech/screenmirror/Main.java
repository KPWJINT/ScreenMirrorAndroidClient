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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

    @BindView(R.id.buttonChangeIP)
    TextView buttonChangeIP;

    @BindView(R.id.buttonConnect)
    Button buttonConnect;

    @OnClick(R.id.buttonConnect)
    public void connect(View view) {
        Intent screenshotActivityIntent = new Intent(this, ScreenshotActivity.class);

        Bundle hostBundle = new Bundle();
        hostBundle.putString("HOST", host);
        screenshotActivityIntent.putExtras(hostBundle);

        startActivity(screenshotActivityIntent);
    }

    private String host = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
