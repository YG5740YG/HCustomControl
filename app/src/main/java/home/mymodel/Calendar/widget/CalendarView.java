package home.mymodel.Calendar.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import newhome.baselibrary.R;

/**
 * Created by zhouyou on 2016/7/25.
 * Class desc:
 *
 * 自定义日历View，可多选
 */
public class CalendarView extends View {

    // 列的数量
    private static final int NUM_COLUMNS    =   7;
    // 行的数量
    private static final int NUM_ROWS       =   6;

    /**
     * 可选日期数据
     */
    private List<String> mOptionalDates = new ArrayList<>();

    /**
     * 已选日期数据
     */
    private List<String> mSelectedDates = new ArrayList<>();

    // 背景颜色
    private int mBgColor = Color.WHITE;
    // 天数默认颜色
    private int mDayNormalColor = Color.BLACK;
    // 天数不可选颜色
    private int mDayNotOptColor = Color.BLUE;
    // 天数选择后颜色
    private int mDayPressedColor = Color.RED;
    // 天数字体大小
    private int mDayTextSize = 16;
    // 是否可以被点击状态
    private boolean mClickable = true;

    private DisplayMetrics mMetrics;
    private Paint mPaint;
    private Paint mPaintBlack;
    private Paint mPaintBackGroundBlue;
    private Paint mPaintBackGroundRed;
    private Paint mPaintBackGroundJu;
    private int mCurYear;
    private int mCurMonth;
    private int mCurDate;

    private int mSelYear;
    private int mSelMonth;
    private int mSelDate;
    private int mColumnSize;
    private int mRowSize;
    private int[][] mDays;

    // 当月一共有多少天
    private int mMonthDays;
    // 当月第一天位于周几
    private int mWeekNumber;
    // 已选中背景Bitmap
//    private Bitmap mBgOptBitmap;
    // 未选中背景Bitmap
//    private Bitmap mBgNotOptBitmap;

    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取手机屏幕参数
        mMetrics = getResources().getDisplayMetrics();
        // 创建画笔
        mPaint = new Paint();
        mPaintBlack=new Paint();
        mPaintBackGroundBlue=new Paint();
        mPaintBackGroundJu=new Paint();
        mPaintBackGroundRed=new Paint();
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        mCurYear    =   calendar.get(Calendar.YEAR);//年
        mCurMonth   =   calendar.get(Calendar.MONTH);//月
        mCurDate    =   calendar.get(Calendar.DATE);//日
        setSelYTD(mCurYear, mCurMonth, mCurDate);

        // 获取背景Bitmap
//        mBgOptBitmap    = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_bg_course_optional);
//        mBgNotOptBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_bg_course_not_optional);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        // 绘制背景
        mPaint.setColor(mBgColor);
        //接口获取数据为蓝色
        mPaintBackGroundBlue.setColor(Color.BLUE);
//        mPaintBackGroundBlue.setStyle(Paint.Style.STROKE);
        //当天颜色设置为橘色
        mPaintBackGroundJu.setColor(Color.parseColor("#ff6700"));
        //点击选择颜色变为红色
        mPaintBackGroundRed.setColor(Color.RED);

        mDays = new int[6][7];
        // 设置绘制字体大小
        mPaint.setTextSize(mDayTextSize * mMetrics.scaledDensity);
        mPaintBlack.setTextSize(mDayTextSize* mMetrics.scaledDensity);
        // 设置绘制字体颜色

