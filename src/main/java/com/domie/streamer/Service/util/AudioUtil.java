package com.domie.streamer.Service.util;

import java.io.IOException;

public class AudioUtil {
    public static void extractAudio(String srcFile, String destFile) throws InterruptedException, IOException {
        Runtime run = Runtime.getRuntime();

        Process p = null;
        String cmd = "ffmpeg -y -i " + srcFile + " -c:a aac -b:a 192k -vn " + destFile;
        p = run.exec(cmd);

        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
