package yg.customcontrol.com.MBluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 *
 * @author Administrator
 * @date 2017/12/1
 * 终于经过了前面的4步，万事俱备只欠东风。而最后这一部分其实是最简单的，因为就只是简单的利用 InputStream 和OutputStream进行数据的收发。
 */

public class ConnectedSendThread extends Thread {
    private BluetoothSocket mmSocket;
//    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private BluetoothDevice mBlueToothDevice;
    private int MESSAGE_READ=10002;
    private int MESSAGE_READ_second=10003;
    int i=0;
    private String mConnetName="myFirstBluetooth";
    private final String mMyBlueToothUUID=
            "00001101-0000-1000-8000-00805F9B34FB";
    public ConnectedSendThread(BluetoothDevice bluetoothDevice) {
        mBlueToothDevice=bluetoothDevice;
//            mmSocket =(BluetoothSocket) mBlueToothDevice.getClass().getMethod("myFirstBluetooth", new Class[] {int.class}).invoke(mBlueToothDevice,1);
            try {
                String str="";
                if(mBlueToothDevice.getUuids()!=null && mBlueToothDevice.getUuids().length>0) {
                    str = str + mBlueToothDevice.getUuids()[0];
                    mConnetName=mBlueToothDevice.getName();
                }else{
                    str = str +mMyBlueToothUUID;
                }
                mmSocket=mBlueToothDevice.createRfcommSocketToServiceRecord(UUID.fromString(str));
            } catch (IOException e) {
                try {
                    mmSocket = (BluetoothSocket)mBlueToothDevice.getClass().getMethod("createRfcommSocket", new Class[]
                            {int
                            .class}).invoke(mBlueToothDevice,1);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
//        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        //通过 socket 得到 InputStream 和 OutputStream
        try {
//            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) { }

//        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
//        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        if (mmSocket != null) {
//                buffer = "OK".getBytes();
            byte[] buffer = {65, 66, 67, 68, 69};
                write(buffer);
        }
    }
    //向 Server 写入数据
    public void write(byte[] bytes) {
//        try {
//            if(mmOutStream!=null) {
//                mmOutStream.write(bytes);
//            }
//
//        } catch (IOException e) {
//            try {
//                mmOutStream.flush();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                cancel();
//            }
//        }
        try {
            final StringBuilder flag = new StringBuilder();
//            new Thread() {
//                @Override
//                public void run() {
//                    long start = System.currentTimeMillis();
//                    while (flag.length()==0) {
//                        long end = System.currentTimeMillis();
//                        if (end - start > 1000) {
//                            try {
//                                mmSocket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        }
//                        try {
//                            sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.start();
            mmOutStream.write(bytes);
            flag.append("end");
        } catch (Exception e) {
            return; //
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
