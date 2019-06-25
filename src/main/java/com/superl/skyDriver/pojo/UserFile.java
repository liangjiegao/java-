package com.superl.skyDriver.pojo;

public class UserFile {
    private Long id;
    private User user;
    private WPFile file;
    private String filename;
    private String parent;
    private Long linkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WPFile getFile() {
        return file;
    }

    public void setFile(WPFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "id=" + id +
                ", user=" + user +
                ", file=" + file +
                ", filename='" + filename + '\'' +
                ", parent='" + parent + '\'' +
                ", linkId=" + linkId +
                '}';
    }
}
