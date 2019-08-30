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

            // create Audio
            AudioUtil.extractAudio(srcFileName, destAudioFileName);


            // create HighQuality Video
            VideoHighQualityUtil.extractVideo5000(srcFileName, destVideoHighQualityFileName);

            // create LowQuality Video
            VideoLowQualityUtil.extractVideo2000(srcFileName, destVideoLowQualityFileName);


            /// create manifest
            CreateManifestUtil.createManifest(manifestFiles, manifestFileName);
        }
    }
}
