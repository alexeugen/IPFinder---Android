package com.example.ipfinder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, IpInfo.class}, version = 1)
public abstract class IpFinderDB extends RoomDatabase {

    static  IpFinderDB instance;
    public static synchronized IpFinderDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, IpFinderDB.class, "ip_finder_db")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }

        return instance;
    }

    public abstract IpInfoDao getIpInfoDao();
    public  abstract CategoriesDao getCategoriesDao();

}
