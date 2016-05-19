package com.hibernate.llf.dao;

import java.util.List;

/**
 * Created by lenovo on 2016/4/23.
 */
public class PageBean {
    private int count;
    private List pageList;//一页里保存的数据

    public PageBean(int count, List pageList) {
        this.count = count;
        this.pageList = pageList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List getPageList() {
        return pageList;
    }

    public void setPageList(List pageList) {
        this.pageList = pageList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "count=" + count +
                ", pageList=" + pageList +
                '}';
    }
}
