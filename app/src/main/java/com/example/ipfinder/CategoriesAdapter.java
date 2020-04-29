package com.example.ipfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> categories;



    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.list_view_categories, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Category category = categories.get(position);
        Log.d("D", viewHolder.tvCategoryName.getText().toString());
        viewHolder.tvCategoryName.setText(category.getName());
        viewHolder.btnDeleteCategory.setTag(category);
        viewHolder.btnEditCategory.setTag(category);


        final IpFinderDB db = IpFinderDB.getInstance(context);

        viewHolder.btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.remove(v.getTag());
                db.getCategoriesDao().delete((Category) v.getTag());
                notifyDataSetChanged();

            }
        });

        viewHolder.tvCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TEXT", "HI");
                Category category = (categories.get(position));
                Intent intent = new Intent(context, FavoritesActivity.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });



        viewHolder.btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                alert.setMessage("Editeaza categoria");
                alert.setTitle("Introdu numele");
                edittext.setText(v.getTag().toString());

                alert.setView(edittext);

                alert.setPositiveButton("Salveaza", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Category category = new Category();
                        category.setCategoryId(((Category)(v.getTag())).getCategoryId());
                        category.setName(edittext.getText().toString());
                        for(Category elem : categories) {
                            if (elem.getCategoryId() == category.getCategoryId()){
                                elem.setName(category.getName());
                            }
                        }

                        db.getCategoriesDao().updateCategory(category);
                        notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Renunta", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();


            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        Button btnDeleteCategory, btnEditCategory;

        public ViewHolder(@NonNull View convertView) {
            super(convertView);
            this.tvCategoryName = convertView.findViewById(R.id.tvCategoryName);
            this.btnDeleteCategory = convertView.findViewById(R.id.btnDeleteCategory);
            this.btnEditCategory = convertView.findViewById(R.id.btnEditCategory);
        }
    }
}
