package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TemplateBean {

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleList;

    // AQL
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> AQLList;

    // RoHS
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> RoHSList;

    public String headerLeft;

    public String headerMid;

    public String headerRight;

    public String footerLeft;

    public String footerMid;

    public String footerRight;


    @Generated(hash = 317713024)
    public TemplateBean(List<String> titleList, List<String> AQLList,
                        List<String> RoHSList, String headerLeft, String headerMid,
                        String headerRight, String footerLeft, String footerMid,
                        String footerRight) {
        this.titleList = titleList;
        this.AQLList = AQLList;
        this.RoHSList = RoHSList;
        this.headerLeft = headerLeft;
        this.headerMid = headerMid;
        this.headerRight = headerRight;
        this.footerLeft = footerLeft;
        this.footerMid = footerMid;
        this.footerRight = footerRight;
    }

    @Generated(hash = 741639705)
    public TemplateBean() {
    }


    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getAQLList() {
        return AQLList;
    }

    public void setAQLList(List<String> AQLList) {
        this.AQLList = AQLList;
    }

    public List<String> getRoHSList() {
        return RoHSList;
    }

    public void setRoHSList(List<String> roHSList) {
        RoHSList = roHSList;
    }

    public String getHeaderLeft() {
        return headerLeft;
    }

    public void setHeaderLeft(String headerLeft) {
        this.headerLeft = headerLeft;
    }

    public String getHeaderMid() {
        return headerMid;
    }

    public void setHeaderMid(String headerMid) {
        this.headerMid = headerMid;
    }

    public String getHeaderRight() {
        return headerRight;
    }

    public void setHeaderRight(String headerRight) {
        this.headerRight = headerRight;
    }

    public String getFooterLeft() {
        return footerLeft;
    }

    public void setFooterLeft(String footerLeft) {
        this.footerLeft = footerLeft;
    }

    public String getFooterMid() {
        return footerMid;
    }

    public void setFooterMid(String footerMid) {
        this.footerMid = footerMid;
    }

    public String getFooterRight() {
        return footerRight;
    }

    public void setFooterRight(String footerRight) {
        this.footerRight = footerRight;
    }
}