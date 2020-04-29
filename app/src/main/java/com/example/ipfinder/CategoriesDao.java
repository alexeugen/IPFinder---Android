package com.example.ipfinder;


import android.widget.ListView;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoriesDao {
    @Query("delete from categories")
    public void deteleAll();

    @Delete
    public void delete(Category category);

    @Query("select * from categories")
    public List<Category> getAll();

    @Query("select name from categories")
    public List<String> getCategoriesNames();

    @Insert
    public void addCategory(Category category);

    @Update
    public void updateCategory(Category category);

}
