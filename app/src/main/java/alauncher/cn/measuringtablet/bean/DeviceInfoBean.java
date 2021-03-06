package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 日期：2019/8/5 0025 9:27
 * 包名：alauncher.cn.measuringtablet.bean
 * 作者： wlznzx
 * 描述：
 */
@Entity
public class DeviceInfoBean {

    @Id(autoincrement = true)
    public Long id;

    public String factoryCode;

    public String factoryName;

    public String deviceCode;

    public String deviceName;

    public String manufacturer;

    public String rmk;

    public int startHour;

    public int stopHour;

    public int startMin;

    public int stopMin;

    @Generated(hash = 47425869)
    public DeviceInfoBean(Long id, String factoryCode, String factoryName, String deviceCode,
            String deviceName, String manufacturer, String rmk, int startHour, int stopHour,
            int startMin, int stopMin) {
        this.id = id;
        this.factoryCode = factoryCode;
        this.factoryName = factoryName;
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.manufacturer = manufacturer;
        this.rmk = rmk;
        this.startHour = startHour;
        this.stopHour = stopHour;
        this.startMin = startMin;
        this.stopMin = stopMin;
    }

    @Override
    public String toString() {
        return "DeviceInfoBean{" +
                "id=" + id +
                ", factoryCode='" + factoryCode + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", rmk='" + rmk + '\'' +
                '}';
    }

    @Generated(hash = 784809703)
    public DeviceInfoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactoryCode() {
        return this.factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getFactoryName() {
        return this.factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDeviceCode() {
        return this.deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getRmk() {
        return this.rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStopHour() {
        return this.stopHour;
    }

    public void setStopHour(int stopHour) {
        this.stopHour = stopHour;
    }

    public int getStartMin() {
        return this.startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getStopMin() {
        return this.stopMin;
    }

    public void setStopMin(int stopMin) {
        this.stopMin = stopMin;
    }
}
