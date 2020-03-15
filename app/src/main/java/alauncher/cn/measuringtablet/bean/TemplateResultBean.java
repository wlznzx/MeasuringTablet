package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TemplateResultBean {

    @Id
    public long codeID;

    public String allJudge;

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleList;

    // AQL
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> AQLList;

    // RoHS
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> RoHSList;

    @Generated(hash = 1040133342)
    public TemplateResultBean(long codeID, String allJudge, List<String> titleList,
            List<String> AQLList, List<String> RoHSList) {
        this.codeID = codeID;
        this.allJudge = allJudge;
        this.titleList = titleList;
        this.AQLList = AQLList;
        this.RoHSList = RoHSList;
    }

    @Generated(hash = 1091879955)
    public TemplateResultBean() {
    }

    public long getCodeID() {
        return this.codeID;
    }

    public void setCodeID(long codeID) {
        this.codeID = codeID;
    }

    public List<String> getTitleList() {
        return this.titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getAQLList() {
        return this.AQLList;
    }

    public void setAQLList(List<String> AQLList) {
        this.AQLList = AQLList;
    }

    public List<String> getRoHSList() {
        return this.RoHSList;
    }

    public void setRoHSList(List<String> RoHSList) {
        this.RoHSList = RoHSList;
    }

    public String getAllJudge() {
        return this.allJudge;
    }

    public void setAllJudge(String allJudge) {
        this.allJudge = allJudge;
    }

}