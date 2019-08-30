package com.domie.streamer.Resource;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Model.VideoDB;
import com.domie.streamer.Service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class VideoResource {

    @Value("${file.storage.videos}")
    private String videoDir;

    public static final Logger logger = LoggerFactory.getLogger(VideoResource.class);


    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Video addVideoPost(VideoDB videoDB, @RequestParam("file") MultipartFile file) {
        return videoService.saveVideo(videoDB, file);
    }

    @RequestMapping(value = "/file/{url}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getVideo(@PathVariable String url) {

        HttpHeaders headers = new HttpHeaders();

        String fileDir = "/apps/media/";
        try {

            File videoFile = new File(fileDir + url);

            System.out.println("Filename = " + fileDir + url);

            File file = new File(fileDir + url);
            URLConnection connection = file.toURL().openConnection();
            String mimeType = connection.getContentType();

            if (Objects.equals(mimeType, "content/unknown") && url != null && url.contains("m4s")) {
                mimeType = "video/iso.segment";
            }

            if (Objects.equals(mimeType, "content/unknown") && url != null && url.contains("mp4")) {
                mimeType = "video/mp4";
            }

            System.out.println("mimeType = " + mimeType);

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(videoFile));
            byte[] media = StreamUtils.copyToByteArray(inputStream);

            headers.setContentType(MediaType.valueOf(mimeType));

            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping("/videoList")
    public List<Video> findAll() {
        return videoService.findAll();
    }

}
