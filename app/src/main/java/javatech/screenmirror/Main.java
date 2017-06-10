package javatech.screenmirror;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

    @OnClick(R.id.buttonChangeIP)
    public void changeIP(View view) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.ip_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Change IP", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        host = userInputDialogEditText.getText().toString();
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