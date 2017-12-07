package SimpleControls.MBlueToothTool;


import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Administrator
 * @date 2017/12/6
 */

public class BluetoothModel implements Parcelable {
    int layoutState;
    String title;
    BlueTtoothList blueTtoothList;

    public int getLayoutState() {
        return layoutState;
    }

    public void setLayoutState(int layoutState) {
        this.layoutState = layoutState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BlueTtoothList getBlueTtoothList() {
        return blueTtoothList;
    }

    public void setBlueTtoothList(BlueTtoothList blueTtoothList) {
        this.blueTtoothList = blueTtoothList;
    }

    public static class BlueTtoothList implements Parcelable {
        int state;
        BluetoothDevice bluetoothDevice;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public BluetoothDevice getBluetoothDevice() {
            return bluetoothDevice;
        }

        public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
            this.bluetoothDevice = bluetoothDevice;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.state);
            dest.writeParcelable(this.bluetoothDevice, flags);
        }

        public BlueTtoothList() {
        }

        protected BlueTtoothList(Parcel in) {
            this.state = in.readInt();
            this.bluetoothDevice = in.readParcelable(BluetoothDevice.class.getClassLoader());
        }

        public static final Creator<BlueTtoothList> CREATOR = new Creator<BlueTtoothList>() {
            @Override
            public BlueTtoothList createFromParcel(Parcel source) {
                return new BlueTtoothList(source);
            }

            @Override
            public BlueTtoothList[] newArray(int size) {
                return new BlueTtoothList[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.layoutState);
        dest.writeString(this.title);
        dest.writeParcelable(this.blueTtoothList, flags);
    }

    public BluetoothModel() {
    }

    protected BluetoothModel(Parcel in) {
        this.layoutState = in.readInt();
        this.title = in.readString();
        this.blueTtoothList = in.readParcelable(BlueTtoothList.class.getClassLoader());
    }

    public static final Parcelable.Creator<BluetoothModel> CREATOR = new Parcelable.Creator<BluetoothModel>() {
        @Override
        public BluetoothModel createFromParcel(Parcel source) {
            return new BluetoothModel(source);
        }

        @Override
        public BluetoothModel[] newArray(int size) {
            return new BluetoothModel[size];
        }
    };
}