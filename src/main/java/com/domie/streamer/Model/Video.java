package com.domie.streamer.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
//@Data
@Table(name = "video")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String videoName;

    private String videoLength;

    private String videoOriginalName;

    private String imageUrl;

    private String videoUrl; // manifest

    private String videoType;

    private long videoSize;

    private String videoStatus; // IN_PROGRESS, DELETED, COMPLETED

    private String folder;

    private String duration;

    @Transient
    private MultipartFile video;

    public Video() {
    }


    public Video(String videoName, String videoLength, String videoOriginalName, String imageUrl, String videoUrl, String videoType, long videoSize, String videoStatus) {
        this.videoName = videoName;
        this.videoLength = videoLength;
        this.videoOriginalName = videoOriginalName;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.videoType = videoType;
        this.videoSize = videoSize;
        this.videoStatus = videoStatus;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", videoName='" + videoName + '\'' +
                ", videoLength='" + videoLength + '\'' +
                ", videoOriginalName='" + videoOriginalName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoType='" + videoType + '\'' +
                ", videoSize=" + videoSize +
                ", videoStatus='" + videoStatus + '\'' +
                ", video=" + video +
                '}';
    }
}
