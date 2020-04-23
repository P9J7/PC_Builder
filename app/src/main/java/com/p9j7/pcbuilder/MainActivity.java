package com.p9j7.pcbuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences shp = this.getPreferences(Context.MODE_PRIVATE);
        Boolean viewType = shp.getBoolean("IS_USING_DARK", false);
        if (viewType) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SharedPreferences shp = this.getPreferences(Context.MODE_PRIVATE);
        Boolean viewType = shp.getBoolean("IS_USING_DARK", false);
        menu.findItem(R.id.darkmode).setChecked(viewType);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            FireMissilesDialogFragment fireMissilesDialogFragment = new FireMissilesDialogFragment();
            fireMissilesDialogFragment.show(getSupportFragmentManager(), null);
            return true;
        } else if (id == R.id.darkmode) {
            SharedPreferences shp = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shp.edit();
            if (item.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("IS_USING_DARK", false);
                item.setChecked(false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("IS_USING_DARK", true);
                item.setChecked(true);
            }
            editor.apply();
        }

        return super.onOptionsItemSelected(item);
    }
}
