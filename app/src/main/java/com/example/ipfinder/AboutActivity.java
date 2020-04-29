package com.example.ipfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class AboutActivity extends AppCompatActivity {

    Button btnSearchIp;
    Switch switchMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        switchMode = findViewById(R.id.switchMode);
        SharedPreferences pref = getSharedPreferences("mode", Activity.MODE_PRIVATE);
        if(pref.getInt("theme", 0) == Utils.MODE_NIGHT){
            switchMode.setChecked(true);
        }

        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   SharedPreferences pref = getSharedPreferences("mode", Activity.MODE_PRIVATE);
                   SharedPreferences.Editor editor = pref.edit();
                   editor.putInt("theme", Utils.MODE_NIGHT);
                   editor.apply();
                   Log.e("THEME", pref.getInt("theme", 0) + "***");
                   Utils.changeToTheme(AboutActivity.this, pref.getInt("theme", 0));
                   //Utils.onActivityCreateSetTheme(AboutActivity.this);

                } else {
                    SharedPreferences pref = getSharedPreferences("mode", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("theme", Utils.MODE_DEFAULT);
                    editor.apply();
                    Log.e("THEME", pref.getInt("theme", 0) + "***");
                    Utils.changeToTheme(AboutActivity.this, pref.getInt("theme", 0));
                    //Utils.onActivityCreateSetTheme(AboutActivity.this);
                }
            }
        });

        btnSearchIp = findViewById(R.id.btnSearchIp);
        btnSearchIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AnotherIpActivity.class);
                startActivity(intent);
            }
        });
    }
}
