package com.example.pieandhistogram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by LIHUI on 2017/2/21.
 */

public class CountChildAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private String[] types;
    private int[] colors;
    private int[] nums;
    private String[] teams;

    public CountChildAdapter(Context context, String[] types,int[] colors,int[] nums ,String[] teams) {
        this.context = context;
        this.types = types;
        this.colors = colors;
        this.nums = nums;
        this.teams = teams;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teams.length != 0 ? teams.length : 0 ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int maxLength = getMax(nums);
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_alarm_count_child, null);
            holder.tvAlarmName = (TextView) convertView.findViewById(R.id.tv_alarm_type_new);
            holder.tvAlarmColor = (LinearLayout) convertView.findViewById(R.id.tv_alarm_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvAlarmName.setText(teams[position]);
        showView(nums[position], holder.tvAlarmColor, position, maxLength, 5);
        return convertView;
    }

    private class ViewHolder {
        private TextView tvAlarmName;
        private LinearLayout tvAlarmColor;
        private TextView tvAlarmCount;
    }

    public void showView(final int length, final LinearLayout layout, final int position, float maxLength, int value) {
        if (length != 0) {
            CustomHistogram histo = new CustomHistogram(context, length, true, colors[position], maxLength, value);
            layout.addView(histo, new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    context.getResources().getDimensionPixelSize(R.dimen.more_head_texttop)));
        }
    }

    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int x = 1; x < arr.length; x++) {
            if (arr[x] > max)
                max = arr[x];
        }
        return max;
    }

}
