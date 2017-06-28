package home.mymodel.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import home.mymodel.Calendar.widget.CalendarView;
import home.mymodel.R;

public class CalendarActivity extends Activity implements View.OnClickListener {

    private TextView mTextSelectMonth;
    private ImageButton mLastMonthView;
    private ImageButton mNextMonthView;
    private CalendarView mCalendarView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);
        mTextSelectMonth = (TextView) findViewById(R.id.txt_select_month);
        mLastMonthView = (ImageButton) findViewById(R.id.img_select_last_month);
        mNextMonthView = (ImageButton) findViewById(R.id.img_select_next_month);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mLastMonthView.setOnClickListener(this);
        mNextMonthView.setOnClickListener(this);

        // 初始化可选日期
        initData();

        // 设置可选日期
        mCalendarView.setOptionalDate(mDatas);
        // 设置已选日期
//        mCalendarView.setSelectedDates(mDatas);
        // 设置不可以被点击
//        mCalendarView.setClickable(false);

        // 设置点击事件
        mCalendarView.setOnClickDate(new CalendarView.OnClickListener() {
            @Override
            public void onClickDateListener(int year, int month, int day) {
                Toast.makeText(getApplication(), year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();

                // 获取已选择日期
                List<String> dates = mCalendarView.getSelectedDates();
                for (String date : dates) {
                    Log.e("test", "date: " + date);
                }
            }
        });

        mTextSelectMonth.setText(mCalendarView.getDate());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("20170601");
        mDatas.add("20170602");
        mDatas.add("20170603");
        mDatas.add("20170616");
        mDatas.add("20170617");
        mDatas.add("20170626");
        mDatas.add("20170610");
        mDatas.add("20170611");
        mDatas.add("20170612");
        mDatas.add("20170512");
        mDatas.add("20170514");
        mDatas.add("20170515");
        mDatas.add("20170715");
        mDatas.add("20170719");
        mDatas.add("20170722");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.img_select_last_month) {
            mCalendarView.setLastMonth();
            mTextSelectMonth.setText(mCalendarView.getDate());

        } else if (i == R.id.img_select_next_month) {
            mCalendarView.setNextMonth();
            mTextSelectMonth.setText(mCalendarView.getDate());
        }
    }
}
