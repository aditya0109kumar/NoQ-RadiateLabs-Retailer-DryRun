package com.aditya.newtest2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {
    private static final String FILE_NAME = "myFile";
    // Splash screen timer
    //Variables
    private static int SPLASH_SCREEN = 5000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image = (ImageView) findViewById(R.id.splashlogo);
        logo = (TextView) findViewById(R.id.splashappname);
        slogan = (TextView) findViewById(R.id.splashslogan);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent1 = new Intent(SplashScreen.this, MainActivity2.class);
            Intent intent2 = new Intent(SplashScreen.this, HomeActivity.class);

            @SuppressLint("WorldReadableFiles") SharedPreferences sharedPreferences = this.getSharedPreferences(FILE_NAME, MODE_WORLD_READABLE);
            String email = sharedPreferences.getString("spemail",null);
            String password = sharedPreferences.getString("sppassword",null);

            if (email == null && password == null )
                {
                    startActivity(intent1);
                }
            else
                {
                    startActivity(intent2);
                }


            finish();
        }, SPLASH_SCREEN);

    }
}