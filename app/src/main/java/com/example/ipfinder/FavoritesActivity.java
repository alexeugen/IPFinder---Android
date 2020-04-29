package com.example.ipfinder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
   // ArrayList<IpInfo> favorites;
    IpInfoAdapter adapter;
    ListView lvFavorites;
    ArrayList<IpInfo> categoryElements;
    TextView tvTitle;
    Category category;

    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        findViews();

        IpFinderDB db = IpFinderDB.getInstance(this);
        //favorites = AnotherIpActivity.getFav();
        //favorites = (ArrayList<IpInfo>) getIntent().getSerializableExtra("favorites");
//        if (favorites == null) {
//           favorites = AnotherIpActivity.getFav();
//        }

        //adapter = new IpInfoAdapter(this, favorites);

        category = null;
        category = (Category) getIntent().getSerializableExtra("category");

        if (category != null) {
            tvTitle.setText(category.getName());
            categoryElements = (ArrayList<IpInfo>) db.getIpInfoDao().getByCategory(category.getCategoryId());

            adapter = new IpInfoAdapter(this, categoryElements);
            lvFavorites.setAdapter(adapter);
        }
        else {
            tvTitle.setText("Istoric");
            categoryElements = (ArrayList<IpInfo>) getIntent().getSerializableExtra("istoric");

            adapter = new IpInfoAdapter(this, categoryElements);
            lvFavorites.setAdapter(adapter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        StringBuilder sb = new StringBuilder();
        sb.append(tvTitle.getText());
        for(IpInfo elem : categoryElements) {
            sb.append(elem.toString());
            sb.append("\n");
        }

        writeToFile(sb.toString(), this);

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("lastAccessed.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.e("good", data);
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void findViews() {
        tvTitle = findViewById(R.id.tvFavoritesTitle);
        lvFavorites = findViewById(R.id.lvFavorites);
    }

}
