package com.example.ipfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnOptionCurrent, btnOptionAnother, btnAbout, btnSeeFavorites, btnSeeIstoric;
    ArrayList<IpInfo> istoricList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("mode", Activity.MODE_PRIVATE);
        Utils.getTheme(pref.getInt("theme", 0));
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        btnOptionCurrent = findViewById(R.id.btnOptionCurrent);
        btnOptionCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CurrentIpActivity.class);
                startActivity(intent);
            }
        });

        btnOptionAnother = findViewById(R.id.btnOptionAnother);
        btnOptionAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnotherIpActivity.class);
                startActivity(intent);
            }
        });

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

//        btnSeeFavorites = findViewById(R.id.btnSeeFavorites);
//        btnSeeFavorites.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
//                startActivity(intent);
//            }
//        });

        btnSeeFavorites = findViewById(R.id.btnSeeFavorites);
        btnSeeFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllCategoriesActivity.class);
                startActivity(intent);
            }
        });

        btnSeeIstoric = findViewById(R.id.btnSeeIstoric);
        btnSeeIstoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataSnapshot ipsSnapshot = dataSnapshot.child("istoric");
                        Iterable<DataSnapshot> istoricChildren = ipsSnapshot.getChildren();
                        istoricList.clear();
                        for (DataSnapshot ds : istoricChildren) {
                            IpInfo ipInfo = ds.getValue(IpInfo.class);
                            istoricList.add(ipInfo);
                        }

                        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);

                        intent.putExtra("istoric", istoricList);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("RetrieveDataFailed", databaseError.toException());
                    }
                });




            }
        });

    }
}
