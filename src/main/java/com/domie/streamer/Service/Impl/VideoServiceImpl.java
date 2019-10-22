package com.domie.streamer.Service.Impl;

import com.domie.streamer.Exceptions.FileStorageException;
import com.domie.streamer.Model.Video;
import com.domie.streamer.Model.VideoDB;
import com.domie.streamer.Repositories.VideoRepo;
import com.domie.streamer.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepo videoRepo;

    @Value("${file.storage.videos}")
    private String fileStoragePath;

    @Override
    @Transactional
    public Video saveVideo(VideoDB videoDB, @RequestParam("file") MultipartFile multipartFile) {

        String videoUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/video/").path(multipartFile.getOriginalFilename()).toUriString();
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/video/").path(multipartFile.getOriginalFilename()).toUriString();
        Video vid = new Video(videoDB.getVideoName(), videoDB.getVideoLength(), multipartFile.getOriginalFilename(), imageUrl, videoUrl, multipartFile.getContentType(), multipartFile.getSize(), "NEW");

        Video video = videoRepo.save(vid);

        if (video.getId() > 0) {
            storeVideo(multipartFile);
            return video;
        } else
            return new Video("N/A", "N/A", "N/A", "N/A", "N/A", "N/A", 0, "");
    }

    @Override
    public List<Video> findAll() {

        return videoRepo.findAll();
    }

    @Override
    public Video findOne(Long id) {
        return videoRepo.getOne(id);
    }

    @Override
    public String storeVideo(MultipartFile video) {

//       Storage location
//       Normalize file name
        String videoOriginalName = StringUtils.cleanPath(video.getOriginalFilename());

        try {
//           check if the file name contain invalid chars
            if (videoOriginalName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + videoOriginalName);
            }

//           Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(fileStoragePath + videoOriginalName);
            Files.copy(video.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return videoOriginalName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + videoOriginalName + ". Please try again!", e);
        }


    }


}
