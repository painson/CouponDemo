package com.example.coupondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<Map<String, Object>> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.coupon_list);
        getDatas();

        SimpleAdapter simplead = new SimpleAdapter(this, mDatas,
                R.layout.coupon_item, new String[] {"name", "sn", "descr", "rate" },
                new int[] {R.id.coupon_name, R.id.coupon_sn, R.id.coupon_descr, R.id.coupon_rate});

        mListView.setAdapter(simplead);
    }

    /**
     * 模拟数据
     */
    private void getDatas() {
        mDatas = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("name", "Google优惠券" + i);
            listem.put("sn", "1234 1234 1234 1234");
            listem.put("rate", 8.5);
            listem.put("descr", "最终解释权归painson所有");
            mDatas.add(listem);
        }
    }
}
