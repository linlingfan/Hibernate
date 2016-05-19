package cn.hibernate.test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/5/5.
 * 用户表和权限多对多，和员工一对一
 */
public class UserAccount {
    private int id;
    private String loginNane;
    private String password;
    private Employee employee;
    private Set<Privilege>privilegeSet=new HashSet<Privilege>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginNane() {
        return loginNane;
    }

    public void setLoginNane(String loginNane) {
        this.loginNane = loginNane;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Privilege> getPrivilegeSet() {
        return privilegeSet;
    }

    public void setPrivilegeSet(Set<Privilege> privilegeSet) {
        this.privilegeSet = privilegeSet;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", loginNane='" + loginNane + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
