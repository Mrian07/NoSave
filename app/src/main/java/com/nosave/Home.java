package com.nosave;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

public class Home extends AppCompatActivity {

    private static final int COUNTRY_CODE = 91;

    private EditText phoneNumber;
    private EditText messageInput;

    //Pointing toward menu xml file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_help) {
            //Go to Menu_Help Activity
            Intent intent = new Intent(getApplicationContext(), Menu_Help.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Normal Start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_bg));

        phoneNumber = findViewById(R.id.enter_Number);
        messageInput = findViewById(R.id.enter_Message);
        Button sendToWhatsApp = findViewById(R.id.sendToWhatsApp);
        Button sendToWhatsApp4B = findViewById(R.id.sendToWhatsApp4B);

        //On-Click Code
        sendToWhatsApp.setOnClickListener(v -> {

            //Validation Check
            String number = phoneNumber.getText().toString().trim();

            if (number.isEmpty() || number.length() < 10) {
                phoneNumber.setError("Aaa hah! Check the number please");
                phoneNumber.requestFocus();
                return;
            }

            String message = messageInput.getText().toString().trim();

            //Send data to WhatsApp
            String url;
            try {

                PackageManager pm = getApplicationContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);

                Intent i = new Intent(Intent.ACTION_VIEW);
                url = "https://api.whatsapp.com/send?phone=" + COUNTRY_CODE + number + "&text=" + URLEncoder.encode(message, "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                startActivity(i);

                //To play sound on button click
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.onclick_sound);
                mediaPlayer.start();

                phoneNumber.getText().clear();
                messageInput.getText().clear();

            } catch (PackageManager.NameNotFoundException | UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "WhatsApp not installed or something went wrong!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        });

        sendToWhatsApp4B.setOnClickListener(v -> {
            //Validation Check
            String number = phoneNumber.getText().toString().trim();

            if (number.isEmpty() || number.length() < 10) {
                phoneNumber.setError("Aaa hah! Check the number please");
                phoneNumber.requestFocus();
                return;
            }

            String message = messageInput.getText().toString().trim();

            //Send data to WhatsApp Business
            String url;
            try {
                PackageManager pm = getApplicationContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_ACTIVITIES);

                Intent i = new Intent(Intent.ACTION_VIEW);
                url = "https://api.whatsapp.com/send?phone=" + COUNTRY_CODE + number + "&text=" + URLEncoder.encode(message, "UTF-8");
                i.setPackage("com.whatsapp.w4b");
                i.setData(Uri.parse(url));
                startActivity(i);

                //To play sound on button click
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.onclick_sound);
                mediaPlayer.start();

                phoneNumber.getText().clear();
                messageInput.getText().clear();

            } catch (PackageManager.NameNotFoundException | UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "WhatsApp Business not installed or something went wrong!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        });
    }
}