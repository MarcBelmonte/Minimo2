package dsa.eetac.upc.edu.minimo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean registered = sharedPref.getBoolean("registered", false);
        Class dest;
        if (!registered) {
            dest= LoginActivity.class;
        } else{
            dest = MainActivity.class;
        }
        Intent intent = new Intent(this, dest);
        startActivity(intent);
        finish();
    }
}

