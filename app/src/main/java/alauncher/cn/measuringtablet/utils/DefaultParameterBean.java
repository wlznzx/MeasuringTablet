package alauncher.cn.measuringtablet.utils;

public class DefaultParameterBean {
    public String name;
    public double normalValue;
    public double upperLimit;
    public double lowerLimit;

    public DefaultParameterBean(String name, double normalValue, double upperLimit, double lowerLimit) {
        this.name = name;
        this.normalValue = normalValue;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }
}
