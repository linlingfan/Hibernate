package cn.hibernate.test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/5/5.
 */
public class Privilege {
    private int id;
    private String action;//权限
    private Set<UserAccount> userAccountSet = new HashSet<UserAccount>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Set<UserAccount> getUserAccountSet() {
        return userAccountSet;
    }

    public void setUserAccountSet(Set<UserAccount> userAccountSet) {
        this.userAccountSet = userAccountSet;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "action='" + action + '\'' +
                ", id=" + id +
                '}';
    }
}
