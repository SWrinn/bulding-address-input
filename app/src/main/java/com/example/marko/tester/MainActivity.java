package com.example.marko.tester;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMapActivity (View view){
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void sendMessage1(View view) {
        String textMessage = "QR disabled" ;
        Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_LONG).show();
    }

    public void sendMessage2(View view) {
        String textMessage = "Text disabled" ;
        Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_LONG).show();
    }

    public void sendMessage3(View view) {
        String voiceMessage = "Voice disabled" ;
        Toast.makeText(getApplicationContext(), voiceMessage, Toast.LENGTH_LONG).show();
    }
}
