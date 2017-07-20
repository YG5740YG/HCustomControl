package home.mymodel.Criminal;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import newhome.baselibrary.Single.SingletonOne;

/**
 * Created by Administrator on 2017/7/20 0020.
 */
//使用了单例
public class CrimeLabData {
    private static CrimeLabData sCrimeLabData;
    private static List<CrimeData> mCrimeDatas;
    public static CrimeLabData get(Context context){
        if(sCrimeLabData==null){
            synchronized (SingletonOne.class){
                if(null==sCrimeLabData){
                    sCrimeLabData=new CrimeLabData(context);
                }
            }
        }
        return sCrimeLabData;
    }

    private CrimeLabData(Context context){
        mCrimeDatas=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CrimeData crime = new CrimeData();
            crime.setDate(new Date());
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            crime.setId(UUID.randomUUID());
            mCrimeDatas.add(crime);
        }
    }

    public static CrimeData getCrimeLabData(UUID id) {
        for (CrimeData crimeData : mCrimeDatas) {
            if (crimeData.getId().equals(id)) {
                return crimeData;
            }
        }
        return null;
    }
    public List<CrimeData> getCrimeDatas() {
        return mCrimeDatas;
    }
}