        String dayStr;
        // 获取当月一共有多少天
        mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        // 获取当月第一天位于周几
        mWeekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);

        for(int day = 0; day < mMonthDays; day++){
            //绘制文字颜色
            mPaintBlack.setColor(Color.BLACK);
            dayStr = String.valueOf(day + 1);
            int column  =  (day + mWeekNumber - 1) % 7;
            int row     =  (day + mWeekNumber - 1) / 7;
            mDays[row][column] = day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(dayStr)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if(mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                if(day+1<10){
                    canvas.drawCircle(startX+12,startY-15,60,mPaintBackGroundRed);
                }else {
                    canvas.drawCircle(startX + 28, startY - 15, 60, mPaintBackGroundRed);
                }
                mPaintBlack.setColor(Color.WHITE);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
                mSelectedDates.remove(getSelData(mSelYear, mSelMonth, mDays[row][column]));
            }else if(day+1==mCurDate&&mCurMonth==mSelMonth&&mCurYear==mSelYear){
                if(day+1<10) {
                    canvas.drawCircle(startX + 12, startY - 15, 60, mPaintBackGroundJu);
                }else{
                    canvas.drawCircle(startX + 28, startY - 15, 60, mPaintBackGroundJu);
                }
                mPaintBlack.setColor(Color.WHITE);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }
            // 判断是否为接口给的天数
            else if(mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                // 可选，继续判断是否是点击过的
//                if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                    // 没有点击过，绘制默认背景
//                    canvas.drawBitmap(mBgNotOptBitmap, startX - (mBgNotOptBitmap.getWidth() / 3), startY - (mBgNotOptBitmap.getHeight() / 2), mPaint);
//                    mPaint.setColor(mDayNormalColor);
//                }else{
                    // 点击过，绘制点击过的背景
//                    canvas.drawBitmap(mBgOptBitmap, startX- (mBgOptBitmap.getWidth() / 3), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
//                    mPaint.setColor(mDayPressedColor);
//                }
                // 绘制天数
                if(day+1<10){
                    canvas.drawCircle(startX+12,startY-15,60,mPaintBackGroundBlue);
                }else {
                    canvas.drawCircle(startX + 28, startY - 15, 60, mPaintBackGroundBlue);
                }
                mPaintBlack.setColor(Color.WHITE);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }else{
                mPaint.setColor(mDayNotOptColor);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }
        }
    }

    private int downX = 0,downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(!mClickable) return true;

                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if(Math.abs(upX - downX) < 20 && Math.abs(upY - downY) < 20){
                    performClick();
                    onClick((upX + downX) /2, (upY + downY)/2);
                }
                break;
        }
        return true;
    }

    /**
     * 点击事件
     */
    private void onClick(int x, int y){
        int row = y / mRowSize;
        int column = x / mColumnSize;
        setSelYTD(mSelYear, mSelMonth, mDays[row][column]);

        // 判断是否点击过
        boolean isSelected = mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
        // 判断是否可以添加
//        boolean isCanAdd = mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
        if(isSelected){
            mSelectedDates.remove(getSelData(mSelYear, mSelMonth, mSelDate));
        }
//        else if(isCanAdd){
        else{
            mSelectedDates.add(getSelData(mSelYear, mSelMonth, mSelDate));
        }
        invalidate();
        if(mListener != null){
            // 执行回调
            mListener.onClickDateListener(mSelYear, (mSelMonth + 1), mSelDate);
        }
    }

    /**
     * 初始化列宽和高
     */
    private void initSize() {
        // 初始化每列的大小
        mColumnSize = getWidth() / NUM_COLUMNS;
        // 初始化每行的大小
        mRowSize = getHeight() / NUM_ROWS;
    }

    /**
     * 设置可选择日期
     * @param dates 日期数据
     */
    public void setOptionalDate(List<String> dates){
        this.mOptionalDates = dates;
        invalidate();
    }

    /**
     * 设置已选日期数据
     */
    public void setSelectedDates(List<String> dates){
        this.mSelectedDates = dates;
    }

    /**
     * 获取已选日期数据
     */
    public List<String> getSelectedDates(){
        return mSelectedDates;
    }

    /**
     * 设置日历是否可以点击
     */
    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 设置年月日
     * @param year  年
     * @param month 月
     * @param date  日
     */
    private void setSelYTD(int year, int month, int date){
        this.mSelYear   =   year;
        this.mSelMonth  =   month;
        this.mSelDate   =   date;
    }

    /**
     * 设置上一个月日历
     */
    public void setLastMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是1月份，则变成12月份
        if(month == 0){
            year = mSelYear-1;
            month = 11;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month-1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month-1;
        }
        setSelYTD(year,month,day);
        invalidate();
    }

    /**
     * 设置下一个日历
     */
    public void setNextMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是12月份，则变成1月份
        if(month == 11){
            year = mSelYear+1;
            month = 0;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month + 1;
        }
        setSelYTD(year,month,day);
        invalidate();
    }

    /**
     * 获取当前展示的年和月份
     * @return 格式：2016-06
     */
    public String getDate(){
        String data;
        if((mSelMonth + 1) < 10){
            data = mSelYear + "-0" + (mSelMonth + 1);
        }else{
            data = mSelYear + "-" + (mSelMonth + 1);
        }
        return data;
    }

    /**
     * 获取当前展示的日期
     * @return 格式：20160606
     */
    private String getSelData(int year, int month, int date){
        String monty, day;
        month = (month + 1);

        // 判断月份是否有非0情况
        if((month) < 10) {
            monty = "0" + month;
        }else{
            monty = String.valueOf(month);
        }
        // 判断天数是否有非0情况
        if((date) < 10){
            day = "0" + (date);
        }else{
            day = String.valueOf(date);
        }
        return year + monty + day;
    }

    private OnClickListener mListener;

    public interface OnClickListener{
        void onClickDateListener(int year, int month, int day);
    }

    /**
     * 设置点击回调
     */
    public void setOnClickDate(OnClickListener listener){
        this.mListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        recyclerBitmap(mBgOptBitmap);
//        recyclerBitmap(mBgNotOptBitmap);
    }

    /**
     * 释放Bitmap资源
     */
    private void recyclerBitmap(Bitmap bitmap) {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
