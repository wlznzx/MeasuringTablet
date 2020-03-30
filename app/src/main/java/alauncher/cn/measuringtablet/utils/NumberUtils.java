package alauncher.cn.measuringtablet.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
    public static DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");

    public static String get4bits(Double vaule) {
        return decimalFormat.format(vaule);
    }

    ;

    public static String notScience(Double value) {
        String _value = String.valueOf(value);
        if (_value.contains("E")) {
            _value = new BigDecimal(value + "").setScale(4, BigDecimal.ROUND_HALF_UP).toString();
        }
        return _value;
    }
}
