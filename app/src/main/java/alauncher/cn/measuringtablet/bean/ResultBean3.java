package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

import alauncher.cn.measuringtablet.utils.StringConverter;

@Entity
public class ResultBean3 {

    @Id(autoincrement = true)
    public Long id;

    public long codeID;

    public String handlerAccout;

    public long timeStamp;

    public String workid;

    public String workid_extra;

    public String eventid;

    public String event;

    public String result;

    @Transient
    public boolean isSelect;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> mValues;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> mPicPaths;

    @Generated(hash = 255719245)
    public ResultBean3(Long id, long codeID, String handlerAccout, long timeStamp,
                       String workid, String workid_extra, String eventid, String event,
                       String result, List<String> mValues, List<String> mPicPaths) {
        this.id = id;
        this.codeID = codeID;
        this.handlerAccout = handlerAccout;
        this.timeStamp = timeStamp;
        this.workid = workid;
        this.workid_extra = workid_extra;
        this.eventid = eventid;
        this.event = event;
        this.result = result;
        this.mValues = mValues;
        this.mPicPaths = mPicPaths;
    }

    @Generated(hash = 1867049585)
    public ResultBean3() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCodeID() {
        return this.codeID;
    }

    public void setCodeID(long codeID) {
        this.codeID = codeID;
    }

    public String getHandlerAccout() {
        return this.handlerAccout;
    }

    public void setHandlerAccout(String handlerAccout) {
        this.handlerAccout = handlerAccout;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getWorkid() {
        return this.workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getWorkid_extra() {
        return this.workid_extra;
    }

    public void setWorkid_extra(String workid_extra) {
        this.workid_extra = workid_extra;
    }

    public String getEventid() {
        return this.eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getMValues() {
        return this.mValues;
    }

    public void setMValues(List<String> mValues) {
        this.mValues = mValues;
    }

    public List<String> getMPicPaths() {
        return this.mPicPaths;
    }

    public void setMPicPaths(List<String> mPicPaths) {
        this.mPicPaths = mPicPaths;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "ResultBean3{" +
                "id=" + id +
                ", codeID=" + codeID +
                ", handlerAccout='" + handlerAccout + '\'' +
                ", timeStamp=" + timeStamp +
                ", workid='" + workid + '\'' +
                ", workid_extra='" + workid_extra + '\'' +
                ", eventid='" + eventid + '\'' +
                ", event='" + event + '\'' +
                ", result='" + result + '\'' +
                ", isSelect=" + isSelect +
                ", mValues=" + mValues +
                ", mPicPaths=" + mPicPaths +
                '}';
    }
}
