package com.example.jana.myreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

/**
 * Created by jmagdeska on 9/2/15.
 */
public class WelcomeScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.welcome);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(WelcomeScreen.this,Reminders.class);
                WelcomeScreen.this.startActivity(mainIntent);
                WelcomeScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
