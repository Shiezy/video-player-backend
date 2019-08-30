package com.domie.streamer.Service;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Model.VideoDB;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    Video saveVideo(VideoDB videoDB, MultipartFile multipartFile);

    List<Video> findAll();

    Video findOne(Long id);

    String storeVideo(MultipartFile video);


//    void removeOne(Long id);

}
