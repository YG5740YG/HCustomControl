package yg.customcontrol.com.MBluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ConnectedAcceptThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private int MESSAGE_READ=10002;
    private int MESSAGE_READ_second=10003;
    int i=0;
    private final String mConnetName="myFirstBluetooth";
    private final UUID mMyBlueToothUUID=
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public ConnectedAcceptThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        //通过 socket 得到 InputStream 和 OutputStream
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {

        if(mmSocket!=null) {
            try {
                OutputStream os = mmSocket.getOutputStream();
                os.write("OK".getBytes(),0,"OK".getBytes().length);
                os.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        //不断的从 InputStream 取数据
        while (true) {
            try {
                bytes = mmInStream.read(buffer);
                mHandler.obtainMessage(MESSAGE_READ, bytes, MESSAGE_READ_second, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10002:
                    Log.d("blueTooth===first=",i+"=="+msg.getData().toString());
                    break;
                case 10003:
                    Log.d("blueTooth===second=",i+"=="+msg.getData().toString());
                    break;
            }
            i++;
            super.handleMessage(msg);
        }
    };
    //向 Server 写入数据
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}

