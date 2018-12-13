package com.example.wedesign.launcherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {
    private AllAppsRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.Adapter<AllAppsAdapter.ViewHolder> adapter = new AllAppsAdapter(iconIds, titleStrs);

        mRecyclerView = (AllAppsRecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private int[] iconIds = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.serven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.twelve
    };

    private String[] titleStrs = {
            "电话",
            "时钟",
            "电子邮件",
            "Search",
            "日历",
            "设置",
            "图库",
            "音乐",
            "QQ输入法",
            "Android键盘",
            "计算器",
            "货车导航"
    };



}
