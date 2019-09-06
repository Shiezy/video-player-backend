package com.domie.streamer.Service.util;

import java.io.IOException;

public class MP4Encoder {
    public static void encode(String srcFileName, String destFileName) throws IOException, InterruptedException {
        // ffmpeg -i LostInTranslation.mkv -codec copy LostInTranslation.mp4
        String cmd = "ffmpeg -i " + srcFileName + " -codec copy -movflags faststart -an " + destFileName;

//        String cmd = "ffmpeg -an -i " + srcFileName + " -vcodec libx264 -pix_fmt yuv420p -profile:v baseline -level 3 " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
