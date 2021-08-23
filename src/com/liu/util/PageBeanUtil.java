package com.liu.util;
//分页的 工具类
public class PageBeanUtil {
    private int page;//第几页 -- 前端传过来
    private int pageSize;//每一页的条数,也叫 limit -- 前端传过来
    private int start;//索引

    public PageBeanUtil(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    //这里的索引需要计算一下
    public int getStart() {
        return (page-1)*pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
