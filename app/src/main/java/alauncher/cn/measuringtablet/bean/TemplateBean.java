package alauncher.cn.measuringtablet.bean;

import android.widget.Switch;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TemplateBean {

    @Id
    public long codeID;

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleList;

    // 签名;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> signList;

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

    public String title;

    public boolean maximumEnable, minimumEnable, averageEnable, rangeEnable, judgeEnable;

    @Generated(hash = 1733318951)
    public TemplateBean(long codeID, List<String> titleList, List<String> signList,
            List<String> AQLList, List<String> RoHSList, String headerLeft, String headerMid,
            String headerRight, String footerLeft, String footerMid, String footerRight,
            String title, boolean maximumEnable, boolean minimumEnable, boolean averageEnable,
            boolean rangeEnable, boolean judgeEnable) {
        this.codeID = codeID;
        this.titleList = titleList;
        this.signList = signList;
        this.AQLList = AQLList;
        this.RoHSList = RoHSList;
        this.headerLeft = headerLeft;
        this.headerMid = headerMid;
        this.headerRight = headerRight;
        this.footerLeft = footerLeft;
        this.footerMid = footerMid;
        this.footerRight = footerRight;
        this.title = title;
        this.maximumEnable = maximumEnable;
        this.minimumEnable = minimumEnable;
        this.averageEnable = averageEnable;
        this.rangeEnable = rangeEnable;
        this.judgeEnable = judgeEnable;
    }

    @Generated(hash = 741639705)
    public TemplateBean() {
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

    public String getHeaderLeft() {
        return this.headerLeft;
    }

    public void setHeaderLeft(String headerLeft) {
        this.headerLeft = headerLeft;
    }

    public String getHeaderMid() {
        return this.headerMid;
    }

    public void setHeaderMid(String headerMid) {
        this.headerMid = headerMid;
    }

    public String getHeaderRight() {
        return this.headerRight;
    }

    public void setHeaderRight(String headerRight) {
        this.headerRight = headerRight;
    }

    public String getFooterLeft() {
        return this.footerLeft;
    }

    public void setFooterLeft(String footerLeft) {
        this.footerLeft = footerLeft;
    }

    public String getFooterMid() {
        return this.footerMid;
    }

    public void setFooterMid(String footerMid) {
        this.footerMid = footerMid;
    }

    public String getFooterRight() {
        return this.footerRight;
    }

    public void setFooterRight(String footerRight) {
        this.footerRight = footerRight;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getMaximumEnable() {
        return this.maximumEnable;
    }

    public void setMaximumEnable(boolean maximumEnable) {
        this.maximumEnable = maximumEnable;
    }

    public boolean getMinimumEnable() {
        return this.minimumEnable;
    }

    public void setMinimumEnable(boolean minimumEnable) {
        this.minimumEnable = minimumEnable;
    }

    public boolean getAverageEnable() {
        return this.averageEnable;
    }

    public void setAverageEnable(boolean averageEnable) {
        this.averageEnable = averageEnable;
    }

    public boolean getRangeEnable() {
        return this.rangeEnable;
    }

    public void setRangeEnable(boolean rangeEnable) {
        this.rangeEnable = rangeEnable;
    }

    public boolean getJudgeEnable() {
        return this.judgeEnable;
    }

    public void setJudgeEnable(boolean judgeEnable) {
        this.judgeEnable = judgeEnable;
    }

    public List<String> getSignList() {
        return this.signList;
    }

    public void setSignList(List<String> signList) {
        this.signList = signList;
    }


}