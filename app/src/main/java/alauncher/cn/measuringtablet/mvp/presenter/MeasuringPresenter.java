package alauncher.cn.measuringtablet.mvp.presenter;

import alauncher.cn.measuringtablet.bean.AddInfoBean;
import alauncher.cn.measuringtablet.bean.ParameterBean;

public interface MeasuringPresenter {
    /**
     * 開啓數據獲取計算.
     */
    void startMeasuing();

    void stopMeasuing();

    ParameterBean getParameterBean();

    String saveResult(double[] ms, AddInfoBean bean);

    String[] getMGroupValues(double[] ms);

    int getStep();
}
