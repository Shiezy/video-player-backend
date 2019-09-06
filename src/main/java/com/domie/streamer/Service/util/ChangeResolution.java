package com.domie.streamer.Service.util;

import java.io.IOException;

public class ChangeResolution {
    public static void to540(String srcFileName, String destFileName) throws InterruptedException, IOException {
//        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=540:-2,setsar=16:9 -c:v libx264 -c:a copy " + destFileName;
        String cmd = "ffmpeg -y -i " + srcFileName + " -c:a aac -ac 2 -ab 128k -c:v libx264 -x264opts 'keyint=24:min-keyint=24:no-scenecut' -b:v 800k -maxrate 800k -bufsize 500k -vf scale=540:-2 " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }

    public static void to360(String srcFileName, String destFileName) throws InterruptedException, IOException {
//        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=360:-2,setsar=1:1 -c:v libx264 -c:a copy " + destFileName;
        String cmd = "ffmpeg -y -i " + srcFileName + " -c:a aac -ac 2 -ab 128k -c:v libx264 -x264opts 'keyint=24:min-keyint=24:no-scenecut' -b:v 400k -maxrate 400k -bufsize 400k -vf scale=360:-2 " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }

    public static void to240(String srcFileName, String destFileName) throws InterruptedException, IOException {
//        String cmd = "ffmpeg -y -i " + srcFileName + " -vf scale=240:-2,setsar=1:1 -c:v libx264 -c:a copy " + destFileName;
        String cmd = "ffmpeg -y -i " + srcFileName + " -c:a aac -ac 2 -ab 128k -c:v libx264 -x264opts 'keyint=24:min-keyint=24:no-scenecut' -b:v 300k -maxrate 200k -bufsize 400k -vf scale=240:-2 " + destFileName;

        System.out.println("CMD: " + cmd);

        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});


        p.getErrorStream();
        p.waitFor();

        p.destroy();
    }
}
