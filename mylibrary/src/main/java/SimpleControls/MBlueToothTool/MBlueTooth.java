package SimpleControls.MBlueToothTool;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import MyTools.BaseAction;
import static android.app.Activity.RESULT_OK;

/**
 *
 * @author yg
 * @date 2017/12/4
 */

public class MBlueTooth {
    Context mContext;
    Activity mActivity;
    /**
     * 蓝牙广播注册监听
     */
    IntentFilter filter;
    /**
     * 蓝牙适配器
     */
    BluetoothAdapter mBluetoothAdapter;
    /**
     * 蓝牙是否打开标识  false:未打开   true：已经打开
     */
    boolean mIsOpenBluetooth=false;
    /**
     * 蓝牙设备搜索信息暂时存储器
     */
    List<BluetoothDevice> mBluetoothSearchDevices;
    /**
     * 蓝牙设备历史信息暂时存储器
     */
    List<BluetoothDevice> mBluetoothHistoryDevices;
    /**
     * 历史蓝牙信息暂时存储器
     */
    Set<BluetoothDevice> pairedDevices;

    /**
     * 蓝牙回调监听
     */
    BlueToothOpenListen mBlueToothOpenListen;

    /**
     * 蓝牙回调监听接口
     */
    public interface BlueToothOpenListen{
        void blueToothOpenSuccess();
        void blueToothOpenFaile();
        void blueToothSearchFinish();
        void blueToothPairSucces(BluetoothDevice bluetoothDevice);
        void blueToothPairFail();
    }

    public void setBluetoothOpenListen(BlueToothOpenListen bluetoothOpenListen){
        mBlueToothOpenListen=bluetoothOpenListen;
    }

    public MBlueTooth(Context context, Activity activity){
        mContext=context;
        mActivity=activity;
        filter = new IntentFilter();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothSearchDevices=new ArrayList<>();
        mBluetoothHistoryDevices=new ArrayList<>();
        initBluetooth();
        setBluetoothFilterReceiver();
    }

    /**
     * 蓝牙初始化操作，查看蓝牙是否已经打开，若没有打开，则打开蓝牙，监听回调
     */
    public void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            //检查是否打开蓝牙
            if (!mBluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivityForResult(intent, BaseAction.OPENBLUETOOTH);
            } else {
                mIsOpenBluetooth=true;
            }
        } else {
            mIsOpenBluetooth=false;
            Toast.makeText(mContext, "该手机不支持蓝牙。。", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 注册广播监听
     */
    public void setIntentFilter() {
        filter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
        filter.addAction(BluetoothDevice.ACTION_UUID);//UUID获取
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);//动作状态发生了变化
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);//当全部搜索完后发送该广播
        filter.addAction(String.valueOf(BluetoothAdapter.STATE_OFF));//当全部搜索完后发送该广播
        filter.addAction(String.valueOf(BluetoothAdapter.STATE_ON));//当全部搜索完后发送该广播
    }

    /**
     * 设置蓝牙广播监听接收
     */
    public void setBluetoothFilterReceiver(){
        setIntentFilter();
        mContext.registerReceiver(mReceiver, filter);//监听蓝牙搜索
    }

    /**
     * 搜索附近蓝牙设备，并监听，返回数据
     */
    public void searchBluetoothDevice() {
        allowExternalDevicesAccessed();
        mBluetoothSearchDevices=new ArrayList<>();
        mBluetoothAdapter.startDiscovery();
    }

    /**
     * 返回附近蓝牙搜索结果
     * @return
     */
    public List<BluetoothDevice> getSearchBluetoothResult(){
        if(mBluetoothSearchDevices.size()>0) {
            return bluetoothListRemoveRepetition(mBluetoothSearchDevices);
        }else{
            return null;
        }
    }

    /**
     * 去重处理
     */
    public List<BluetoothDevice> bluetoothListRemoveRepetition(List<BluetoothDevice> bluetoothDevices){
        List<String>list=new ArrayList<>();
        for(int i=0;i<bluetoothDevices.size();i++){
            if(list.contains(bluetoothDevices.get(i).getAddress())){
                bluetoothDevices.remove(i);
            }else {
                list.add(bluetoothDevices.get(i).getAddress());
            }
        }
        return bluetoothDevices;
    }

    /**
     * 返回附近蓝牙历史结果
     * @return
     */
    public List<BluetoothDevice> getBluetoothHistoryResult(){
        if(mBluetoothHistoryDevices.size()>0) {
            return bluetoothListRemoveRepetition(mBluetoothHistoryDevices);
        }else{
            return null;
        }
    }

    /**
     * 查看是否有历史配对蓝牙信息，如果有已经配对过的蓝牙设备信息，则使用已配对的信息
     */
    public void searchHistoryDevices(int sate) {
        if(sate==0) {
            allowExternalDevicesAccessed();
        }
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            mBluetoothHistoryDevices=new ArrayList<>();
            for (BluetoothDevice device : pairedDevices) {
                mBluetoothHistoryDevices.add(device);
            }
        } else {
            Toast.makeText(mContext, "暂无历史配对信息，请重新搜索配对。。", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 让蓝牙设备可以被发现
     * startDiscovery() 只能扫描到那些状态被设为 可发现 的设备。安卓设备默认是不可发现的，要改变设备为可发现的状态
     */
    public void allowExternalDevicesAccessed() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);//设置可被发现的时间，300s
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 关闭蓝牙搜索
     * 搜索是一个十分耗费资源的操作，所以需要及时的调用cancelDiscovery()来释放资源。比如在进行设备连接之前，
     * 一定要先调用cancelDiscovery()
     */
    public void cancelSearchDevice() {
        mBluetoothAdapter.cancelDiscovery();
    }

    /**
     * 蓝牙回调监听
     * 当有设备被发现的时候会收到 action == BluetoothDevice.ACTION_FOUND 的广播
     */
    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if(mBluetoothSearchDevices.size()<5) {
                    mBluetoothSearchDevices.add(device);
                    device.fetchUuidsWithSdp();
                }else{
                    mBlueToothOpenListen.blueToothSearchFinish();
                    cancelSearchDevice();
                }
            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                if (uuidExtra != null) {
                    for (int i = 0; i < uuidExtra.length; i++) {
                        String uuid = uuidExtra[i].toString();
                    }
                }
            }
            //已搜素完成
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(mContext, "搜索完成", Toast.LENGTH_LONG).show();
                if(mBluetoothSearchDevices.size()<5) {
                    mBlueToothOpenListen.blueToothSearchFinish();
                    cancelSearchDevice();
                }
            }
            else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
