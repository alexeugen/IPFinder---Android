package com.example.ipfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CurrentIpActivity extends AppCompatActivity {


    TextView tvCurrentIp, tvCurrentType, tvCurrentISP, tvCurrentCountry,
    tvCurrentLanguage;
    Button btnSeeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ip);

        findViews();
        //final String adress = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
        final String adress = "http://api.ipstack.com/check?access_key=c45409c19f33a0d8f65c4a625419a6ec&hostname=1";

        try {
            URL url = new URL(adress);

            @SuppressLint("StaticFieldLeak") NetworkUtilsAsync nua = new NetworkUtilsAsync() {
                @Override
                protected void onPostExecute(final IpInfo ipInfo) {

                    tvCurrentIp.setText(ipInfo.getIp());
                    tvCurrentCountry.setText(ipInfo.getCountry());
                    tvCurrentISP.setText(ipInfo.getHostName());
                    tvCurrentType.setText(ipInfo.getType());
                    tvCurrentLanguage.setText(ipInfo.getLanguage());

                    btnSeeMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CurrentIpActivity.this, PreciseLocationActivity.class);
                            intent.putExtra("object", ipInfo);
                            startActivity(intent);
                        }
                    });
                }
            };
            nua.execute(url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public void findViews() {
        tvCurrentIp = findViewById(R.id.tvCurrentIP);
        tvCurrentCountry = findViewById(R.id.tvCountry);
        tvCurrentISP = findViewById(R.id.tvCurrentISP);
        tvCurrentType = findViewById(R.id.tvCurrentType);
        tvCurrentLanguage = findViewById(R.id.tvLanguage);
        btnSeeMap = findViewById(R.id.btnSeeMap);

    }
}
