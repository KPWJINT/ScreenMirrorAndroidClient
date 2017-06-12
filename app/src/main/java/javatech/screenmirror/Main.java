package javatech.screenmirror;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

    public static final String MY_PREFERENCES = "MyPrefs" ;
    public static final String HOST_PREF_NAME = "HOST";
    public static final String NO_HOST_VALUE = "No host address";
    private SharedPreferences sharedPreferences;

    @OnClick(R.id.buttonChangeIP)
    public void changeIP(View view) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.ip_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        String host = sharedPreferences.getString(HOST_PREF_NAME, NO_HOST_VALUE);
        if(host.equals(NO_HOST_VALUE))
        {
            userInputDialogEditText.setText("No IP saved");
        }else
        {
            userInputDialogEditText.setText(host);
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Change IP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        String host = userInputDialogEditText.getText().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(HOST_PREF_NAME, host);
                        editor.commit();

                        Toast.makeText(Main.this, "New IP = " + host, Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    @OnClick(R.id.buttonConnect)
    public void connect(View view) {
        Intent screenshotActivityIntent = new Intent(this, ScreenshotActivity.class);

        String host = sharedPreferences.getString(HOST_PREF_NAME, NO_HOST_VALUE);
        if(host.equals(NO_HOST_VALUE))
            Toast.makeText(Main.this, "Please set IP address", Toast.LENGTH_SHORT).show();
        else
        {
            Bundle hostBundle = new Bundle();
            hostBundle.putString(HOST_PREF_NAME, host);
            screenshotActivityIntent.putExtras(hostBundle);

            startActivity(screenshotActivityIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }
}