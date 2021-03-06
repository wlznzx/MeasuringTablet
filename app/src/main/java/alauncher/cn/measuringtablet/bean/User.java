package alauncher.cn.measuringtablet.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 日期：2019/4/25 0025 9:27
 * 包名：alauncher.cn.measuringtablet.bean
 * 作者： wlznzx
 * 描述：
 */
@Entity
public class User {
    @Id
    public String accout;
    @NotNull
    public String name;
    @NotNull
    public String password;

    public int status;

    public String email;

    public String id;

    public int limit;

    @Generated(hash = 668259641)
    public User(String accout, @NotNull String name, @NotNull String password,
            int status, String email, String id, int limit) {
        this.accout = accout;
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
        this.id = id;
        this.limit = limit;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getAccout() {
        return this.accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "accout='" + accout + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
