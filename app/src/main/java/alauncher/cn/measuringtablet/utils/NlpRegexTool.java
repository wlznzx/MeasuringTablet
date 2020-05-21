package alauncher.cn.measuringtablet.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NlpRegexTool {

    private static final String _END = "##!!&&&&";
    /**
     * des:正则表达式匹配多个group 例：String mydata = "我要买北京到上海的机票###";//最后一个啊字补位 Pattern
     * pattern = Pattern.compile("我要买(.*?)到(.*?)的(.*?)###");
     *
     * @param content
     * @param regxStr
     * @param codes
     * @param groupCount
     * @return
     */
    public static Map<String, String> matchMultiGroup(String content,
                                                      String regxStr, List<String> codes, int groupCount) {
        Map<String, String> codeGroupMap = new HashMap<String, String>();
        try {
            Pattern pattern = Pattern.compile(regxStr + _END);
            Matcher matcher = pattern.matcher(content.trim() + _END);
            if (matcher.find()) {
                for (int i = 0; i < codes.size(); i++) {
                    String code = codes.get(i);
                    codeGroupMap.put(code, matcher.group(i + 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeGroupMap;
    }
}
