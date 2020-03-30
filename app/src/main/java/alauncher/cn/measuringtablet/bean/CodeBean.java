package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

import java.util.Arrays;
import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

/**
 * 日期：2019/8/5 0025 9:27
 * 包名：alauncher.cn.measuringtablet.bean
 * 作者： wlznzx
 * 描述：
 */
@Entity
public class CodeBean {

    @Id(autoincrement = true)
    public Long codeID;

    public String name;

    public String machineTool;

    public String parts;

    public boolean isEnableStep;

    public byte[] workpiecePic;

    @Generated(hash = 379354465)
    public CodeBean(Long codeID, String name, String machineTool, String parts,
            boolean isEnableStep, byte[] workpiecePic, List<String> defaultTitles) {
        this.codeID = codeID;
        this.name = name;
        this.machineTool = machineTool;
        this.parts = parts;
        this.isEnableStep = isEnableStep;
        this.workpiecePic = workpiecePic;
        this.defaultTitles = defaultTitles;
    }

    public CodeBean(String name, String machineTool, String parts,
                    boolean isEnableStep, byte[] workpiecePic) {
        this.name = name;
        this.machineTool = machineTool;
        this.parts = parts;
        this.isEnableStep = isEnableStep;
        this.workpiecePic = workpiecePic;
    }

    @Generated(hash = 544591002)
    public CodeBean() {
    }

    public Long getCodeID() {
        return this.codeID;
    }

    public void setCodeID(Long codeID) {
        this.codeID = codeID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMachineTool() {
        return this.machineTool;
    }

    public void setMachineTool(String machineTool) {
        this.machineTool = machineTool;
    }

    public String getParts() {
        return this.parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public boolean getIsEnableStep() {
        return this.isEnableStep;
    }

    public void setIsEnableStep(boolean isEnableStep) {
        this.isEnableStep = isEnableStep;
    }

    public byte[] getWorkpiecePic() {
        return this.workpiecePic;
    }

    public void setWorkpiecePic(byte[] workpiecePic) {
        this.workpiecePic = workpiecePic;
    }

    public List<String> getDefaultTitles() {
        return this.defaultTitles;
    }

    public void setDefaultTitles(List<String> defaultTitles) {
        this.defaultTitles = defaultTitles;
    }

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> defaultTitles;

    
}
