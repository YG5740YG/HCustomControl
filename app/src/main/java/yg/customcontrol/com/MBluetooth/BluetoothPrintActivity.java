//package yg.customcontrol.com.MBluetooth;
//
//import android.Manifest;
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothProfile;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import MyTools.BaseAction;
//import SimpleControls.MScrollControl.DrawerAdapter;
//import SimpleControls.MScrollControl.DrawerContentModel;
//import me.grantland.widget.AutofitTextView;
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//import yg.customcontrol.com.R;
//
///**
// *
// * @author zhanggh
// * @date 2017/12/1
// */
//
//public class BluetoothPrintActivity extends Activity implements DrawerAdapter.ItemClickListen,IOCallBack {
//    Context mContext;
//    BluetoothAdapter mBluetoothAdapter;
//    AutofitTextView mBlueToothText;
//    TextView mBlueToothConnect;
//    TextView mBlueToothAccept;
//    AutofitTextView mGetBlueToothListTextView;
//    AutofitTextView mSearchBlueToothListTextView;
//    RecyclerView mRecyclerview;
//    DrawerAdapter mDrawerAdapter;
//    DrawerContentModel drawerContentModel;
//    List<DrawerContentModel> drawerContentModelList;
//    Set<BluetoothDevice> pairedDevices;
//    List<BluetoothDevice> mBluetoothDevices;
//    BluetoothDevice hasPairDevice;
//    boolean mIsHavePairDeice = false;
//    boolean mIsOpenBluetooth=false;
//    boolean mIsClient=false;
//    IntentFilter filter;
//
//    BTPrinting mBTPrinting = new BTPrinting();
//
//    public void RxUse(){
//        Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
////                Drawable drawable = context.getTheme().getDrawable(drawableRes);//Api21以上可以用
//                Boolean boolea=true;
//                subscriber.onNext(boolea);
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Observer<Boolean>() {
//                    @Override
//                    public void onNext(Boolean drawable) {
//                        Toast.makeText(getApplicationContext(), "success=="+drawable, Toast.LENGTH_SHORT).show();
//                        Log.d("gg======susuccess==",drawable+"");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d("gg======Error==",e.toString()+"");
//                    }
//                });
//    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_bluetooth);
//        mContext = getApplicationContext();
//        RxUse();
//        mBlueToothText = (AutofitTextView) findViewById(R.id.blue_tooth);
//        mGetBlueToothListTextView = (AutofitTextView) findViewById(R.id.get_blue_tooth_list);
//        mSearchBlueToothListTextView = (AutofitTextView) findViewById(R.id.search_blue_tooth);
//        mRecyclerview = (RecyclerView) findViewById(R.id.bluetooth_recyclerview);
//        mBlueToothConnect = (TextView) findViewById(R.id.blue_tooth_connect);
//        mBlueToothAccept = (TextView) findViewById(R.id.blue_tooth_accept);
//        initRecycleView();
//        mBlueToothText.setText("打开蓝牙。。。");
//
////        手机位置权限判断，使用蓝牙需要打开此权限
//        if (Build.VERSION.SDK_INT >= 6.0) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
//                            .ACCESS_FINE_LOCATION},
//                    10004);
//        }
//        //判断系统版本
//        if (Build.VERSION.SDK_INT >= 23) {
//            //检测当前app是否拥有某个权限
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION);
//            //判断这个权限是否已经授权过
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                //判断是否需要 向用户解释，为什么要申请该权限
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    Toast.makeText(this, "Need bluetooth permission.",
//                            Toast.LENGTH_SHORT).show();
//                    ActivityCompat.requestPermissions(this, new String[]
//                            {Manifest.permission.ACCESS_COARSE_LOCATION}, 10007);
//                    return;
//                }
//            }
//            drawerContentModelList.clear();
//            initBluetoothPrint();
//            mGetBlueToothListTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(mIsOpenBluetooth){
//                        hasHistoryDevices();
//                    }else{
//                        Toast.makeText(mContext,"请打开蓝牙设备",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            mSearchBlueToothListTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(mIsOpenBluetooth){
//                        searchDevice();
//                    }else{
//                        Toast.makeText(mContext,"请打开蓝牙设备",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            mBlueToothConnect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sendOutData(hasPairDevice);
//                }
//            });
//            mBlueToothAccept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    receiveData();
//                }
//            });
//
//        }
//    }
//
//    /**
//     * 初始化recycleView
//     */
//    public void initRecycleView() {
//        mBluetoothDevices = new ArrayList<>();
//        drawerContentModelList = new ArrayList<>();
//        setRecyclerDate(R.mipmap.ic_launcher, "开始。。");
//        drawerContentModelList.add(drawerContentModel);
//        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        mDrawerAdapter = new DrawerAdapter(getApplicationContext(), drawerContentModelList, false);
//        mDrawerAdapter.setItemClickListen(this);
//        mRecyclerview.setAdapter(mDrawerAdapter);
//    }
//
//    private void initDrawerData(DrawerContentModel drawerContentModel) {
//        drawerContentModelList.add(drawerContentModel);
//        mDrawerAdapter.notifyDataSetChanged();
//    }
//
//    /**
//     * // 注册广播监听
//     */
//    public void setIntentFilter() {
//        filter = new IntentFilter();
//        filter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
//        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
//        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
//        filter.addAction(BluetoothDevice.ACTION_UUID);//UUID获取
//        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);//动作状态发生了变化
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
//        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);//当全部搜索完后发送该广播
//        filter.addAction(String.valueOf(BluetoothAdapter.STATE_OFF));//当全部搜索完后发送该广播
//        filter.addAction(String.valueOf(BluetoothAdapter.STATE_ON));//当全部搜索完后发送该广播
//    }
//
//    /**
//     * 蓝牙初始化操作
//     */
//    public void initBluetoothPrint() {
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter != null) {
//            //检查是否打开蓝牙
//            if (!mBluetoothAdapter.isEnabled()) {
//                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(intent, BaseAction.OPENBLUETOOTH);
//            } else {
//                mIsOpenBluetooth=true;
//            }
//        } else {
//            mIsOpenBluetooth=false;
//            Log.i("blueTooth", "该手机不支持蓝牙");
//        }
//    }
//
//    /**
//     * 让蓝牙设备可以被发现
//     */
//    public void allowExternalDevicesAccessed() {
//        //startDiscovery() 只能扫描到那些状态被设为 可发现 的设备。安卓设备默认是不可发现的，要改变设备为可发现的状态
//        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);//设置可被发现的时间，300s
//        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//        startActivity(intent);
//    }
//
//    /**
//     * recycleView数据初始化
//     *
//     * @param imageId
//     * @param content
//     */
//    public void setRecyclerDate(int imageId, String content) {
//        drawerContentModel = new DrawerContentModel();
//        drawerContentModel.setContent(content);
//        drawerContentModel.setIcon(imageId);
//    }
//
//    /**
//     * 如果有已经配对过的蓝牙设备信息，则直接配对
//     * 系统会保存所有的曾经成功配对过的设备信息。所以在执行startDiscovery()之前，可以先尝试查找已配对设备，
//     * 因为这是一个本地信息读取的过程，所以比startDiscovery()要快得多，也避免占用过多资源。如果设备在蓝牙信号的覆盖范围内，
//     * 就可以直接发起连接了。
//     */
//    public void hasHistoryDevices() {
//        mBluetoothDevices.clear();
//        mDrawerAdapter.notifyDataSetChanged();
//        allowExternalDevicesAccessed();
//        pairedDevices = mBluetoothAdapter.getBondedDevices();
//        if (pairedDevices.size() > 0) {
//            mIsHavePairDeice = true;
//            mBluetoothDevices.clear();
//            for (BluetoothDevice device : pairedDevices) {
//                mBluetoothDevices.add(device);
//                setRecyclerDate(R.mipmap.e2, device.getName() + ":" + device.getAddress());
//                initDrawerData(drawerContentModel);
//            }
//            setIntentFilter();
//            registerReceiver(mReceiver, filter);//监听蓝牙搜索
//        } else {
//
//        }
//    }
//
//    /**
//     * 搜索蓝牙设备，并监听
//     */
//    public void searchDevice() {
//        allowExternalDevicesAccessed();
//        mBluetoothDevices.clear();
//        mDrawerAdapter.notifyDataSetChanged();
//        mBluetoothAdapter.startDiscovery();
//
//        setIntentFilter();
//        registerReceiver(mReceiver, filter);//监听蓝牙搜索
//        //广播监听测试
//        Intent intent = new Intent();
//        intent.setAction("com.receiverTest");
//        sendBroadcast(intent);
//    }
//
//    @Override
//    public void setItemListen(int position) {
//        mIsClient=true;
//        if (mBluetoothDevices.size() > 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                mBluetoothDevices.get(position).connectGatt(getApplicationContext(), true, mGattCallback);
//            }
//            hasPairDevice=mBluetoothDevices.get(position);
//            ConnectThread connectThread = new ConnectThread(mBluetoothAdapter, mBluetoothDevices.get(position));
//            Thread thread = new Thread(connectThread);
//            thread.start();
//        }
//    }
//
//    /**
//     * 关闭蓝牙搜索
//     * 搜索是一个十分耗费资源的操作，所以需要及时的调用cancelDiscovery()来释放资源。比如在进行设备连接之前，
//     * 一定要先调用cancelDiscovery()
//     */
//    public void cancelSearchDevice() {
//        mBluetoothAdapter.cancelDiscovery();
//    }
//
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            //广播的 intent 里包含了一个 BluetoothDevice 对象
//            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//            // 当有设备被发现的时候会收到 action == BluetoothDevice.ACTION_FOUND 的广播
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                setRecyclerDate(R.mipmap.e1, device.getName() + ":" + device.getAddress());
//                initDrawerData(drawerContentModel);
//                mBluetoothDevices.add(device);
//                device.fetchUuidsWithSdp();
//            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
//                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
//                Log.d("gg======uuid==", uuidExtra + "");
//                if (uuidExtra != null) {
//                    for (int i = 0; i < uuidExtra.length; i++) {
//                        String uuid = uuidExtra[i].toString();
//                        Log.d("gg======uuid==12=", uuid + "");
//                    }
//                }
//            }
//            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                //已搜素完成
//            }
//            else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
//                Toast.makeText(mContext, device.getName() + "已连接", Toast.LENGTH_LONG).show();
//                if(!mIsClient){
//                    hasPairDevice=device;
//                    receiveData();
//                }
//            }
//            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
//                Toast.makeText(mContext, device.getName() + "正在断开蓝牙连接。。。", Toast.LENGTH_LONG).show();
//            }
//            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
//                Toast.makeText(mContext, device.getName() + "蓝牙连接已断开！！！", Toast.LENGTH_LONG).show();
//            }
//
//            else if(action.equals(BluetoothAdapter.STATE_OFF))
//            {
//                Toast.makeText(mContext, "蓝牙已关闭", Toast.LENGTH_LONG).show();
//            }
//            else if(action.equals(BluetoothAdapter.STATE_ON))
//            {
//                Toast.makeText(mContext, "蓝牙打开", Toast.LENGTH_LONG).show();
//            }
////            //配对时，状态改变时
//            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
//                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                switch (device.getBondState()) {
//                    case BluetoothDevice.BOND_BONDING://正在配对
//                        Log.d("BlueToothTestActivity==", "正在配对......");
//                        mBlueToothText.setText("正在配对......");
//                        break;
//                    case BluetoothDevice.BOND_BONDED://配对结束
//                        Log.d("BlueToothTestActivity==", "完成配对");
//                        mBlueToothText.setText("完成配对......");
//                        break;
//                    case BluetoothDevice.BOND_NONE://取消配对/未配对
//                        Log.d("BlueToothTestActivity==", "取消配对");
//                        mBlueToothText.setText("取消配对......");
//                    default:
//                        break;
//                }
//            }
//        }
//    };
//
//    /**
//     * 发送信息
//     *
//     * @param device
//     */
//    public void sendOutData(BluetoothDevice device) {
//        if (device != null) {
//            ConnectedSendThread connectedThread = new ConnectedSendThread(device);
//            Thread thread = new Thread(connectedThread);
//            thread.start();
//        } else {
//            mBlueToothText.setText("设备连接失败。。。");
//        }
//
//    }
//
//    /**
//     * 接收信息
//     */
//    public void receiveData() {
//        if(mIsOpenBluetooth &&hasPairDevice!=null) {
//            AcceptThread acceptThread = new AcceptThread(mBluetoothAdapter, hasPairDevice, getApplicationContext());
//            Thread thread = new Thread(acceptThread);
//            thread.start();
//        }else {
//            Toast.makeText(mContext,"请打开蓝牙和选择传输设备",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("gg======", requestCode + "==" + resultCode);
//        if (resultCode == RESULT_OK) {
//            if (BaseAction.OPENBLUETOOTH == requestCode) {
//                mIsOpenBluetooth=true;
//                Log.d("gg======", requestCode + "");
//                mBlueToothText.setText("蓝牙已经打开。。。");
//                hasHistoryDevices();
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
//
//    private final BluetoothGattCallback mGattCallback =new BluetoothGattCallback() {
//        @Override
//        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
//            Log.d("gg===callBack==","1");
//        }
//
//        @Override
//        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//            super.onPhyRead(gatt, txPhy, rxPhy, status);
//            Log.d("gg===callBack==","2");
//        }
//        @Override
//        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
//            super.onConnectionStateChange(gatt, status, newState);
//            Log.d("gg===callBack==","3");
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                    gatt.discoverServices(); //执行到这里其实蓝牙已经连接成功了
//                }
//                Log.d("gg===callBack==", "Connected to GATT server.");
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                if(hasPairDevice != null){
//                    Log.d("gg===callBack==", "重新连接");
//                }else{
//                    Log.d("gg===callBack==", "Disconnected from GATT server.");
//                }
//            }
//        }
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            super.onServicesDiscovered(gatt, status);
//            Log.d("gg===callBack==","4");
//        }
//
//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicRead(gatt, characteristic, status);
//            Log.d("gg===callBack==","5");
//        }
//
//        @Override
//        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicWrite(gatt, characteristic, status);
//            Log.d("gg===callBack==","6");
//        }
//
//        @Override
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            super.onCharacteristicChanged(gatt, characteristic);
//            Log.d("gg===callBack==","7");
//        }
//
//        @Override
//        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorRead(gatt, descriptor, status);
//            Log.d("gg===callBack==","8");
//        }
//
//        @Override
//        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorWrite(gatt, descriptor, status);
//            Log.d("gg===callBack==","9");
//        }
//
//        @Override
//        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
//            super.onReliableWriteCompleted(gatt, status);
//            Log.d("gg===callBack==","10");
//        }
//
//        @Override
//        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//            super.onReadRemoteRssi(gatt, rssi, status);
//            Log.d("gg===callBack==","11");
//        }
//
//        @Override
//        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
//            super.onMtuChanged(gatt, mtu, status);
//            Log.d("gg===callBack==","12");
//        }
//    };
//
//    //    可以通过方法startDiscovery(),来扫描设备周边可以使用的其他的蓝牙设备，这个方法会触发下面的广播
//    String ACTION_DISCOVERY_STARTED = "android.bluetooth.adapter.action.DISCOVERY_STARTED";  //开始扫描
//    String ACTION_DISCOVERY_FINISHED = "android.bluetooth.adapter.action.DISCOVERY_FINISHED"; //扫描结束
//    //    扫描到可用的设备，还会触发广播,属于类 `BluetoothDevice`
//    String ACTION_FOUND = "android.bluetooth.device.action.FOUND";
//    //    在扫描到设备之后，可用调用方法fetchUuidsWithSdp(),调用这个方法会触发这个广播
//    String ACTION_UUID = "android.bluetooth.device.action.UUID";
//
//
//    @Override
//    public void OnOpen() {
//
//    }
//
//    @Override
//    public void OnOpenFailed() {
//
//    }
//
//    @Override
//    public void OnClose() {
//
//    }
//}