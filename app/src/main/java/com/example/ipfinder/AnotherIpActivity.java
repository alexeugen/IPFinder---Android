package com.example.ipfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnotherIpActivity extends AppCompatActivity {

    TextView tvAnotherType, tvAnotherISP, tvAnotherCountry, tvAnotherLanguage;
    Button btnSearch, btnSeeMap, btnSetCategory;
    EditText etSearchIp;
    //ArrayList<IpInfo> favorites = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<IpInfo> savedIps;
    Spinner spCategories;

    private NotificationHelper mNotificationHelper;
   // private static ArrayList<IpInfo> favStatic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_ip);

        mNotificationHelper = new NotificationHelper(this);

        findViews();

        IpFinderDB db = IpFinderDB.getInstance(this);
        categories =(ArrayList<Category>) db.getCategoriesDao().getAll();
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(adapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("istoric");
        myRef.push();
        Map<String, IpInfo> ips = new HashMap<>();

        final IpInfo[] searchedIp = new IpInfo[1];

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String adress = "http://api.ipstack.com/" + etSearchIp.getText() +  "?access_key=c45409c19f33a0d8f65c4a625419a6ec&hostname=1";

                try {
                    URL url = new URL(adress);
                    @SuppressLint("StaticFieldLeak") NetworkUtilsAsync nua = new NetworkUtilsAsync() {
                        @Override
                        protected void onPostExecute(final IpInfo ipInfo) {

                            searchedIp[0] = ipInfo;

                           tvAnotherType.setText(ipInfo.getType());
                           tvAnotherISP.setText(ipInfo.getHostName());
                           tvAnotherCountry.setText(ipInfo.getCountry());
                           tvAnotherLanguage.setText(ipInfo.getLanguage());

                           myRef.push();
                           String strId = ipInfo.getIp().replace(".", "");
                           myRef.child("elem" + strId).setValue(ipInfo);

                        }
                    };
                    nua.execute(url);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSeeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnotherIpActivity.this, PreciseLocationActivity.class);
                intent.putExtra("object", searchedIp[0]);
                startActivity(intent);
            }
        });

        btnSetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category selectedCategory = (Category) spCategories.getSelectedItem();
                searchedIp[0].setCategoryId(selectedCategory.getCategoryId());
                db.getIpInfoDao().insert(searchedIp[0]);

                sendNotification("IP salvat", "Ai adaugat IP-ul in categoria " + selectedCategory.getName());
//                Intent intent = new Intent(AnotherIpActivity.this, AllCategoriesActivity.class);
//                startActivity(intent);

            }
        });

    }

    public void sendNotification(String title, String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(title, message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    public void findViews() {
        tvAnotherType = findViewById(R.id.tvAnotherType);
        tvAnotherISP = findViewById(R.id.tvAnotherISP);
        tvAnotherCountry = findViewById(R.id.tvAnotherCountry);
        tvAnotherLanguage = findViewById(R.id.tvAnotherLanguage);
        etSearchIp = findViewById(R.id.etSearchIp);
        btnSearch = findViewById(R.id.btnSearchIp);
        btnSeeMap = findViewById(R.id.btnSeeMap);
        //btnAddToFavorites = findViewById(R.id.btnAddToFavorites);
        spCategories = findViewById(R.id.spCategories);
        btnSetCategory = findViewById(R.id.btnSetCateory);

    }

//    public static ArrayList<IpInfo> getFav() {
//        return favStatic;
//    }

}
