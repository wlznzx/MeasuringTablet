package alauncher.cn.measuringtablet.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempResultsBean {
    TemplateResultBean templateResultBean;
    List<ResultBean3> resultBean3s;
    List<String> addImgs;

    public TemplateResultBean getTemplateResultBean() {
        return templateResultBean;
    }

    public void setTemplateResultBean(TemplateResultBean templateResultBean) {
        this.templateResultBean = templateResultBean;
    }

    public List<ResultBean3> getResultBean3s() {
        return resultBean3s;
    }

    public void setResultBean3s(List<ResultBean3> resultBean3s) {
        this.resultBean3s = resultBean3s;
    }

    public List<String> getAddImgs() {
        return addImgs;
    }

    public void setAddImgs(List<String> addImgs) {
        this.addImgs = addImgs;
    }
}