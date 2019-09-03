package com.domie.streamer.Service.util;

import java.io.IOException;

public class ChangeResolution {
    public static void to540(String srcFileName, String destFileName) throws InterruptedException, IOException {
        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=540:-2,setsar=1:1 -c:v libx264 -c:a copy " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }

    public static void to360(String srcFileName, String destFileName) throws InterruptedException, IOException {
        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=360:-2,setsar=1:1 -c:v libx264 -c:a copy " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }

    public static void to240(String srcFileName, String destFileName) throws InterruptedException, IOException {
        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=240:-2,setsar=1:1 -c:v libx264 -c:a copy " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
