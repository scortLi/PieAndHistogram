package com.example.pieandhistogram;

import android.content.Context;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class MainActivity extends AppCompatActivity {
    private LinearLayout piechart;
    private RecyclerView lv_count;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private CountTypeAdapter countTypeAdapter;
    private DividerItemDecoration dividerItemDecoration;
    // 画饼状图
    private GraphicalView mPieChartView;
    private int color;
    private int[] nums = { 15, 30, 45, 60 ,90};
    private String[] names = { "红队（60）", "绿队（45）", "蓝队（30）", "橙队（15）" };
    private String[] types = { "跳   绳", "拔   河", "篮   球", "羽毛球", "乒乓球" };
    // 饼状图每个板块的颜色
    private int[] colors = {R.color.control_alarm_select_two_color, R.color.control_alarm_select_three_color,
            R.color.control_alarm_select_four_color, R.color.control_alarm_select_one_color,
            R.color.control_alarm_select_five_color, R.color.control_alarm_select_six_color,
            R.color.control_alarm_select_seven_color, R.color.control_alarm_select_eight_color};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {
        piechart = (LinearLayout) findViewById(R.id.piechart);
        lv_count = (RecyclerView) findViewById(R.id.lv_count);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lv_count.setLayoutManager(new LinearLayoutManager(this));
        lv_count.addItemDecoration(new DividerItemDecoration(MainActivity.this , DividerItemDecoration.VERTICAL_LIST));
    }

    private void initData() {
        paintingPieChart();
        homeAdapter = new HomeAdapter(MainActivity.this ,names);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        countTypeAdapter = new CountTypeAdapter(MainActivity.this , types,colors,nums);
        lv_count.setAdapter(countTypeAdapter);
        countTypeAdapter.notifyDataSetChanged();


    }
    /**
     * 画饼状图
     */
    public void paintingPieChart() {
        CategorySeries series = new CategorySeries("表格");
        for (int i = 0; i < names.length; i++) {
            series.add(names[i], nums[i]);
        }
        DefaultRenderer renderer = new DefaultRenderer();
        // 设置背景颜色
        renderer.setBackgroundColor(Color.WHITE);
        // 显示数据
        // renderer.setDisplayValues(false);
        // 设置 轴标签字体大小： 15 renderer.setLabelsTextSize(15); // 图例字体大小： 15
        // 不显示标签
        renderer.setShowLabels(false);
        // renderer.setLabelsColor(Color.BLACK);
        renderer.setLabelsTextSize(20);
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);// 设置是否可以平移
        // 不显示底部说明
        renderer.setShowLegend(false);
        // 图形 4边的边距
        renderer.setMargins(new int[] { R.dimen.control_alarm_text_top, R.dimen.control_alarm_text_top, 50,
                R.dimen.common_back_marginleft });
        // 为饼状图赋予颜色
        for (int i = 0; i < names.length; i++) {
            color = colors[i];
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(getResources().getColor(color));
            renderer.addSeriesRenderer(r);
        }
        // 集合饼状图各种属性于mPieChartView
        mPieChartView = ChartFactory.getPieChartView(this, series, renderer);
        // piechar=(LinearLayout) findViewById(R.id.piechart);
        piechart.removeAllViews();
        // piechar.setBackgroundColor(Color.BLACK);
        // 将mPieChartView加到piechar视图中
        piechart.addView(mPieChartView, new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
