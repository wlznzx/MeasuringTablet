package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TemplatePicBean {

    @Id(autoincrement = true)
    public Long id;

    public long templateResultID;

    public byte[] img;

    @Generated(hash = 1013472022)
    public TemplatePicBean(Long id, long templateResultID, byte[] img) {
        this.id = id;
        this.templateResultID = templateResultID;
        this.img = img;
    }

    @Generated(hash = 1788440318)
    public TemplatePicBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTemplateResultID() {
        return this.templateResultID;
    }

    public void setTemplateResultID(long templateResultID) {
        this.templateResultID = templateResultID;
    }

    public byte[] getImg() {
        return this.img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "TemplatePicBean{" +
                "id=" + id +
                ", templateResultID=" + templateResultID +
                '}';
    }
}
