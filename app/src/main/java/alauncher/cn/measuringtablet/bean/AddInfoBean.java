package alauncher.cn.measuringtablet.bean;

/**
 * 日期：2019/6/19 0019 15:14
 * 包名：alauncher.cn.measuringtablet.bean
 * 作者： wlznzx
 * 描述：
 */
public class AddInfoBean {

    private String workid;

    private String eventid;

    private boolean isAutoShow;

    private String event;

    private String work;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public boolean isAutoShow() {
        return isAutoShow;
    }

    public void setAutoShow(boolean autoShow) {
        isAutoShow = autoShow;
    }
}
