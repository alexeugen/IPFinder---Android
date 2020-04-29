package com.example.ipfinder;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class NetworkUtilsAsync extends AsyncTask<URL, Void, IpInfo> {


    @Override
    protected IpInfo doInBackground(URL... urls) {
        IpInfo ipInfo;
        String fullString = "";
        try{

            HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
            conn.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);

            }

            fullString = response.toString();
            in.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        return getIpInfo(fullString);
    }

    public String getIP(String str){
        String ip = "";
        try {
            JSONObject json = new JSONObject(str);
            if (json != null) {
                ip += json.getString("ip");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ip;
    }

    public IpInfo getIpInfo(String str) {
        IpInfo ipInfo = new IpInfo();
        try {
            JSONObject json = new JSONObject(str);
            if (json != null) {
                ipInfo.setIp(json.getString("ip"));
                ipInfo.setType(json.getString("type"));
                ipInfo.setHostName(json.getString("hostname"));
                ipInfo.setCity(json.getString("city"));
                ipInfo.setContinent(json.getString("continent_name"));
                ipInfo.setCountry(json.getString("country_name"));
                ipInfo.setLat(json.getDouble("latitude"));
                ipInfo.setLog(json.getDouble("longitude"));

                JSONObject location = json.getJSONObject("location");
                JSONArray languages = location.getJSONArray("languages");
                JSONObject lang = (JSONObject) languages.get(0);
                ipInfo.setLanguage(lang.getString("name"));
                ipInfo.setFlag("http://www.geognos.com/api/en/countries/flag/" + json.getString("country_code") + ".png");
//213.233.103.141
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return ipInfo;
    }

}
