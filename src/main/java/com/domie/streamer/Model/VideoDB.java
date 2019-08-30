package com.domie.streamer.Model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;

public class VideoDB {

    private Long id;

    private String videoName;

    private String videoLength;

    private String videoOriginalName;

    private String videoUrl;

    private String videoType;

    private long videoSize;

    private MultipartFile video;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getVideoOriginalName() {
        return videoOriginalName;
    }

    public void setVideoOriginalName(String videoOriginalName) {
        this.videoOriginalName = videoOriginalName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "VideoDB{" +
                "id=" + id +
                ", videoName='" + videoName + '\'' +
                ", videoLength='" + videoLength + '\'' +
                ", videoOriginalName='" + videoOriginalName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoType='" + videoType + '\'' +
                ", videoSize=" + videoSize +
                ", video=" + video +
                '}';
    }
}
