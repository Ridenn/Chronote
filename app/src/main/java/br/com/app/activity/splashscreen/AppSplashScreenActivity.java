package br.com.app.activity.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import java.util.Timer;
import java.util.TimerTask;

import br.com.app.activity.R;
import br.com.app.activity.usuario.UsuarioActivity;

/**
 * Created by Wesley on 01/10/2016.
 */
public class AppSplashScreenActivity extends Activity {

    private final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash_screen);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        iniciarAplicacao();
    }

    private void iniciarAplicacao() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(AppSplashScreenActivity.this, UsuarioActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}