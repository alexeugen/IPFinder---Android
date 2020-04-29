package com.example.ipfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IpInfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<IpInfo> favorites;

    public IpInfoAdapter(Context context, ArrayList<IpInfo> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int position) {
        return favorites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_favorites, parent, false);

            viewHolder.tvIp = convertView.findViewById(R.id.tvIp);
            viewHolder.tvType = convertView.findViewById(R.id.tvType);
            viewHolder.tvISP = convertView.findViewById(R.id.tvISP);
            viewHolder.tvContinent = convertView.findViewById(R.id.tvContinet);
            viewHolder.tvCountry = convertView.findViewById(R.id.tvCountry);
            viewHolder.tvCity = convertView.findViewById(R.id.tvCity);
            viewHolder.tvLanguage = convertView.findViewById(R.id.tvLanguage);

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        setFadeAnimation(convertView);
        IpFinderDB db = IpFinderDB.getInstance(context);

        IpInfo ipInfo = favorites.get(position);
        String deleteEmoji = context.getResources().getString(R.string.delete_emoji);
        viewHolder.tvIp.setText(ipInfo.getIp() + "   " + deleteEmoji);
        viewHolder.tvIp.setTag(ipInfo);
        viewHolder.tvType.setText(ipInfo.getType());
        viewHolder.tvISP.setText(ipInfo.getHostName());
        viewHolder.tvContinent.setText(ipInfo.getContinent());
        viewHolder.tvCountry.setText(ipInfo.getCountry());
        viewHolder.tvCity.setText(ipInfo.getCity());
        viewHolder.tvLanguage.setText(ipInfo.getLanguage());

        viewHolder.tvIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorites.remove(v.getTag());
                db.getIpInfoDao().delete((IpInfo) v.getTag());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        TextView tvIp, tvType, tvISP, tvContinent, tvCountry, tvCity, tvLanguage;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }
}
