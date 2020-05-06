package alauncher.cn.measuringtablet.bean;

import java.util.Arrays;
import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TemplateBean {

    @Id(autoincrement = true)
    public Long templateID;

    public String name;

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleList;

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleTypeList;

    // 签名;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> signList;

    // AQL
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> AQLList;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> AQLTypeList;

    // RoHS
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> RoHSList;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> RoHSTypeList;

    public String headerLeft;

    public String headerMid;

    public String headerRight;

    public String footerLeft;

    public String footerMid;

    public String footerRight;

    public String title;

    public int dataNum;

    public boolean maximumEnable, minimumEnable, averageEnable, rangeEnable, judgeEnable;

    public boolean aqlEnable, roshEnable;

    public byte[] logoPic;

    public int confirmationFrequency;

    public long lastConfirmTimeStamp;

    @Generated(hash = 58443959)
    public TemplateBean(Long templateID, String name, List<String> titleList,
                        List<String> titleTypeList, List<String> signList, List<String> AQLList,
                        List<String> AQLTypeList, List<String> RoHSList, List<String> RoHSTypeList,
                        String headerLeft, String headerMid, String headerRight, String footerLeft,
                        String footerMid, String footerRight, String title, int dataNum,
                        boolean maximumEnable, boolean minimumEnable, boolean averageEnable,
                        boolean rangeEnable, boolean judgeEnable, boolean aqlEnable, boolean roshEnable,
                        byte[] logoPic, int confirmationFrequency, long lastConfirmTimeStamp) {
        this.templateID = templateID;
        this.name = name;
        this.titleList = titleList;
        this.titleTypeList = titleTypeList;
        this.signList = signList;
        this.AQLList = AQLList;
        this.AQLTypeList = AQLTypeList;
        this.RoHSList = RoHSList;
        this.RoHSTypeList = RoHSTypeList;
        this.headerLeft = headerLeft;
        this.headerMid = headerMid;
        this.headerRight = headerRight;
        this.footerLeft = footerLeft;
        this.footerMid = footerMid;
        this.footerRight = footerRight;
        this.title = title;
        this.dataNum = dataNum;
        this.maximumEnable = maximumEnable;
        this.minimumEnable = minimumEnable;
        this.averageEnable = averageEnable;
        this.rangeEnable = rangeEnable;
        this.judgeEnable = judgeEnable;
        this.aqlEnable = aqlEnable;
        this.roshEnable = roshEnable;
        this.logoPic = logoPic;
        this.confirmationFrequency = confirmationFrequency;
        this.lastConfirmTimeStamp = lastConfirmTimeStamp;
    }

    @Generated(hash = 741639705)
    public TemplateBean() {
    }

    public Long getTemplateID() {
        return this.templateID;
    }

    public void setTemplateID(Long templateID) {
        this.templateID = templateID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitleList() {
        return this.titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getTitleTypeList() {
        return this.titleTypeList;
    }

    public void setTitleTypeList(List<String> titleTypeList) {
        this.titleTypeList = titleTypeList;
    }

    public List<String> getSignList() {
        return this.signList;
    }

    public void setSignList(List<String> signList) {
        this.signList = signList;
    }

    public List<String> getAQLList() {
        return this.AQLList;
    }

    public void setAQLList(List<String> AQLList) {
        this.AQLList = AQLList;
    }

    public List<String> getAQLTypeList() {
        return this.AQLTypeList;
    }

    public void setAQLTypeList(List<String> AQLTypeList) {
        this.AQLTypeList = AQLTypeList;
    }

    public List<String> getRoHSList() {
        return this.RoHSList;
    }

    public void setRoHSList(List<String> RoHSList) {
        this.RoHSList = RoHSList;
    }

    public List<String> getRoHSTypeList() {
        return this.RoHSTypeList;
    }

    public void setRoHSTypeList(List<String> RoHSTypeList) {
        this.RoHSTypeList = RoHSTypeList;
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

    public int getDataNum() {
        return this.dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
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

    public boolean getAqlEnable() {
        return this.aqlEnable;
    }

    public void setAqlEnable(boolean aqlEnable) {
        this.aqlEnable = aqlEnable;
    }

    public boolean getRoshEnable() {
        return this.roshEnable;
    }

    public void setRoshEnable(boolean roshEnable) {
        this.roshEnable = roshEnable;
    }

    public byte[] getLogoPic() {
        return this.logoPic;
    }

    public void setLogoPic(byte[] logoPic) {
        this.logoPic = logoPic;
    }

    public int getConfirmationFrequency() {
        return this.confirmationFrequency;
    }

    public void setConfirmationFrequency(int confirmationFrequency) {
        this.confirmationFrequency = confirmationFrequency;
    }

    public long getLastConfirmTimeStamp() {
        return this.lastConfirmTimeStamp;
    }

    public void setLastConfirmTimeStamp(long lastConfirmTimeStamp) {
        this.lastConfirmTimeStamp = lastConfirmTimeStamp;
    }

    @Override
    public String toString() {
        return "TemplateBean{" +
                "templateID=" + templateID +
                ", name='" + name + '\'' +
                ", titleList=" + titleList +
                ", titleTypeList=" + titleTypeList +
                ", signList=" + signList +
                ", AQLList=" + AQLList +
                ", AQLTypeList=" + AQLTypeList +
                ", RoHSList=" + RoHSList +
                ", RoHSTypeList=" + RoHSTypeList +
                ", headerLeft='" + headerLeft + '\'' +
                ", headerMid='" + headerMid + '\'' +
                ", headerRight='" + headerRight + '\'' +
                ", footerLeft='" + footerLeft + '\'' +
                ", footerMid='" + footerMid + '\'' +
                ", footerRight='" + footerRight + '\'' +
                ", title='" + title + '\'' +
                ", dataNum=" + dataNum +
                ", maximumEnable=" + maximumEnable +
                ", minimumEnable=" + minimumEnable +
                ", averageEnable=" + averageEnable +
                ", rangeEnable=" + rangeEnable +
                ", judgeEnable=" + judgeEnable +
                ", aqlEnable=" + aqlEnable +
                ", roshEnable=" + roshEnable +
                ", confirmationFrequency=" + confirmationFrequency +
                ", lastConfirmTimeStamp=" + lastConfirmTimeStamp +
                '}';
    }
}