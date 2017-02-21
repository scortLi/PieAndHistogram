package com.example.pieandhistogram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.maxLength;
import static com.example.pieandhistogram.CountChildAdapter.getMax;

/**
 * Created by LIHUI on 2017/2/20.
 */

public class CountTypeAdapter extends RecyclerView.Adapter<CountTypeAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private String[] types;
    private int[] colors;
    private int[] nums;
    private String[] teams={"第一组", "第二组", "第三组", "第四组"};

    public CountTypeAdapter(Context context, String[] types, int[] colors, int[] nums) {
        this.context = context;
        this.types = types;
        this.colors = colors;
        this.nums = nums;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_alarm_count, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_alarm_team.setText(types[position % 5]);
        CountChildAdapter adapter = new CountChildAdapter(context ,types ,colors,nums,teams);
        holder.tvAlarmName.setAdapter(adapter);
        setInsertLvHeight(holder ,adapter);
    }

    @Override
    public int getItemCount() {

        return types.length != 0 ? types.length : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_alarm_team;
        private LinearLayout tv_color;
        private ListView tvAlarmName;
        public MyViewHolder(View view) {
            super(view);
            tv_alarm_team = (TextView) view.findViewById(R.id.tv_alarm_team);
            tv_color = (LinearLayout) view.findViewById(R.id.tv_color);
            tvAlarmName = (ListView) view.findViewById(R.id.tv_alarm_type);
        }
    }
    private void setInsertLvHeight(MyViewHolder holder, CountChildAdapter mAdapter) {
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, holder.tvAlarmName);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = holder.tvAlarmName.getLayoutParams();
        params.height = totalHeight + (holder.tvAlarmName.getDividerHeight() * (mAdapter.getCount() - 1));
        holder.tvAlarmName.setLayoutParams(params);
    }
}

