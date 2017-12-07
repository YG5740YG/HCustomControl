package SimpleControls.MBlueToothTool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lvrenyang.io.Canvas;

import java.io.UnsupportedEncodingException;

import SimpleControls.MBlueToothTool.Print.PrintUitil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import yg.customcontrol.com.mylibrary.R;

/**
 *
 * @author Administrator
 * @date 2017/12/6
 */

@RuntimePermissions
public class PrintTest extends Activity {
    Context mContext;
    TextView startPrinter;
    MPrinterView mMPrinterView;
    LinearLayout content;
    Canvas mCanvas;
    /**
     * 开启定位权限
     */
    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void openPermission() {

    }
    /**
     * 请求定位权限，蓝牙需要的一个权限：当前设备可以接收到基站的服务信号，便可获得位置信息
     */
    @OnShowRationale({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void requestLocationPermission(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRequestDeny() {
        Toast.makeText(mContext, "权限获取失败。。", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION})
    void onRequestLocationNeverAskAgain() {
        new AlertDialog.Builder(mContext)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(PrintTest.this, new String[]{Manifest.permission
                                        .ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                                10004);
                    }
                })
                .setNegativeButton("考虑一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false)
                .setMessage("您已禁止定位权限，现在去设置开启")
                .show();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mMPrinterView =new MPrinterView(mContext,this);
        content=(LinearLayout)findViewById(R.id.content);
        startPrinter=(TextView)findViewById(R.id.start_printer);
        content.addView(mMPrinterView.getContentView());
        mMPrinterView.setData();
        mCanvas= mMPrinterView.getCanvas();
        startPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<3;i++) {
                    initCaver();
                    mMPrinterView.startPrinter(mCanvas);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMPrinterView.onDestroy();
    }
    public void initCaver(){
        CanvsModel orderPartPrintByCityMatchMode = new CanvsModel();
        orderPartPrintByCityMatchMode.setWeight(10 + "");
        orderPartPrintByCityMatchMode.setDownGoodsStation(11 + "");
        orderPartPrintByCityMatchMode.setGoodsName("test");
        orderPartPrintByCityMatchMode.setNumIndex(12 + "");
        orderPartPrintByCityMatchMode.setOrderPartId(13 + "");
        orderPartPrintByCityMatchMode.setPackageNo(14 + "");
        orderPartPrintByCityMatchMode.setReceiveAddress(15 + "");
        orderPartPrintByCityMatchMode.setReceiveName(16 + "");
        orderPartPrintByCityMatchMode.setReceivePhone(17 + "");
        orderPartPrintByCityMatchMode.setStationNo(18 + "");
        orderPartPrintByCityMatchMode.setTotalNumber(19 + "");
        orderPartPrintByCityMatchMode.setUpGoodsStation(20 + "");
        try {
            mCanvas= PrintUitil.PrintStringTest(mContext,mCanvas,orderPartPrintByCityMatchMode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