//                Toast.makeText(mContext, device.getName() + "已连接", Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
//                Toast.makeText(mContext, device.getName() + "正在断开蓝牙连接。。。", Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
//                Toast.makeText(mContext, device.getName() + "蓝牙连接已断开！！！", Toast.LENGTH_LONG).show();
            }

            else if(action.equals(BluetoothAdapter.STATE_OFF))
            {
//                Toast.makeText(mContext, "蓝牙已关闭", Toast.LENGTH_LONG).show();
            }
            else if(action.equals(BluetoothAdapter.STATE_ON))
            {
//                Toast.makeText(mContext, "蓝牙打开", Toast.LENGTH_LONG).show();
            }
            //配对时，状态改变时
            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Toast.makeText(mContext,"正在配对......",Toast.LENGTH_SHORT);
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Toast.makeText(mContext,"完成配对......",Toast.LENGTH_SHORT);
                    mBlueToothOpenListen.blueToothPairSucces(device);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Toast.makeText(mContext,"取消配对......",Toast.LENGTH_SHORT);
                        mBlueToothOpenListen.blueToothPairFail();
                    default:
                        break;
                }
            }
        }
    };

    /**
     * 判断需要使用的设备是否已经配对
     * @return
     */
    public boolean isPair(BluetoothDevice selectBluetoothDevice){
        searchHistoryDevices(1);
        if(mBluetoothHistoryDevices.size()>0 && selectBluetoothDevice!=null){
            for (BluetoothDevice bluetoothDevice:mBluetoothHistoryDevices){
                if(bluetoothDevice.getAddress().equals(selectBluetoothDevice.getAddress())){
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }

    /**
     * 蓝牙进行配对
     * @param selectBluetoothDevice
     */
    public void bluetoothPair(BluetoothDevice selectBluetoothDevice){
        if (selectBluetoothDevice!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                selectBluetoothDevice.connectGatt(mContext, true, mGattCallback);
            }
            ConnectThread connectThread = new ConnectThread(mBluetoothAdapter, selectBluetoothDevice);
            Thread thread = new Thread(connectThread);
            thread.start();
        }
    }

    private final BluetoothGattCallback mGattCallback =new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            Log.d("gg===callBack==", "1");
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
            Log.d("gg===callBack==", "2");
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d("gg===callBack==", "3");
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    gatt.discoverServices(); //执行到这里其实蓝牙已经连接成功了
                }
                Log.d("gg===callBack==", "Connected to GATT server.");
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d("gg===callBack==", "重新连接");
                    Log.d("gg===callBack==", "Disconnected from GATT server.");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d("gg===callBack==", "4");
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d("gg===callBack==", "5");
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d("gg===callBack==", "6");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d("gg===callBack==", "7");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d("gg===callBack==", "8");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d("gg===callBack==", "9");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.d("gg===callBack==", "10");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.d("gg===callBack==", "11");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.d("gg===callBack==", "12");
        }
    };

    /**
     * 监听蓝牙回调，需要在上一个activity onActivityResult里面调用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (BaseAction.OPENBLUETOOTH == requestCode) {
                mIsOpenBluetooth=true;
                if(mBlueToothOpenListen!=null){
                    mBlueToothOpenListen.blueToothOpenSuccess();
                }
                Toast.makeText(mContext,"该设备蓝牙已经打开。。。",Toast.LENGTH_SHORT);
            }else{
                mIsOpenBluetooth=false;
                if(mBlueToothOpenListen!=null){
                    mBlueToothOpenListen.blueToothOpenFaile();
                }
                Toast.makeText(mContext,"该设备蓝牙未打开，若需进行打印操作，请打开该机器设备的蓝牙。",Toast.LENGTH_SHORT);
            }
        }
    }
    //    可以通过方法startDiscovery(),来扫描设备周边可以使用的其他的蓝牙设备，这个方法会触发下面的广播
    String ACTION_DISCOVERY_STARTED = "android.bluetooth.adapter.action.DISCOVERY_STARTED";  //开始扫描
    String ACTION_DISCOVERY_FINISHED = "android.bluetooth.adapter.action.DISCOVERY_FINISHED"; //扫描结束
    //    扫描到可用的设备，还会触发广播,属于类 `BluetoothDevice`
    String ACTION_FOUND = "android.bluetooth.device.action.FOUND";
    //    在扫描到设备之后，可用调用方法fetchUuidsWithSdp(),调用这个方法会触发这个广播
    String ACTION_UUID = "android.bluetooth.device.action.UUID";
}
