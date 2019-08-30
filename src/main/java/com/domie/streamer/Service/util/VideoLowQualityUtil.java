package com.domie.streamer.Service.util;

import java.io.IOException;

public class VideoLowQualityUtil {
    public static void extractVideo2000(String srcFile, String destFile) throws InterruptedException, IOException {

        String cmd = "ffmpeg -y -i " + srcFile + " -preset slow -tune film -vsync passthrough -write_tmcd 0 -an -c:v libx264 -x264opts 'keyint=25:min-keyint=25:no-scenecut' -crf 23  -maxrate 2000k -bufsize 4000k -pix_fmt yuv420p -f mp4 " + destFile;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});

        // p = run.exec(cmd);

        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
