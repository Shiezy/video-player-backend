package com.domie.streamer.Service;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Repositories.VideoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectVideoService {
    private final VideoRepo videoRepo;

    public DirectVideoService(VideoRepo videoRepo) {
        this.videoRepo = videoRepo;
    }

    public Video save(Video video) {
        return videoRepo.save(video);
    }

    public List<Video> findAll() {
        List<Video> videoList = videoRepo.findAll();

        videoList.removeIf(video -> !"COMPLETED".equals(video.getVideoStatus()));
        
        return videoList;
    }

    public Video findOne(Long id) {
        return videoRepo.findById(id).orElse(null);
    }
}
