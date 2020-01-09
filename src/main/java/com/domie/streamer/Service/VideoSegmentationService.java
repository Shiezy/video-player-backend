package com.domie.streamer.Service;

import com.domie.streamer.Model.Video;
import com.domie.streamer.Service.util.*;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

@Service
public class VideoSegmentationService {

    private String mediaFolder = "/apps/media";


    public void encodeAndSegment(Video video) throws IOException, InterruptedException {
        String srcFileName = mediaFolder + "/" + video.getVideoName();
        
        String destMP4FileName = mediaFolder + "/" + video.getVideoName() + ".mp4";
        String destMP4_540FileName = mediaFolder + "/" + video.getVideoName() + "-540.mp4";
        String destMP4_360FileName = mediaFolder + "/" + video.getVideoName() + "-360.mp4";
        String destMP4_240FileName = mediaFolder + "/" + video.getVideoName() + "-240.mp4";
        String destAudioFileName = mediaFolder + "/" + video.getVideoName() + "_audio.m4a";

        String manifestFileName = mediaFolder + "/" + video.getFolder() + "/" + "Manifest.mpd";
		String destThumbnailFileName = mediaFolder + "/" + video.getFolder() + "/" + "poster.png";
        List<String> manifestFiles = new ArrayList<>();

        // first encode to mp4 - without audio
        // first encode to mp4 - with audio
        MP4Encoder.encode(srcFileName, destMP4FileName);

        //create a thumbnail from the video
		CreateThumbnail.createThumbNail(srcFileName, destThumbnailFileName);

        CountDownLatch latch = new CountDownLatch(2);

        System.out.println("starting the video segmentation process at :" + LocalTime.now());

        // create Audio
//        new Thread(() -> {
//            try {
//                System.out.println("Started Extracting Audio");
//                AudioUtil.extractAudio(srcFileName, destAudioFileName);
//                manifestFiles.add(destAudioFileName);
//                System.out.println("Finished Extracting Audio");
//                latch.countDown();
//            } catch (InterruptedException | IOException e) {
//                System.out.println("Encountered Exception " + e.getLocalizedMessage());
//            }
//        }).start();

        // create res 540

//        new Thread(() -> {
//            try {
//                System.out.println("Started creating Res 540");
//                ChangeResolution.to540(destMP4FileName, destMP4_540FileName);
//                manifestFiles.add(destMP4_540FileName);
//                latch.countDown();
//                System.out.println("Finished creating Res 540");
//            } catch (InterruptedException | IOException e) {
//                System.out.println("Encountered Exception " + e.getLocalizedMessage());
//            }
//        }).start();

        // create res 360
        new Thread(() -> {
            try {
                System.out.println("Started creating Res 360");
                ChangeResolution.to360(destMP4FileName, destMP4_360FileName);
                manifestFiles.add(destMP4_360FileName);
                latch.countDown();
                System.out.println("Finished creating Res 360");
            } catch (InterruptedException | IOException e) {
                System.out.println("Encountered Exception " + e.getLocalizedMessage());
            }
        }).start();

        // create res 240
        new Thread(() -> {
            try {
                System.out.println("Started creating Res 240");
                ChangeResolution.to240(destMP4FileName, destMP4_240FileName);
                manifestFiles.add(destMP4_240FileName);
                latch.countDown();
                System.out.println("Finished creating Res 240");
            } catch (InterruptedException | IOException e) {
                System.out.println("Encountered Exception " + e.getLocalizedMessage());
            }
        }).start();


        /// create manifest
        latch.await();
        System.out.println("Started creating manifest");
        CreateManifestUtil.createManifest(manifestFiles, manifestFileName);
        System.out.println("Finished creating manifest");

        System.out.println("Finished the video segmentation process at :" + LocalTime.now());

    }
}
