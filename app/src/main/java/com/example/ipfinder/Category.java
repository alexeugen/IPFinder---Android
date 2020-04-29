package com.example.ipfinder;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity (tableName = "categories")
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int categoryId;
    private String name;


    public Category() {
        name ="n/a";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getCategoryId() == category.getCategoryId() &&
                getName().equals(category.getName());
    }

}


