package alauncher.cn.measuringtablet.utils;

public class CommonUtil {
    public static boolean isNull(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }
        return false;
    }
}
