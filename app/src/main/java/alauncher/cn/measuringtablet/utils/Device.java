package alauncher.cn.measuringtablet.utils;

public abstract class Device {
    private String name; // 设备名称
    private String ip; // 设备IP
    private int port;  // 端口号

    public boolean doJob(String code, String param){
        return true;
    }
}
