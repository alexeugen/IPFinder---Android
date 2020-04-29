package com.example.ipfinder;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IpInfoDao {
    @Delete()
    public void delete(IpInfo ip);

    @Query("delete from ipInfo")
    public void deleteAll();

    @Query("select * from ipInfo")
    public List<IpInfo> getAll();

    @Query("select * from ipInfo where categoryId=:id")
    public List<IpInfo> getByCategory (int id);

    @Insert
    public void insert(IpInfo ip);

    @Insert
    public void insert(List<IpInfo> listIp);

    @Update
    public void update(IpInfo info);
}
