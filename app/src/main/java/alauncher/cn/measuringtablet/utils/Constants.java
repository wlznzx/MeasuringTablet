package alauncher.cn.measuringtablet.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2019/5/9 0009 15:29
 * 包名：alauncher.cn.measuringtablet.utils
 * 作者： wlznzx
 * 描述：
 */
public class Constants {

    public static final double[] D4 = {3.27, 2.57, 2.28, 2.11, 2.00, 1.92, 1.86, 1.82, 1.78};

    public static final double[] D3 = {0, 0, 0, 0, 0, 0.08, 0.14, 0.18, 0.22};

    public static final double[] A2 = {1.88, 1.02, 0.73, 0.58, 0.48, 0.42, 0.34, 0.34, 0.31};

    public static final double[] d2 = {1.13, 1.69, 2.06, 2.33, 2.53, 2.70, 2.85, 2.97, 3.08};

    public static final String IP_KEY = "IP_KEY";

    public static final String INPUT_TIME_KEY = "INPUT_TIME_KEY";

    public static List<DefaultParameterBean> mDefaultParameterBeanList;

    static {
        mDefaultParameterBeanList = new ArrayList<>();

        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径表面粗糙度\nRa0.2以下", 0, 0.2, 0));

        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径表面粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔锥孔部粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径表面粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("止推面表面粗糙度\nRa0.4以下", 0, 0.4, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面粗糙度\nRa1.6以下", 0, 1.6, 0));

        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴安装孔外径粗糙度\nRa1.2以下", 0, 1.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("定子面表面粗糙度\nRa3.2~6.4", 3.2, 3.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔到阀面间距\n60.13±0.02", 60.13, 0.02, -0.02));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔到定子面直角度\n80um以下", 0, 0.08, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔到曲轴孔直角度\n-50~100/150um", 0, 0.1, -0.05));

        mDefaultParameterBeanList.add(new DefaultParameterBean("止推面直角度\n20um以下", 0, 0.02, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面直角度\n25um以下", 0, 0.025, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面平面度\n25um以下", 0, 0.025, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内孔尺寸\n25.396~25.406", 25.4, 0.006, -0.004));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径尺寸\n17.921~17.925", 17.9, 0.025, 0.021));

        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径锥度\n3um以下", 0, 0.003, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径圆柱度（全）\n4um以下", 0, 0.004, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径圆柱度\n2um以下", 0, 0.002, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径圆柱度\n4um以下", 0, 0.004, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔锥孔部圆度\n2um以下", 0, 0.002, 0));

        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径圆度\n2um以下", 0, 0.002, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔直孔长度\n20", 20, 0.2, 0));

        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n毛刺", 1, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n生锈", 1, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n伤痕", 1, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n加工不全", 1, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n混种机", 1, 0.2, 0));
    }

}
