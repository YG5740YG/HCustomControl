package yg.customcontrol.com.MBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 *
 * @author Administrator
 * @date 2017/12/1
 * Client 端
 */
/**
 * 蓝牙设备的连接和网络连接的模型十分相似，都是Client-Server 模式，都通过一个 socket 来进行数据传输。那么作为一个 Android 设备，就存在三种情况：
 只作为 Client 端发起连接
 只作为 Server 端等待别人发起建立连接的请求
 同时作为 Client 和 Server
 Android 设备作为 Client 建立连接的情况。因为打印机是不可能主动跟 Android 设备建立连接的，所以打印机必然是作为 Server 被连接。
 1,首先需要获取一个 BluetoothDevice 对象。
 2,通过 BluetoothDevice.createRfcommSocketToServiceRecord(UUID) 得到 BluetoothSocket 对象
 3,通过BluetoothSocket.connect()建立连接
 4，异常处理以及连接关闭
 UUID：它是一串约定格式的字符串，用来唯一的标识一种蓝牙服务。Client 发起连接时传入的 UUID 必须要和 Server 端设置的一样！否则就会报错！
 一些常见的蓝牙服务协议已经有约定的 UUID。比如我们连接热敏打印机是基于 SPP 串口通信协议，其对应的 UUID 是 "00001101-0000-1000-8000-00805F9B34FB"
 只是用于自己的应用之间的通信的话，那么理论上可以随便定义一个 UUID，只要 server 和 client 两边使用的 UUID 一致即可。
 UUID:http://dxjia.cn/2016/01/29/android-bluetooth-uuid/
 */
public class ConnectThread extends Thread {
    private BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private String mConnetName="myFirstBluetooth";
    private String mMyBlueToothUUID=
            "abcd1234-ab12-ab12-ab12-abcdef123456";
    private OutputStream os;//输出流

    public ConnectThread(BluetoothAdapter bluetoothAdapter, BluetoothDevice device) {
        mBluetoothAdapter=bluetoothAdapter;
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
//            mMyBlueToothUUID=""+device.getUuids()[0];
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(mMyBlueToothUUID));
        } catch (IOException e) { }
        mmSocket = tmp;
    }
    @Override
    public void run() {
        // 建立连接前记得取消设备发现
        mBluetoothAdapter.cancelDiscovery();
        try {
            // 耗时操作，所以必须在主线程之外进行
            mmSocket.connect();
            //获得输出流（客户端指向服务端输出文本）
            os = mmSocket.getOutputStream();
            if (os != null) {
                //往服务端写信息
                os.write("蓝牙信息来了".getBytes("utf-8"));
            }
            cancel();
        } catch (IOException connectException) {
            //处理连接建立失败的异常
//            1、发起端，通过反射把端口改成1：
//            socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class})
//                    .invoke(device,1);
//            socket.connect();
//            2、接收端，severce,服务器监听端口可能不是1
//            Method listenMethod = btClass.getMethod("listenUsingRfcommOn", new Class[]{int.class});
//            BluetoothServerSocket returnValue = ( BluetoothServerSocket) listenMethod.invoke(btAdapter, new Object[]{ 29});
            try {
                try {
//                    首次配对连接
                    mmSocket = (BluetoothSocket)mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int
                            .class}).invoke(mmDevice,1);
                    mmSocket.connect();
                } catch (IllegalAccessException e) {
                    cancel();
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    cancel();
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    cancel();
                    e.printStackTrace();
                }
            } catch (IOException closeException) {
                cancel();
            }
            return;
        }
    }
    //关闭一个正在进行的连接
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
