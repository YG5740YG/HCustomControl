package SimpleControls.MScrollControl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class DrawerContentModel  implements Parcelable {
    private int icon;
    private String content;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.content);
    }

    public DrawerContentModel() {
    }

    protected DrawerContentModel(Parcel in) {
        this.icon = in.readInt();
        this.content = in.readString();
    }

    public static final Creator<DrawerContentModel> CREATOR = new Creator<DrawerContentModel>() {
        @Override
        public DrawerContentModel createFromParcel(Parcel source) {
            return new DrawerContentModel(source);
        }

        @Override
        public DrawerContentModel[] newArray(int size) {
            return new DrawerContentModel[size];
        }
    };
}
