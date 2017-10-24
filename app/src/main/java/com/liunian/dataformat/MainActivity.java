package com.liunian.dataformat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.liunian.dataformat.util.Long2DateUtil;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text1);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        textView.setText("当前时刻为：" + Long2DateUtil.getData(date.getTime())
                + "\n" + "转化后的时间：" + Long2DateUtil.getDateStr4WeekAndTime(date.getTime())
                + "\n" + "前一天：" + Long2DateUtil.getDateStr4WeekAndTime(calendar.getTimeInMillis()));
    }
}
