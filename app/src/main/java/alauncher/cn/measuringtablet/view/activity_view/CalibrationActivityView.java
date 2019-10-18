package alauncher.cn.measuringtablet.view.activity_view;


import alauncher.cn.measuringtablet.bean.CalibrationBean;

public interface CalibrationActivityView {

    void onUIUpdate(CalibrationBean bean);

    void onDataUpdate(int[] values);
}
