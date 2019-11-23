package alauncher.cn.measuringtablet.utils;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.bean.CodeBean;

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
        // 1 - 5
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径表面粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔锥孔部粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径表面粗糙度\nRa0.2以下", 0, 0.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("止推面表面粗糙度\nRa0.4以下", 0, 0.4, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面粗糙度\nRa1.6以下", 0, 1.6, 0));
        // 5 - 10
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴安装孔外径粗糙度\nRa1.2以下", 0, 1.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("定子面表面粗糙度\nRa3.2~6.4", 3.2, 3.2, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔到阀面间距\n60.13±0.02", 60.13, 0.02, -0.02));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔到定子面直角度\n80um以下", 0, 0.08, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔到曲轴孔直角度\n-50~100/150um", 0, 0.1, -0.05));
        // 11 - 15
        mDefaultParameterBeanList.add(new DefaultParameterBean("止推面直角度\n20um以下", 0, 0.02, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("轴承安装部同心度\n100um以下", 0, 0.1, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面直角度\n25um以下", 0, 0.025, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面平面度\n25um以下", 0, 0.025, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔到阀面直角度\n25um以下", 0, 0.025, 0));
        // 16 - 20
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔直孔部内孔尺寸\n25.396~25.406", 25.4, 0.006, -0.004));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔锥孔部内孔尺寸\n25.396~25.406", 25.4, 0.006, -0.004));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径尺寸\n17.921~17.925", 17.9, 0.025, 0.021));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径锥度\n3um以下", 0, 0.003, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径圆柱度（全）\n4um以下", 0, 0.004, 0));
        // 21 - 27
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径圆柱度\n2um以下", 0, 0.002, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔内径圆度（锥孔18mm）\n2um以下", 0, 0.002, 0));

        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径圆柱度\n4um以下", 0, 0.004, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔内径圆度\n2um以下", 0, 0.002, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔直孔长度\n8~12", 8, 4, 0));
        mDefaultParameterBeanList.add(new DefaultParameterBean("气缸孔倒角\n0.1~0.4", 0, 0.4, 0.1));
        mDefaultParameterBeanList.add(new DefaultParameterBean("阀面排气孔尺寸\n5.2±0.25", 5.2, 0.25, -0.25));
        mDefaultParameterBeanList.add(new DefaultParameterBean("阀面排气孔通孔尺寸\n5±0.25", 5, 0.25, -0.25));
        mDefaultParameterBeanList.add(new DefaultParameterBean("外观检查\n毛刺生锈伤痕加工不全混机种", 1, 0.2, -0.2));
    }


    public static List<DefaultParameterBean> m1_15DefaultParameterBeanList = new ArrayList<>();
    public static List<DefaultParameterBean> m16_27DefaultParameterBeanList = new ArrayList<>();
    public static List<DefaultParameterBean> m28_30DefaultParameterBeanList = new ArrayList<>();
    public static List<DefaultParameterBean> m31_45DefaultParameterBeanList = new ArrayList<>();
    public static List<DefaultParameterBean> m43_45DefaultParameterBeanList = new ArrayList<>();

    static {
        // 1 - 15
        m1_15DefaultParameterBeanList.add(new DefaultParameterBean("定子脚粗糙度\nRa3.2~6.4", 3.2, 3.2, 0));
        m1_15DefaultParameterBeanList.add(new DefaultParameterBean("定子脚粗糙度\nRa3.2~6.4", 3.2, 3.2, 0));

        // 16 - 27
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("阀面平面度\n50um以下", 0, 0.05, 0));
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("阀面平面度\n50um以下", 0, 0.05, 0));
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("阀面粗糙度\nRa3.2以下", 0, 3.2, 0));
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("滚珠轴承面粗糙度\nRa1.6以下", 0, 1.6, 0));
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("曲孔安装孔外径粗糙度\nRa1.2以下", 0, 1.2, 0));
        m16_27DefaultParameterBeanList.add(new DefaultParameterBean("止推面外圆倒角\nC0.1~0.4", 0, 0.4, 0.1));

        // 28 - 30
        m28_30DefaultParameterBeanList.add(new DefaultParameterBean("阀面平面度\n50um以下", 0, 0.05, 0));
        m28_30DefaultParameterBeanList.add(new DefaultParameterBean("阀面粗糙度\nRa3.2以下", 0, 3.2, 0));
        m28_30DefaultParameterBeanList.add(new DefaultParameterBean("C部直径（止推面）\n20.5~21", 20.5, 0.5, 0));
        m28_30DefaultParameterBeanList.add(new DefaultParameterBean("E部直径（止推面）\n26.15~26.65", 26.15, 0.5, 0));

        // 31 - 42
        m31_45DefaultParameterBeanList.add(new DefaultParameterBean("气缸孔圆柱度\n2um以下", 0, 0.002, 0));
        m31_45DefaultParameterBeanList.add(new DefaultParameterBean("气缸孔圆柱度\n2um以下", 0, 0.002, 0));
        m31_45DefaultParameterBeanList.add(new DefaultParameterBean("气缸孔粗糙度\nRa0.2以下", 0, 0.2, 0));
        m31_45DefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔粗糙度\nRa0.2以下", 0, 0.2, 0));

        // 43 - 45
        m43_45DefaultParameterBeanList.add(new DefaultParameterBean("气缸孔圆柱度\n2um以下", 0, 0.002, 0));
        m43_45DefaultParameterBeanList.add(new DefaultParameterBean("气缸孔粗糙度\nRa0.2以下", 0, 0.2, 0));
        m43_45DefaultParameterBeanList.add(new DefaultParameterBean("曲轴孔粗糙度\nRa0.2以下", 0, 0.2, 0));
        m43_45DefaultParameterBeanList.add(new DefaultParameterBean("止推面粗糙度\nRa0.4以下", 0, 0.4, 0));
    }


    public static List<CodeBean> defaultCodeBeans = new ArrayList<>();

    static {
        defaultCodeBeans.add(new CodeBean(0, "TRA100", "", "", false, null));
        // 1 ~ 15
        defaultCodeBeans.add(new CodeBean(1, "十工位-TRA100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(2, "十工位-TRA91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(3, "十工位-TRA120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(4, "十工位-TRB100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(5, "十工位-TRB91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(6, "十工位-TRB120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(7, "十工位-ERI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(8, "十工位-ERI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(9, "十工位-ERI120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(10, "十工位-TKD100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(11, "十工位-TKD91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(12, "十工位-TKD120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(13, "十工位-EEI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(14, "十工位-EEI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(15, "十工位-EEI120", "", "", false, null));
        // 16 ~30
        defaultCodeBeans.add(new CodeBean(16, "四工位-TRA100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(17, "四工位-TRA91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(18, "四工位-TRA120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(19, "四工位-TRB100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(20, "四工位-TRB91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(21, "四工位-TRB120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(22, "四工位-ERI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(23, "四工位-ERI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(24, "四工位-ERI120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(25, "四工位-TKD100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(26, "四工位-TKD91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(27, "四工位-TKD120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(28, "四工位-EEI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(29, "四工位-EEI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(30, "四工位-EEI120", "", "", false, null));
        // 31 ~ 45
        defaultCodeBeans.add(new CodeBean(31, "大足-TRA100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(32, "大足-TRA91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(33, "大足-TRA120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(34, "大足-TRB100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(35, "大足-TRB91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(36, "大足-TRB120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(37, "大足-ERI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(38, "大足-ERI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(39, "大足-ERI120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(40, "大足-TKD100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(41, "大足-TKD91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(42, "大足-TKD120", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(43, "大足-EEI100", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(44, "大足-EEI91", "", "", false, null));
        defaultCodeBeans.add(new CodeBean(45, "大足-EEI120", "", "", false, null));
    }

}
