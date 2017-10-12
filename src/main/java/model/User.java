package model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.FIELD)
public class User  implements Serializable{
    private long id;
    private String userName;
    private String realName;
    private long instituteId;
    private String password;

    public User() {
    }

    public User(long id, String userName, String realName, long instituteId, String password) {
        this.id = id;
        this.userName = userName;
        this.realName = realName;
        this.instituteId = instituteId;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(long instituteId) {
        this.instituteId = instituteId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "id: " + id + " username: " + userName + " realname: " + realName + " instituteid: " + instituteId + " password: " + password;
    }
}
