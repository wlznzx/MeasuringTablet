package alauncher.cn.measuringtablet.utils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringConverter implements PropertyConverter<List<String>, String> {

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else if (databaseValue.equals("")) {
            return new ArrayList<>();
        } else {
            List<String> list = Arrays.asList(databaseValue.split(","));
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String link : entityProperty) {
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }

    public static String convertToDatabaseValueS(List<String> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String link : entityProperty) {
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
