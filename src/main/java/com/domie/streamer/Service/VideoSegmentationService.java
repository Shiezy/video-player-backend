package com.domie.streamer.Service;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Service.util.AudioUtil;
import com.domie.streamer.Service.util.CreateManifestUtil;
import com.domie.streamer.Service.util.VideoHighQualityUtil;
import com.domie.streamer.Service.util.VideoLowQualityUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class VideoSegmentationService {

    private String mediaFolder = "/apps/media";

    public void segmentVideo(Video video) throws IOException, InterruptedException {

        if (video != null) {

            String videoFolder = video.getFolder();
            String srcFileName = mediaFolder + "/" + video.getVideoName();
            String destAudioFileName = mediaFolder + "/" + video.getVideoName() + "_audio.m4a";
            String destVideoHighQualityFileName = mediaFolder + "/" + video.getVideoName() + "_high.mp4";
            String destVideoLowQualityFileName = mediaFolder + "/" + video.getVideoName() + "low.mp4";
            String manifestFileName = mediaFolder + "/" + videoFolder + "/" + "Manifest.mpd";

            List<String> manifestFiles = new ArrayList<>();
            manifestFiles.add(destAudioFileName);
            manifestFiles.add(destVideoHighQualityFileName);
            manifestFiles.add(destVideoLowQualityFileName);

            CountDownLatch latch = new CountDownLatch(3);

            // create Audio
            new Thread(() -> {
                try {
                    AudioUtil.extractAudio(srcFileName, destAudioFileName);
                    latch.countDown();
                } catch (InterruptedException | IOException e) {
                    System.out.println("Encountered Exception " + e.getLocalizedMessage());
                }
            }).start();


            // create HighQuality Video
            new Thread(() -> {
                try {
                    VideoHighQualityUtil.extractVideo5000(srcFileName, destVideoHighQualityFileName);
                    latch.countDown();
                } catch (InterruptedException | IOException e) {
                    System.out.println("Encountered Exception " + e.getLocalizedMessage());
                }
            }).start();

            // create LowQuality Video
            new Thread(() -> {
                try {
                    VideoLowQualityUtil.extractVideo2000(srcFileName, destVideoLowQualityFileName);
                    latch.countDown();
                } catch (InterruptedException | IOException e) {
                    System.out.println("Encountered Exception " + e.getLocalizedMessage());
                }
            }).start();


            /// create manifest
            latch.await();
            CreateManifestUtil.createManifest(manifestFiles, manifestFileName);
        }
    }

    public void createManifest(List<String> manifestFiles, String manifestFileName, int count) throws IOException, InterruptedException {
        if (count == 3) {
            CreateManifestUtil.createManifest(manifestFiles, manifestFileName);


        }
    }
}
