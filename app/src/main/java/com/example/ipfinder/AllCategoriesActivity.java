package com.example.ipfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {
    ArrayList<Category> categories;
    CategoriesAdapter adapter;
    RecyclerView lvCategories;
    Button btnAddCategory, btnDeleteCategory, btnSeeChart;
    EditText etAddCategory;


    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        findViews();

        final IpFinderDB db = IpFinderDB.getInstance(this);
        //db.getCategoriesDao().deteleAll();

        categories = (ArrayList<Category>) db.getCategoriesDao().getAll();
        adapter = new CategoriesAdapter(this, categories);
//        int resId = R.anim.layout_animation_fall_down;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
//        lvCategories.setLayoutAnimation(animation);
        lvCategories.setAdapter(adapter);
        lvCategories.setLayoutManager(new LinearLayoutManager(this));
//        runLayoutAnimation(lvCategories);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddCategory.getText().toString() != null) {
                    Category category = new Category();
                    category.setName(etAddCategory.getText().toString());
                    db.getCategoriesDao().addCategory(category);
                    categories.add(category);
                    for(Category elem : categories) {
                        Log.d("myLog", elem.toString());
                    }
                    etAddCategory.setText("");
                }

                adapter.notifyDataSetChanged();
                lvCategories.setAdapter(adapter);

            }
        });


        btnSeeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategoriesActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });

    }


//    private void runLayoutAnimation(final RecyclerView recyclerView) {
//        final Context context = recyclerView.getContext();
//        final LayoutAnimationController controller =
//                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
//        recyclerView.setLayoutAnimation(controller);
//        recyclerView.getAdapter().notifyDataSetChanged();
//        recyclerView.scheduleLayoutAnimation();
//    }

    @Override
    protected void onStop() {
        super.onStop();
        StringBuilder sb = new StringBuilder();
        for(Category elem : categories) {
            sb.append(elem.toString());
            sb.append(System.getProperty("line.separator"));
        }

        writeToFile(sb.toString(), this);
        // Log.e("fileContent", readFromFile(this));
    }

    public void findViews() {
        lvCategories = findViewById(R.id.lvCategories);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        etAddCategory = findViewById(R.id.etAddCategory);
        btnDeleteCategory = findViewById(R.id.btnDeleteCategory);
        btnSeeChart = findViewById(R.id.btnSeeChart);
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("categories.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.e("good", data);
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("categories.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}

