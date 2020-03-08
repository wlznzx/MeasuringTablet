package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RoleBean {

    @Id(autoincrement = true)
    public Long id;

    public String roleName;

    @Generated(hash = 1165631179)
    public RoleBean(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Generated(hash = 1583495277)
    public RoleBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
