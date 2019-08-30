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

import java.io.*;
import java.util.List;

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

    @RequestMapping(value = "/video/{video}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getVideo(@PathVariable String video) {

        HttpHeaders headers = new HttpHeaders();

        try {

            File videoFile = new File(videoDir + video);

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(videoFile));
            byte[] media = StreamUtils.copyToByteArray(inputStream);

            headers.setContentType(MediaType.valueOf("video/mp4"));

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
