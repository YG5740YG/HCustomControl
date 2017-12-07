package yg.customcontrol.com.MBluetooth;

/**
 *
 * @author Administrator
 * @date 2017/12/1
 * Server 端
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 *通过BluetoothAdapter.listenUsingRfcommWithServiceRecord(String, UUID)获取一个 BluetoothServerSocket 对象。
 * 这里传入的第一个参数用来设置服务的名称，当其他设备扫描的时候就会显示这个名称。
 * 调用BluetoothServerSocket.accept()开始监听连接请求。这是一个阻塞操作，所以当然也要放在主线程之外进行。当该操作成功执行，
 * 即有连接建立的时候，会返回一个BluetoothSocket 对象。
 * 调用 BluetoothServerSocket.close() 会关闭监听连接的服务，但是当前已经建立的链接并不会受影响。
 */
public class AcceptThread extends Thread {
    private BluetoothServerSocket mmServerSocket;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private String mConnetName="myFirstBluetooth";
    private final String  mMyBlueToothUUID= "abcd1234-ab12-ab12-ab12-abcdef123456";
    private InputStream is;//输入流
    Context context;

    public AcceptThread(BluetoothAdapter bluetoothAdapter, BluetoothDevice bluetoothDevice,Context context) {
        this.context=context;
        mBluetoothAdapter=bluetoothAdapter;
        mBluetoothDevice=bluetoothDevice;
        BluetoothServerSocket tmp = null;
        String str="";
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(mConnetName, UUID.fromString
                    (mMyBlueToothUUID));
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }
    @Override
    public void run() {
        BluetoothSocket socket = null;
        //阻塞操作
        while (true) {
            try {
                socket = mmServerSocket.accept();
                is = socket.getInputStream();
                while(true) {
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                cancel();
                break;
            }
        }
    }

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(context, String.valueOf(msg.obj),
                    Toast.LENGTH_LONG).show();
            cancel();
            super.handleMessage(msg);
        }
    };
}
