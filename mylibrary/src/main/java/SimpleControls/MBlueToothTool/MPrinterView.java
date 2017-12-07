package SimpleControls.MBlueToothTool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.Canvas;
import com.lvrenyang.io.IOCallBack;

import java.util.ArrayList;
import java.util.List;
import MyTools.BaseAction;
import MyTools.CommonTools;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yg.customcontrol.com.mylibrary.R;

/**
 * @author yg
 * @date 2017/12/6
 */

public class MPrinterView implements IOCallBack, MBlueTooth.BlueToothOpenListen, MBTRecycleAdapter.ItemClickListen {
    private RecyclerView recyclerView;
    private TextView deviceId;
    private TextView deviceState;
    private ImageView blueToothLoding;
    private ImageView arrowImg;
    private LinearLayout arrowContent;
    private LinearLayout mBluetoothContent;
    private TextView mMaskContent;
    private View mContentView;
    private MBTRecycleAdapter mbtRecycleAdapter;
    private LinearLayout.LayoutParams LayoutParams;
    private BTPrinting mBTPrinting = new BTPrinting();
    private Canvas mCanvas = new Canvas();
    private MBlueTooth mBlueTooth;
    private Context mContext;
    private Activity activity;
    private List<BluetoothModel> mBluetoothModels=new ArrayList<>();
    private BluetoothModel mBluetoothModel=new BluetoothModel();
    private BluetoothModel.BlueTtoothList blueTtoothList=new BluetoothModel.BlueTtoothList();
    private CommonTools mCommonTools;
    private String mCurrentPrintingDeviceInfo;
    private List<BluetoothDevice>mBluetoothDevices=new ArrayList<>();
    private List<BluetoothDevice>mBluetoothHistoryDevices=new ArrayList<>();
    private AnimationDrawable animationDrawable;
    private boolean isFold=false;
    private int contentHeight=0;
    private ValueAnimator animatorFoldLayout;
    private ValueAnimator animatorOpenLayout;
    private CanvsModel orderPartPrintByCityMatchMode=new CanvsModel();
    private List<CanvsModel> canvsModels =new ArrayList<>();
    /**
     * 打印机异步调用标识   0：连接打印机   1：开始打印
     */
    int mRxPrintState=0;
    public MPrinterView(Context context, Activity activity){
        this.activity=activity;
        mContext=context;
        mCommonTools=new CommonTools(context);
        initView();
        initData();
    }
    /**
     * view 初始化
     */
    public void initView(){
        mContentView= LayoutInflater.from(mContext).inflate(R.layout.view_bluetooth,null);
        recyclerView = (RecyclerView)mContentView.findViewById(R.id.bluetooth_recycle_view);
        deviceId=(TextView)mContentView.findViewById(R.id.device_id) ;
        deviceState=(TextView)mContentView.findViewById(R.id.device_state) ;
        blueToothLoding=(ImageView)mContentView.findViewById(R.id.blue_tooth_loding) ;
        arrowImg=(ImageView)mContentView.findViewById(R.id.arrow_img);
        arrowContent=(LinearLayout)mContentView.findViewById(R.id.arrow_content);
        mBluetoothContent=(LinearLayout)mContentView.findViewById(R.id.bluetooth_content);
        mMaskContent=(TextView) mContentView.findViewById(R.id.mask_content);
        animationDrawable = (AnimationDrawable) blueToothLoding.getDrawable();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
    /**
     * 数据初始化
     */
    private void initData(){
        mBluetoothModels=new ArrayList<>();
        mBTPrinting.SetCallBack(this);
        mCanvas.Set(mBTPrinting);
        mBlueTooth=new MBlueTooth(mContext,activity);
        mBlueTooth.setBluetoothOpenListen(this);
        deviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectionPrinter();
            }
        });
        arrowContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!animationDrawable.isRunning()) {
                    if (isFold) {
                        openDetailLayout();
                    } else {
                        foldDetailLayout();
                    }
                }
            }
        });
        blueToothLoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!animationDrawable.isRunning()) {
                    if (isFold) {
                        openDetailLayout();
                    }
                    recyclerView.postDelayed(new
                                                     Runnable() {
                                                         @Override
                                                         public void run() {
                                                             setData();
                                                         }
                                                     },1000);
                }
            }
        });
    }

    public void setRecyclerView(){
        if(mbtRecycleAdapter==null && mBluetoothModels.size()>0) {
            mbtRecycleAdapter = new MBTRecycleAdapter(mContext, mBluetoothModels);
            mbtRecycleAdapter.setItemClickListen(this);
            recyclerView.setAdapter(mbtRecycleAdapter);
        }else if(mBluetoothModels.size()>0){
            mbtRecycleAdapter.refresh(mBluetoothModels);
        }else{
            Toast.makeText(mContext, "数据加载失败", Toast.LENGTH_LONG).show();
        }
    }

    public MPrinterView setData(){
        mBluetoothModels=new ArrayList<>();
        bluetoothLodingStart();
        if(mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER).equals("")){
            mCurrentPrintingDeviceInfo="";
            deviceId.setText("本机没有打印设备信息");
        }else{
            mCurrentPrintingDeviceInfo=mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER);
            deviceId.setText(mCommonTools.getSharedPreference(BaseAction.SHARESTATEPRINTER));
        }
        getHistoryBluetooth();
        return this;
    }
    /**
     * 获取布局View
     * @return
     */
    public View getContentView(){
        return mContentView;
    }
    /**
     * 打印机方法执行异步调用
     * @param position
     */
    public void RxPrint(final int position){
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if(mRxPrintState==0) {
                    if(!mCurrentPrintingDeviceInfo.equals("")) {
                        String deveceId = mCurrentPrintingDeviceInfo.split("P58A\\+:")[1];
                        if (!mBTPrinting.IsOpened()) {
                            mBTPrinting.Open(deveceId, mContext);
                        } else {
                            mBTPrinting.Close();
                        }
                    }
                    Boolean boolea=true;
                    subscriber.onNext(boolea);
                    subscriber.onCompleted();
                }else if(mRxPrintState==1){
                    if(mBTPrinting.IsOpened()) {
                                mCanvas.GetIO().IsOpened();
                    }else{
                        animationDrawable.stop();
                        Toast.makeText(mContext, "请打开打印机!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "success=="+drawable, Toast.LENGTH_SHORT).show();
                        Log.d("gg======susuccess==",drawable+"");
                        controlViewShow();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d("gg======onCompleted==","");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("gg======Error==",e.toString()+"");
                    }
                });
    }
    /**
     * 控制view的显示
     */
    public void controlViewShow(){
        if(!mBTPrinting.IsOpened()){
            deviceState.setTextColor(ContextCompat.getColor(mContext,R.color.text_red));
            deviceId.setTextColor(ContextCompat.getColor(mContext,R.color.text_red));
            deviceState.setText("已断开");
        }else {
            deviceState.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
            deviceId.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
            deviceState.setText("已连接");
        }
    }
    /**
     * 开始动画
     */
    public void bluetoothLodingStart(){
//        mMaskContent.setVisibility(View.VISIBLE);
        animationDrawable = (AnimationDrawable) blueToothLoding.getDrawable();
//        blueToothLoding.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.bluetooth_loading));
        if(!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }
    /**
     * 停止动画
     */
    public void bluetoothLodingStop(){
//        mMaskContent.setVisibility(View.GONE);
        if(animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
    /**
     * 连接打印机
     */
    public void connectionPrinter(){
        mRxPrintState=0;
        RxPrint(0);
    }

    /**
     * 开始打印
     */
    public void startPrinter(Canvas canvas){
        mCanvas=canvas;
        if(mCurrentPrintingDeviceInfo!=null && !mCurrentPrintingDeviceInfo.equals("")) {
            mRxPrintState = 1;
            RxPrint(0);
        }else{
            Toast.makeText(mContext,"请打开蓝牙设备",Toast.LENGTH_SHORT).show();
        }
    }
    public Canvas getCanvas(){
        return mCanvas;
    }
    /**
     * 获取已经匹配过的设备
     */
    public void getHistoryBluetooth(){
        mBlueTooth.searchHistoryDevices(0);
        mBluetoothHistoryDevices=mBlueTooth.getBluetoothHistoryResult();
        if(mBluetoothHistoryDevices!=null && mBluetoothHistoryDevices.size()>0){
            mBluetoothModel=new BluetoothModel();
            mBluetoothModel.setLayoutState(0);
            mBluetoothModel.setTitle(activity.getResources().getString(R.string.pair_printer_String));
            mBluetoothModels.add(mBluetoothModel);
            for (int i=0;i<mBluetoothHistoryDevices.size();i++){
                mBluetoothModel=new BluetoothModel();
                blueTtoothList =new BluetoothModel.BlueTtoothList();
                blueTtoothList.setState(2);
                blueTtoothList.setBluetoothDevice(mBluetoothHistoryDevices.get(i));
                mBluetoothModel.setLayoutState(1);
                mBluetoothModel.setBlueTtoothList(blueTtoothList);
                mBluetoothModels.add(mBluetoothModel);
            }
            setRecyclerView();
            getSearchBluetooth();
        }else{
            Toast.makeText(mContext, "历史配对数据搜索失败", Toast.LENGTH_LONG).show();
            getSearchBluetooth();
        }
    }

    /**
     * 搜索附近蓝牙
     */
    public void getSearchBluetooth(){
        mBlueTooth.searchBluetoothDevice();
    }

    /**
     * 判断搜索得到的蓝牙是否已经匹配过
     */
    public void judgeHistoryOnLine(){
        List<String>searchDevices=new ArrayList<>();
        for (int i=0;i<mBluetoothModels.size();i++){
            if(mBluetoothModels.get(i).getLayoutState()==2){
                searchDevices.add(mBluetoothModels.get(i).getBlueTtoothList().getBluetoothDevice().getAddress());
            }
        }
        for (int i=0;i<mBluetoothModels.size();i++){
            if(mBluetoothModels.get(i).getLayoutState()==1){
                if(searchDevices.contains(mBluetoothModels.get(i).getBlueTtoothList().getBluetoothDevice().getAddress
                        ())){
                    mBluetoothModels.get(i).getBlueTtoothList().setState(1);
                }
            }
        }
    }

    /**
     * 判断是否重复加载附近蓝牙数据
     */
    public boolean judgeRepeatSearch(){
        List<String>searchDevices=new ArrayList<>();
        for (int i=0;i<mBluetoothModels.size();i++){
            if(mBluetoothModels.get(i).getLayoutState()==0 && mBluetoothModels.get(i)
                    .getTitle().equals(mContext.getResources().getString(R.string.bluetooth_String))){
                return true;
            }
        }
        return false;
    }

    /**
     * 界面销毁调用
     */
    public void onDestroy(){
        bluetoothLodingStop();
        mBTPrinting.Close();
    }

    public void mShowDialog(final int position){
        new  AlertDialog.Builder(activity)
                .setTitle("标题" )
                .setMessage("该设备蓝牙未匹配过，是否进行匹配?" )
                .setPositiveButton("确定" ,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBlueTooth.bluetoothPair(mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice());
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
    /**
     * 旋转箭头角标方向
     * @param isFold
     */
    private void rotateArrow(boolean isFold){
        ObjectAnimator rotateAnimator;
        if (isFold){
            rotateAnimator = ObjectAnimator.ofFloat(arrowImg, "rotation", 180, 0);
        }else {
            rotateAnimator = ObjectAnimator.ofFloat(arrowImg, "rotation", 0, 180);
        }
        rotateAnimator.start();
    }

    /**
     * 将详细信息页面折叠收起
     */
    private void foldDetailLayout(){
        if (animatorFoldLayout == null){
            LayoutParams= (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
            animatorFoldLayout = ValueAnimator.ofInt(contentHeight, 0);
            animatorFoldLayout.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int height = (int) valueAnimator.getAnimatedValue();
                    LayoutParams.height = height;
                    mBluetoothContent.setLayoutParams(LayoutParams);
                }
            });
            animatorFoldLayout.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isFold = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rotateArrow(isFold);
                }
            });
            animatorFoldLayout.setDuration(1000);
        }
        animatorFoldLayout.start();
    }
    /**
     * 将详细信息展开显示
     */
    private void openDetailLayout(){
        if (animatorOpenLayout == null){
            LayoutParams= (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
            animatorOpenLayout = ValueAnimator.ofInt(0, contentHeight);
            animatorOpenLayout.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    LayoutParams.height = height;
                    mBluetoothContent.setLayoutParams(LayoutParams);
                }
            });
            animatorOpenLayout.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rotateArrow(isFold);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isFold = false;
                }
            });
            animatorOpenLayout.setDuration(1000);
        }
        animatorOpenLayout.start();
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
        if(mBluetoothDevices!=null && mBluetoothDevices.size()>0){
            if(!judgeRepeatSearch()) {
                mBluetoothModel = new BluetoothModel();
                mBluetoothModel.setLayoutState(0);
                mBluetoothModel.setTitle(activity.getResources().getString(R.string.bluetooth_String));
                mBluetoothModels.add(mBluetoothModel);
                for (int i = 0; i < mBluetoothDevices.size(); i++) {
                    mBluetoothModel = new BluetoothModel();
                    blueTtoothList = new BluetoothModel.BlueTtoothList();
                    blueTtoothList.setState(1);
                    blueTtoothList.setBluetoothDevice(mBluetoothDevices.get(i));
                    mBluetoothModel.setLayoutState(2);
                    mBluetoothModel.setBlueTtoothList(blueTtoothList);
                    mBluetoothModels.add(mBluetoothModel);
                }
                judgeHistoryOnLine();
                setRecyclerView();
                bluetoothLodingStop();
            }
        }else{
            Toast.makeText(mContext, "数据搜索失败", Toast.LENGTH_LONG).show();
            bluetoothLodingStop();
        }
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                contentHeight=recyclerView.getHeight();
            }
        },1000);
    }
    @Override
    public void blueToothPairSucces(BluetoothDevice bluetoothDevice) {
        mCurrentPrintingDeviceInfo=bluetoothDevice.getName()+":"+bluetoothDevice.getAddress();
        deviceId.setText("当前打印设备:"+mCurrentPrintingDeviceInfo);
        mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
        Toast.makeText(mContext, "蓝牙配对成功，可以进行打印", Toast.LENGTH_LONG).show();
    }

    @Override
    public void blueToothPairFail() {
        Toast.makeText(mContext, "蓝牙配对失败，请重新选择配对。。", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItemListen(int position, int state) {
        if(state==2) {
            if (mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getName()
                    ==null||!mBluetoothModels
                    .get(position).getBlueTtoothList().getBluetoothDevice().getName().contains("P58A+")) {
                Toast.makeText(mContext, "该设备不属于打印设备，请重新选择。。", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mBlueTooth.isPair(mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice())) {
                mCurrentPrintingDeviceInfo=mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getName()
                        +":"+mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getAddress();
                deviceId.setText(mCurrentPrintingDeviceInfo);
                mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
                Toast.makeText(mContext, "该设备蓝牙已经匹配过且已选中，可以直接连接打印。。", Toast.LENGTH_SHORT).show();
            } else {
                mShowDialog(position);
            }
        }else{
            if (mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getName()
                    ==null||!mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getName()
                    .contains("P58A+")) {
                Toast.makeText(mContext, "该设备不属于打印设备，请重新选择。。", Toast.LENGTH_SHORT).show();
                return;
            }else{
                mCurrentPrintingDeviceInfo=mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getName()
                        +":"+mBluetoothModels.get(position).getBlueTtoothList().getBluetoothDevice().getAddress();
                deviceId.setText(mCurrentPrintingDeviceInfo);
                deviceId.setText(mCurrentPrintingDeviceInfo);
                mCommonTools.SetSharedPreference(BaseAction.SHARESTATEPRINTER,mCurrentPrintingDeviceInfo);
                if(mBluetoothModels.get(position).getBlueTtoothList().getState()==2){
                    Toast.makeText(mContext, "该设备未在线，请开启该设备或者重新选择。。", Toast.LENGTH_SHORT).show();
                } else if (mBluetoothModels.get(position).getBlueTtoothList().getState()==4) {
                    Toast.makeText(mContext, "该设备已选中但未连接，请连接该设备或者重新选择。。", Toast.LENGTH_SHORT).show();
                }else if(mBluetoothModels.get(position).getBlueTtoothList().getState()==1){
                    Toast.makeText(mContext, "该设备已选中且已连接，可以直接连接打印。。", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void OnOpen() {

    }

    @Override
    public void OnOpenFailed() {

    }

    @Override
    public void OnClose() {

    }
}
