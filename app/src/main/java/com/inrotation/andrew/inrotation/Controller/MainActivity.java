package com.inrotation.andrew.inrotation.Controller;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.inrotation.andrew.inrotation.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button authenticateButton = (Button) findViewById(R.id.spotifyAuthenticateButton);

        authenticateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                runAuthenticateProcess();
            }
        });
    }

    protected void runAuthenticateProcess() {
        Intent homeIntent = new Intent(this, HomeScreenActivity.class);
        startActivity(homeIntent);
    }


}

