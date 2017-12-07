package SimpleControls.MBlueToothTool;

import java.io.Serializable;

/**
 * Created by Yang on 2017/6/14.
 */

public class CanvsModel implements Serializable {

    private String OrderPartId;
    private String StationNo;
    private String PackageNo;
    private String UpGoodsStation;
    private String DownGoodsStation;
    private String GoodsName;
    private String Weight;
    private String NumIndex;
    private String TotalNumber;
    private String ReceiveAddress;
    private String ReceivePhone;
    private String ReceiveName;

    public String getOrderPartId() {
        return this.OrderPartId;
    }

    public void setOrderPartId(String orderPartId) {
        this.OrderPartId = orderPartId;
    }

    public String getStationNo() {
        return this.StationNo;
    }

    public void setStationNo(String stationNo) {
        this.StationNo = stationNo;
    }

    public String getPackageNo() {
        return this.PackageNo;
    }

    public void setPackageNo(String packageNo) {
        this.PackageNo = packageNo;
    }

    public String getUpGoodsStation() {
        return this.UpGoodsStation;
    }

    public void setUpGoodsStation(String upGoodsStation) {
        this.UpGoodsStation = upGoodsStation;
    }

    public String getDownGoodsStation() {
        return this.DownGoodsStation;
    }

    public void setDownGoodsStation(String downGoodsStation) {
        this.DownGoodsStation = downGoodsStation;
    }

    public String getGoodsName() {
        return this.GoodsName;
    }

    public void setGoodsName(String goodsName) {
        this.GoodsName = goodsName;
    }

    public String getWeight() {
        return this.Weight;
    }

    public void setWeight(String weight) {
        this.Weight = weight;
    }

    public String getNumIndex() {
        return this.NumIndex;
    }

    public void setNumIndex(String numIndex) {
        this.NumIndex = numIndex;
    }

    public String getTotalNumber() {
        return this.TotalNumber;
    }

    public void setTotalNumber(String TotalNumber) {
        this.TotalNumber = TotalNumber;
    }

    public String getReceiveAddress() {
        return this.ReceiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.ReceiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return this.ReceivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.ReceivePhone = receivePhone;
    }

    public String getReceiveName() {
        return this.ReceiveName;
    }

    public void setReceiveName(String receiveName) {
        this.ReceiveName = receiveName;
    }
}
