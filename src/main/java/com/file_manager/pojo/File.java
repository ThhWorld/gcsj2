package com.file_manager.pojo;


public class File {

  private long id;
  private long useId;
  private String filePath;
  private java.sql.Date createTime;
  private java.sql.Date uploadTime;
  private String fileName;
  private long isDeleted;
  private String localPath;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUseId() {
    return useId;
  }

  public void setUseId(long useId) {
    this.useId = useId;
  }


  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


  public java.sql.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Date createTime) {
    this.createTime = createTime;
  }


  public java.sql.Date getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(java.sql.Date uploadTime) {
    this.uploadTime = uploadTime;
  }


  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public long getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(long isDeleted) {
    this.isDeleted = isDeleted;
  }


  public String getLocalPath() {
    return localPath;
  }

  public void setLocalPath(String localPath) {
    this.localPath = localPath;
  }

}
