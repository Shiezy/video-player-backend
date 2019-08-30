package com.domie.streamer.Service.util;

import java.io.IOException;

public class VideoHighQualityUtil {
    public static void extractVideo5000(String srcFile, String destFile) throws InterruptedException, IOException {

        // Process p = null;
        String cmd = "ffmpeg -y -i " + srcFile + " -preset slow -tune film -vsync passthrough -write_tmcd 0 -an -c:v libx264 -x264opts 'keyint=25:min-keyint=25:no-scenecut' -crf 22  -maxrate 5000k -bufsize 10000k -pix_fmt yuv420p -f mp4 " + destFile;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});

        // p = run.exec(cmd);

        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
