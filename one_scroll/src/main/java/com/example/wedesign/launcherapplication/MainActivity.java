package com.example.wedesign.launcherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.Adapter<AllAppAdapter.ViewHolder> adapter = new AllAppAdapter(iconIds, titleStrs);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(adapter);
    }
    
    private int[] iconIds = {
            R.drawable.1,
            R.drawable.2,
            R.drawable.3,
            R.drawable.4,
            R.drawable.5,
            R.drawable.6,
            R.drawable.7,
            R.drawable.8,
            R.drawable.9,
            R.drawable.10,
            R.drawable.11,
            R.drawable.12
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
