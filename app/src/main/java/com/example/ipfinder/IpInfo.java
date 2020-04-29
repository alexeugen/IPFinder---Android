package com.example.ipfinder;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "ipInfo", foreignKeys = @ForeignKey(entity = Category.class,
                                                        parentColumns = "categoryId",
                                                        childColumns = "categoryId",
                                                        onDelete = ForeignKey.CASCADE))
public class IpInfo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Ip;
    private String type;
    private String continent;
    private String country;
    private String city;
    private String flag;
    private String hostName;
    private String language;
    private double lat;
    private double log;

    private int categoryId;

    public IpInfo() {
        Ip = "n/a";
        type = "n/a";
        continent = "n/a";
        country = "n/a";
        city = "n/a";
        flag = "n/a";
        hostName = "n/a";
        lat = 0.0;
        log = 0.0;
        language = "n/a";
        categoryId = -1;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }



    @Override
    public String toString() {
        return "IpInfo{" +
                "Ip='" + Ip + '\'' +
                ", type='" + type + '\'' +
                ", continent='" + continent + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", flag='" + flag + '\'' +
                ", hostName='" + hostName + '\'' +
                ", language='" + language + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                '}';
    }
}


