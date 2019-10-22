package com.domie.streamer.Resource;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Model.VideoDB;
import com.domie.streamer.Service.DirectVideoService;
import com.domie.streamer.Service.UploadService;
import com.domie.streamer.Service.VideoSegmentationService;
import com.domie.streamer.Service.VideoService;
import com.domie.streamer.Service.dto.FileUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoResource {


    @Value("${file.storage.videos}")
    private String videoDir;

    public static final Logger logger = LoggerFactory.getLogger(VideoResource.class);


    private final VideoService videoService;

    private final UploadService uploadService;

    private final DirectVideoService directVideoService;

    private final VideoSegmentationService videoSegmentationService;

    public VideoResource(VideoService videoService, UploadService uploadService, DirectVideoService directVideoService, VideoSegmentationService videoSegmentationService) {
        this.videoService = videoService;
        this.uploadService = uploadService;
        this.directVideoService = directVideoService;
        this.videoSegmentationService = videoSegmentationService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Video addVideoPost(VideoDB videoDB, @RequestParam("file") MultipartFile file) {
        return videoService.saveVideo(videoDB, file);
    }

    @RequestMapping(value = "/file/upload/", method = RequestMethod.POST)
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        FileUploadDTO fileUploadDTO = null;

        try {
            fileUploadDTO = uploadService.uploadFile(file);

            String videoUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/").path(fileUploadDTO.getFolder()).path("/Manifest.mpd").toUriString();
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/").path(fileUploadDTO.getFolder()).path("/poster.png").toUriString();

            Video video = new Video();
            video.setVideoName(fileUploadDTO.getFileName());
            video.setVideoOriginalName(fileUploadDTO.getOriginalFileName());
            video.setVideoSize(fileUploadDTO.getSize());
            video.setImageUrl(imageUrl);
            video.setVideoUrl(videoUrl);
            video.setFolder(fileUploadDTO.getFolder());
            video.setVideoStatus("IN_PROGRESS");

            // save video
            video = directVideoService.save(video);


            Boolean segmentationStatus;
            // segment Video
            Video finalVideo = video;
            new Thread(() -> {
                try {
                    videoSegmentationService.encodeAndSegment(finalVideo);
                    finalVideo.setVideoStatus("COMPLETED");
                } catch (IOException | InterruptedException e) {
                    finalVideo.setVideoStatus("FAILED");
                }
                // then save the video
                directVideoService.save(finalVideo);
            }).start();


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Encountered Exception " + e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "/file/{folder}/{url}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getVideo(@PathVariable String folder, @PathVariable String url) {

        HttpHeaders headers = new HttpHeaders();

        String fileDir = "/apps/media";

        String filePath = fileDir + "/" + url;

        if (folder != null && !folder.isEmpty()) {
            filePath = fileDir + "/" + folder + "/" + url;
        }

        try {

            System.out.println("filePath = " + filePath);
            File videoFile = new File(filePath);

            String mimeType = uploadService.getMimeType(videoFile, url);

            System.out.println("mimeType = " + mimeType);

            byte[] media = uploadService.getFile(videoFile);

            headers.setContentType(MediaType.valueOf(mimeType));

            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping("/videoList")
    public List<Video> findAll() {
        return videoService.findAll();
    }

    @RequestMapping("/videos")
    public List<Video> getVideos() {
        return directVideoService.findAll();
    }

    @RequestMapping("/video/{id}")
    public Video getVideo(@PathVariable("id") Long id) {

        return directVideoService.findOne(id);

    }

}
