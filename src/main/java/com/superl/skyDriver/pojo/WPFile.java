package com.superl.skyDriver.pojo;

import java.util.Date;

public class WPFile {
    private Long id;
    private String filePath;
    private String type;
    private Long size;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        return "WPFile{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", createDate=" + createDate +
                '}';
    }
}
