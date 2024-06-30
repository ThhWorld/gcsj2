package com.file_manager.pojo;

import org.springframework.web.multipart.MultipartFile;

public class UpFile {
    private MultipartFile file;
    private int useId;
    private String filePath;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
