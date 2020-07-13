package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

@Entity
public class TemplateResultBean {

    @Id(autoincrement = true)
    public Long id;

    public String factoryCode;

    public String deviceCode;

    public int codeID;

    public byte[] logoPic;

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

    // Marks
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> markList;

    public String headerLeft;

    public String headerMid;

    public String headerRight;

    public String footerLeft;

    public String footerMid;

    public String footerRight;

    public String title;

    public int dataNum;

    public boolean maximumEnable, minimumEnable, averageEnable, rangeEnable, judgeEnable;

    public String allJudge;

    public long timeStamp;

    public byte[] img;

    public String remarks;

    public String user;

    public boolean isUpload;

    // 标题;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> titleResultList;

    // AQL
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> AQLResultList;

    // RoHS
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> RoHSResultList;


    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> valueIndexs;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> upperToleranceValues;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> lowerToleranceValues;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> nominalValues;

    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> valueTypes;

    @Generated(hash = 1844604968)
    public TemplateResultBean(Long id, String factoryCode, String deviceCode, int codeID,
            byte[] logoPic, List<String> titleList, List<String> signList,
            List<String> AQLList, List<String> RoHSList, List<String> markList,
            String headerLeft, String headerMid, String headerRight, String footerLeft,
            String footerMid, String footerRight, String title, int dataNum,
            boolean maximumEnable, boolean minimumEnable, boolean averageEnable,
            boolean rangeEnable, boolean judgeEnable, String allJudge, long timeStamp,
            byte[] img, String remarks, String user, boolean isUpload,
            List<String> titleResultList, List<String> AQLResultList,
            List<String> RoHSResultList, List<String> valueIndexs,
            List<String> upperToleranceValues, List<String> lowerToleranceValues,
            List<String> nominalValues, List<String> valueTypes) {
        this.id = id;
        this.factoryCode = factoryCode;
        this.deviceCode = deviceCode;
        this.codeID = codeID;
        this.logoPic = logoPic;
        this.titleList = titleList;
        this.signList = signList;
        this.AQLList = AQLList;
        this.RoHSList = RoHSList;
        this.markList = markList;
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
        this.allJudge = allJudge;
        this.timeStamp = timeStamp;
        this.img = img;
        this.remarks = remarks;
        this.user = user;
        this.isUpload = isUpload;
        this.titleResultList = titleResultList;
        this.AQLResultList = AQLResultList;
        this.RoHSResultList = RoHSResultList;
        this.valueIndexs = valueIndexs;
        this.upperToleranceValues = upperToleranceValues;
        this.lowerToleranceValues = lowerToleranceValues;
        this.nominalValues = nominalValues;
        this.valueTypes = valueTypes;
    }

    @Generated(hash = 1091879955)
    public TemplateResultBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactoryCode() {
        return this.factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getDeviceCode() {
        return this.deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getCodeID() {
        return this.codeID;
    }

    public void setCodeID(int codeID) {
        this.codeID = codeID;
    }

    public byte[] getLogoPic() {
        return this.logoPic;
    }

    public void setLogoPic(byte[] logoPic) {
        this.logoPic = logoPic;
    }

    public List<String> getTitleList() {
        return this.titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
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

    public List<String> getRoHSList() {
        return this.RoHSList;
    }

    public void setRoHSList(List<String> RoHSList) {
        this.RoHSList = RoHSList;
    }

    public List<String> getMarkList() {
        return this.markList;
    }

    public void setMarkList(List<String> markList) {
        this.markList = markList;
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

    public String getAllJudge() {
        return this.allJudge;
    }

    public void setAllJudge(String allJudge) {
        this.allJudge = allJudge;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public byte[] getImg() {
        return this.img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public List<String> getTitleResultList() {
        return this.titleResultList;
    }

    public void setTitleResultList(List<String> titleResultList) {
        this.titleResultList = titleResultList;
    }

    public List<String> getAQLResultList() {
        return this.AQLResultList;
    }

    public void setAQLResultList(List<String> AQLResultList) {
        this.AQLResultList = AQLResultList;
    }

    public List<String> getRoHSResultList() {
        return this.RoHSResultList;
    }

    public void setRoHSResultList(List<String> RoHSResultList) {
        this.RoHSResultList = RoHSResultList;
    }

    public List<String> getValueIndexs() {
        return this.valueIndexs;
    }

    public void setValueIndexs(List<String> valueIndexs) {
        this.valueIndexs = valueIndexs;
    }

    public List<String> getUpperToleranceValues() {
        return this.upperToleranceValues;
    }

    public void setUpperToleranceValues(List<String> upperToleranceValues) {
        this.upperToleranceValues = upperToleranceValues;
    }

    public List<String> getLowerToleranceValues() {
        return this.lowerToleranceValues;
    }

    public void setLowerToleranceValues(List<String> lowerToleranceValues) {
        this.lowerToleranceValues = lowerToleranceValues;
    }

    public List<String> getNominalValues() {
        return this.nominalValues;
    }

    public void setNominalValues(List<String> nominalValues) {
        this.nominalValues = nominalValues;
    }

    public List<String> getValueTypes() {
        return this.valueTypes;
    }

    public void setValueTypes(List<String> valueTypes) {
        this.valueTypes = valueTypes;
    }


}