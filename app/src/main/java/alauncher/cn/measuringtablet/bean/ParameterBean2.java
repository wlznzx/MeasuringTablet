package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ParameterBean2 {

    @Id(autoincrement = true)
    public Long id;

    public long codeID;

    public int sequenceNumber;

    // 描述
    public String describe;

    // 名义值
    public double nominalValue;

    // 上公差
    public double upperToleranceValue;

    // 下公差
    public double lowerToleranceValue;

    // 偏移
    public double deviation;

    // 分辨率，精度
    public int resolution;

    // 
    public double scale;

    // 计算公式
    public String code;

    // 使能
    public boolean enable;

    public int type;

    public String name;

    @Generated(hash = 572636147)
    public ParameterBean2(Long id, long codeID, int sequenceNumber, String describe,
                          double nominalValue, double upperToleranceValue, double lowerToleranceValue,
                          double deviation, int resolution, double scale, String code, boolean enable,
                          int type, String name) {
        this.id = id;
        this.codeID = codeID;
        this.sequenceNumber = sequenceNumber;
        this.describe = describe;
        this.nominalValue = nominalValue;
        this.upperToleranceValue = upperToleranceValue;
        this.lowerToleranceValue = lowerToleranceValue;
        this.deviation = deviation;
        this.resolution = resolution;
        this.scale = scale;
        this.code = code;
        this.enable = enable;
        this.type = type;
        this.name = name;
    }

    @Generated(hash = 1020281904)
    public ParameterBean2() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCodeID() {
        return this.codeID;
    }

    public void setCodeID(long codeID) {
        this.codeID = codeID;
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getNominalValue() {
        return this.nominalValue;
    }

    public void setNominalValue(double nominalValue) {
        this.nominalValue = nominalValue;
    }

    public double getUpperToleranceValue() {
        return this.upperToleranceValue;
    }

    public void setUpperToleranceValue(double upperToleranceValue) {
        this.upperToleranceValue = upperToleranceValue;
    }

    public double getLowerToleranceValue() {
        return this.lowerToleranceValue;
    }

    public void setLowerToleranceValue(double lowerToleranceValue) {
        this.lowerToleranceValue = lowerToleranceValue;
    }

    public double getDeviation() {
        return this.deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public int getResolution() {
        return this.resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParameterBean2{" +
                "id=" + id +
                ", codeID=" + codeID +
                ", sequenceNumber=" + sequenceNumber +
                ", describe='" + describe + '\'' +
                ", nominalValue=" + nominalValue +
                ", upperToleranceValue=" + upperToleranceValue +
                ", lowerToleranceValue=" + lowerToleranceValue +
                ", deviation=" + deviation +
                ", resolution=" + resolution +
                ", scale=" + scale +
                ", code='" + code + '\'' +
                ", enable=" + enable +
                ", type=" + type +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
