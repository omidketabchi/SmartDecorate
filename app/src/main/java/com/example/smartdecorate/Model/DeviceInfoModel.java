package com.example.smartdecorate.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceInfoModel implements Parcelable {

    private String id;
    private String deviceName;
    private String deviceIp;
    private String deviceType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.deviceName);
        dest.writeString(this.deviceIp);
        dest.writeString(this.deviceType);
    }

    public DeviceInfoModel() {
    }

    protected DeviceInfoModel(Parcel in) {
        this.id = in.readString();
        this.deviceName = in.readString();
        this.deviceIp = in.readString();
        this.deviceType = in.readString();
    }

    public static final Parcelable.Creator<DeviceInfoModel> CREATOR = new Parcelable.Creator<DeviceInfoModel>() {
        @Override
        public DeviceInfoModel createFromParcel(Parcel source) {
            return new DeviceInfoModel(source);
        }

        @Override
        public DeviceInfoModel[] newArray(int size) {
            return new DeviceInfoModel[size];
        }
    };
}
