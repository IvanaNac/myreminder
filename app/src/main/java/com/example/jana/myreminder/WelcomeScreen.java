package com.example.jana.myreminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

/**
 * Created by jmagdeska on 9/2/15.
 */
public class WelcomeScreen extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        welcome();
    }

    public void welcome() {
        ImageView imageView = (ImageView)findViewById(R.id.welcomeScreen);
        imageView.setBackgroundResource(R.drawable.icon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(WelcomeScreen.this, Reminders.class);
                startActivity(i);
            }
        }, 5000);
    }

}
