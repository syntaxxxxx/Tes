package fendri.inovatif.latihan.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fendri.inovatif.latihan.MainActivity;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.helper.SessionManager;

public class Splash extends AppCompatActivity {

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        manager = new SessionManager(this);

        runSplash();
    }

    private void runSplash() {
        Thread thread = new Thread() {
            Intent intent = new Intent();
            @Override
            public void run() {
                try {
                    sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    if (manager.isLogin()) {
                        intent = new Intent(Splash.this, MainActivity.class);

                    } else {
                        intent = new Intent(Splash.this, Login.class);
                    }
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
