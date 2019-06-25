package com.superl.skyDriver.responseEntry;

public class PageingObject<T> {

    private T obj;
    private Long total;
    private Long nowPage;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getNowPage() {
        return nowPage;
    }

    public void setNowPage(Long nowPage) {
        this.nowPage = nowPage;
    }

    @Override
    public String toString() {
        return "PageingObject{" +
                "obj=" + obj +
                ", total=" + total +
                ", nowPage=" + nowPage +
                '}';
    }
}
