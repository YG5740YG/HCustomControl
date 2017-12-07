package SimpleControls.MBlueToothTool.Print;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.Canvas;
import com.lvrenyang.io.IOCallBack;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import MyTools.BaseAction;
import MyTools.CommonTools;
import SimpleControls.MBlueToothTool.MBlueTooth;
import SimpleControls.MScrollControl.DrawerAdapter;
import SimpleControls.MScrollControl.DrawerContentModel;
import me.grantland.widget.AutofitTextView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import SimpleControls.MBlueToothTool.CanvsModel;
import yg.customcontrol.com.mylibrary.R;

/**
 * Created by Administrator on 2017/12/4.
 */
@RuntimePermissions
public class PrintTest extends Activity implements DrawerAdapter.ItemClickListen,IOCallBack, MBlueTooth
        .BlueToothOpenListen {
    Context mContext;
    AutofitTextView mGetSearchBlueToothList;
    AutofitTextView mGetHistoryBlueToothList;
    AutofitTextView mConnectionPrinterTextview;
    AutofitTextView mStartPrinterTextview;
    AutofitTextView mPrintDevice;
    RecyclerView mRecyclerview;
    DrawerAdapter mDrawerAdapter;
    DrawerContentModel drawerContentModel;
    List<DrawerContentModel> drawerContentModelList;
    BTPrinting mBTPrinting = new BTPrinting();
    protected Canvas mCanvas = new Canvas();
    List<BluetoothDevice>mBluetoothDevices=new ArrayList<>();
    List<BluetoothDevice>mBluetoothHistoryDevices=new ArrayList<>();
    MBlueTooth mBlueTooth;
    CommonTools mCommonTools;
    String mCurrentPrintingDeviceInfo;
    /**
     * 打印机异步调用标识   0：连接打印机   1：开始打印
     */
    int mRxPrintState=0;

    boolean mSelectHistory=false;

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PrintTestPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /**
     * 打印机方法执行异步调用
     * @param position
     */
    public void RxPrint(final int position){
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
//                Drawable drawable = context.getTheme().getDrawable(drawableRes);//Api21以上可以用
                if(mRxPrintState==0) {
                    if(!mCurrentPrintingDeviceInfo.equals("")) {
                        String deveceId = mCurrentPrintingDeviceInfo.split("P58A\\+:")[1];
                        if (!mBTPrinting.IsOpened()) {
                            mBTPrinting.Open(deveceId, getApplicationContext());
                        } else {
                            mBTPrinting.Close();
                        }
                    }
                    Boolean boolea=true;
                    subscriber.onNext(boolea);
                    subscriber.onCompleted();
                }else if(mRxPrintState==1){
                    if(mBTPrinting.IsOpened()) {
                        CanvsModel orderPartPrintByCityMatchMode=new CanvsModel();
                        orderPartPrintByCityMatchMode.setWeight(10+"");
                        orderPartPrintByCityMatchMode.setDownGoodsStation(11+"");
                        orderPartPrintByCityMatchMode.setGoodsName("test");
                        orderPartPrintByCityMatchMode.setNumIndex(12+"");
                        orderPartPrintByCityMatchMode.setOrderPartId(13+"");
                        orderPartPrintByCityMatchMode.setPackageNo(14+"");
                        orderPartPrintByCityMatchMode.setReceiveAddress(15+"");
                        orderPartPrintByCityMatchMode.setReceiveName(16+"");
                        orderPartPrintByCityMatchMode.setReceivePhone(17+"");
                        orderPartPrintByCityMatchMode.setStationNo(18+"");
                        orderPartPrintByCityMatchMode.setTotalNumber(19+"");
                        orderPartPrintByCityMatchMode.setUpGoodsStation(20+"");
                        try {
                            PrintUitil.PrintStringTest(getApplicationContext(),
                                    mCanvas, orderPartPrintByCityMatchMode);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "请打开打印机!", Toast.LENGTH_SHORT).show();
                        Log.d("gg======Error==","请打开打印机");
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(Boolean drawable) {
                        Toast.makeText(getApplicationContext(), "success=="+drawable, Toast.LENGTH_SHORT).show();
                        Log.d("gg======susuccess==",drawable+"");
                        controlViewShow();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("gg======Error==",e.toString()+"");
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_printer);
        mContext = getApplicationContext();
        initView();
        initData();
    }

    /**
     * 控制view的显示
     */
    public void controlViewShow(){
        if(mBTPrinting.IsOpened()){
            mConnectionPrinterTextview.setText("断开打印机");
        }else {
            mConnectionPrinterTextview.setText("连接打印机");
        }
    }
    /**
     * 初始化布局
     */
    public void initView(){
        mGetSearchBlueToothList = (AutofitTextView) findViewById(R.id.search_bluetooth);
        mGetHistoryBlueToothList = (AutofitTextView) findViewById(R.id.history_bluetooth);
        mRecyclerview = (RecyclerView) findViewById(R.id.bluetooth_recyclerview);
        mConnectionPrinterTextview=(AutofitTextView) findViewById(R.id.connection_printer_textview);
        mStartPrinterTextview=(AutofitTextView) findViewById(R.id.start_printer_textview);
        mPrintDevice=(AutofitTextView) findViewById(R.id.printer_device);
    }

    /**
     * 数据初始化
     */
    public void initData(){
        mBTPrinting.SetCallBack(this);
        mCanvas.Set(mBTPrinting);
        mBlueTooth=new MBlueTooth(mContext,this);
        mBlueTooth.setBluetoothOpenListen(this);
        drawerContentModelList = new ArrayList<>();
        setDrawerContentModel(R.mipmap.ic_launcher, "开始。。");
        mCommonTools=new CommonTools(mContext);
        if(mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER).equals("")){
            mCurrentPrintingDeviceInfo="";
            mPrintDevice.setText("本机没有打印设备信息");
        }else{
            mCurrentPrintingDeviceInfo=mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER);
            mPrintDevice.setText("当前打印设备:"+mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER));
        }

        drawerContentModelList.add(drawerContentModel);
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        if(drawerContentModelList.size()>0) {
            mDrawerAdapter = new DrawerAdapter(getApplicationContext(), drawerContentModelList, false);
            mDrawerAdapter.setItemClickListen(this);
            mRecyclerview.setAdapter(mDrawerAdapter);
        }
        mConnectionPrinterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentPrintingDeviceInfo!=null && !mCurrentPrintingDeviceInfo.equals("")) {
                    mRxPrintState=0;
                    RxPrint(0);
                }else{
                    Toast.makeText(mContext,"请打开蓝牙设备",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mStartPrinterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRxPrintState=1;
                RxPrint(0);
            }
        });
        mGetHistoryBlueToothList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mBlueTooth.searchHistoryDevices(0);
                mBluetoothHistoryDevices=mBlueTooth.getBluetoothHistoryResult();
                mSelectHistory=true;
                if(mBluetoothHistoryDevices!=null){
                    setRecycleViewData(mBluetoothHistoryDevices);
                }else{
                    Toast.makeText(mContext, "数据搜索失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        mGetSearchBlueToothList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectHistory=false;
               mBlueTooth.searchBluetoothDevice();
            }
        });
    }

    /**
     * recycleView数据初始化
     *
     * @param imageId
     * @param content
     */
    public void setDrawerContentModel(int imageId, String content) {
        drawerContentModel = new DrawerContentModel();
        drawerContentModel.setContent(content);
        drawerContentModel.setIcon(imageId);
    }

    public void setRecycleViewData(List<BluetoothDevice>bluetoothDevices){
        drawerContentModelList=new ArrayList<>();
        for (int i=0;i<bluetoothDevices.size();i++){
            setDrawerContentModel(R.mipmap.e2,bluetoothDevices.get(i).getName()+":"+
                    bluetoothDevices.get(i).getAddress());
            drawerContentModelList.add(drawerContentModel);
        }
        if(drawerContentModelList.size()>0){
            mDrawerAdapter.refresh(drawerContentModelList);
        }
    }


    @Override
    public void setItemListen(final int position) {
        if(!mSelectHistory) {
            if (!drawerContentModelList.get(position).getContent().contains("P58A+")) {
                Toast.makeText(mContext, "该设备不属于打印设备，请重新选择。。", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mBlueTooth.isPair(mBluetoothDevices.get(position))) {
                mCurrentPrintingDeviceInfo=drawerContentModelList.get(position).getContent();
                mPrintDevice.setText("当前打印设备:"+mCurrentPrintingDeviceInfo);
                mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
                Toast.makeText(mContext, "该设备蓝牙已经匹配过且已选中，可以直接连接打印。。", Toast.LENGTH_SHORT).show();
            } else {
                mShowDialog(position);
            }
        }else{
            if (!mBluetoothHistoryDevices.get(position).getName().contains("P58A+")) {
                Toast.makeText(mContext, "该设备不属于打印设备，请重新选择。。", Toast.LENGTH_SHORT).show();
                return;
            }else{
                mCurrentPrintingDeviceInfo=drawerContentModelList.get(position).getContent();
                mPrintDevice.setText("当前打印设备:"+mCurrentPrintingDeviceInfo);
                mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
                Toast.makeText(mContext, "该设备蓝牙已经匹配过且已选中，可以直接连接打印。。", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void mShowDialog(final int position){
        new  AlertDialog.Builder(this)
                .setTitle("标题" )
                .setMessage("该设备蓝牙未匹配过，是否进行匹配?" )
                .setPositiveButton("确定" ,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBlueTooth.bluetoothPair(mBluetoothDevices.get(position));
                    }
                })
                .setNegativeButton("关闭",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBlueTooth.onActivityResult(requestCode,resultCode,data);
    }

//    /**
//     * 权限监听回调
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
//        switch (requestCode) {
//            case 10004: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                }
//                return;
//            }
//        }
//    }


    @Override
    public void OnOpen() {
        Log.d("gg======susuccess==","12");
    }

    @Override
    public void OnOpenFailed() {
        Log.d("gg======susuccess==","13");
    }

    @Override
    public void OnClose() {
        Log.d("gg======susuccess==","14");
    }


    @Override
    public void blueToothOpenSuccess() {

    }

    @Override
    public void blueToothOpenFaile() {

    }

    @Override
    public void blueToothSearchFinish() {
        mBluetoothDevices=mBlueTooth.getSearchBluetoothResult();
        if(mBluetoothDevices!=null){
            setRecycleViewData(mBluetoothDevices);
        }else{
            Toast.makeText(mContext, "数据搜索失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void blueToothPairSucces(BluetoothDevice device) {
        mCurrentPrintingDeviceInfo=device.getName()+":"+device.getAddress();
        mPrintDevice.setText("当前打印设备:"+mCurrentPrintingDeviceInfo);
        mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
        Toast.makeText(mContext, "蓝牙配对成功，可以进行打印", Toast.LENGTH_LONG).show();
    }

    @Override
    public void blueToothPairFail() {
        Toast.makeText(mContext, "蓝牙配对失败，请重新选择配对。。", Toast.LENGTH_LONG).show();
    }

    /**
     * 蓝牙回调监听
     * 当有设备被发现的时候会收到 action == BluetoothDevice.ACTION_FOUND 的广播
     */
    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    };
}
