package newhome.baselibrary.MyHttp.OkHttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedHashMap;

import newhome.baselibrary.MyViewI.DataResponseT;
import newhome.baselibrary.R;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class MyHttpTese extends Activity {

    TextView mButton;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myhttptest);
        mButton=(TextView)findViewById(R.id.mSubmitButton);
        mTextView=(TextView)findViewById(R.id.mText) ;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                url:https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetLimitBuy&cityid=0
//               new MyHttp().myHttp("http://www.weather.com.cn/adat/cityinfo/101190404.html").get(new DataResponseT() {
//               new MyHttp().myHttp("https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetLimitBuy&cityid=0").get(new DataResponseT() {
//                   @Override
//                   public void onSucc(Object response) {
//                       mTextView.setText(response.toString());
//                   }
//                   @Override
//                   public void onFail(String error) {
//                       mTextView.setText("error");
//                   }
//               });
                LinkedHashMap<String,String>linkedHashMap=new LinkedHashMap<String, String>();
                linkedHashMap.put("act","GetLimitBuy");
                linkedHashMap.put("cityid","0");
                new MyHttp().myHttp("https://m.9ji.com/app/2_0/ProductSearch.aspx",linkedHashMap).get(new DataResponseT() {
                    @Override
                    public void onSucc(Object response) {
                        mTextView.setText(response.toString());
                    }
                    @Override
                    public void onFail(String error) {
                        mTextView.setText("error");
                    }
                });
            }
        });

    }
}
