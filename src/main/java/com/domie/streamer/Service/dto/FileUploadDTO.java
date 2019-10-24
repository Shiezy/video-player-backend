package com.domie.streamer.Service.dto;

public class FileUploadDTO {

    private String contentType;

    private String fileName;

    private String error;

    // supports 200, 503, 504, 507
    private int statusCode;

    private String originalFileName;

    private String name;

    private long size;

    private String folder;

    private  StringBuilder duration;



    public FileUploadDTO() {
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public StringBuilder getDuration() {
        return duration;
    }

    public void setDuration(StringBuilder duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FileUploadDTO{" +
                "contentType='" + contentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", error='" + error + '\'' +
                ", statusCode=" + statusCode +
                ", originalFileName='" + originalFileName + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", folder='" + folder + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
