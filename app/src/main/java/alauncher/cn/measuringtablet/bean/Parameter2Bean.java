package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Parameter2Bean {

    @Id(autoincrement = true)
    public Long id;

    public long code_id;

    public int index;

    public boolean enable;

    public String describe;

    public double nominal_value;

    public double upper_tolerance_value;

    public double lower_tolerance_value;

    @Generated(hash = 280239974)
    public Parameter2Bean(Long id, long code_id, int index, boolean enable,
            String describe, double nominal_value, double upper_tolerance_value,
            double lower_tolerance_value) {
        this.id = id;
        this.code_id = code_id;
        this.index = index;
        this.enable = enable;
        this.describe = describe;
        this.nominal_value = nominal_value;
        this.upper_tolerance_value = upper_tolerance_value;
        this.lower_tolerance_value = lower_tolerance_value;
    }

    @Generated(hash = 1675772578)
    public Parameter2Bean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCode_id() {
        return this.code_id;
    }

    public void setCode_id(long code_id) {
        this.code_id = code_id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getNominal_value() {
        return this.nominal_value;
    }

    public void setNominal_value(double nominal_value) {
        this.nominal_value = nominal_value;
    }

    public double getUpper_tolerance_value() {
        return this.upper_tolerance_value;
    }

    public void setUpper_tolerance_value(double upper_tolerance_value) {
        this.upper_tolerance_value = upper_tolerance_value;
    }

    public double getLower_tolerance_value() {
        return this.lower_tolerance_value;
    }

    public void setLower_tolerance_value(double lower_tolerance_value) {
        this.lower_tolerance_value = lower_tolerance_value;
    }

}
