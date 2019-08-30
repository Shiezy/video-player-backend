package com.domie.streamer.Service.util;

import java.io.IOException;
import java.util.List;

public class CreateManifestUtil {
    public static void createManifest(List<String> files, String manifestFile) throws InterruptedException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        if (files != null) {
            for (String file : files) {
                stringBuilder.append(file).append(" ");
            }
        }

        String combinedFiles = stringBuilder.toString();

        // MP4Box -dash 4000 -frag 4000 -rap -bs-switching no -profile dashavc264:live -url-template girl_5000.mp4 girl_2000.mp4 girl_audio.m4a -out output/Manifest.mpd
        String cmd = "MP4Box -dash 4000 -frag 4000 -rap -bs-switching no -profile dashavc264:live -url-template  " + combinedFiles + " -out " + manifestFile;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
        

        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
