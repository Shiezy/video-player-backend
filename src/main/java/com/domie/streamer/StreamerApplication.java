package com.domie.streamer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;


@SpringBootApplication
public class StreamerApplication {



    public static void main(String[] args) throws IOException {

        SpringApplication.run(StreamerApplication.class, args);



        splitFile(new File("src/main/resources/static/video/frag_boasty.mp4"));

//        try {
//            File file = new File("src/main/resources/static/video/frag_blessings.mp4");//File read from Source folder to Split.
//            if (file.exists()) {
//
//                String videoFileName = file.getName().substring(0, file.getName().lastIndexOf(".")); // Name of the videoFile without extension
//                File splitFile = new File("src/main/resources/static/video/Videos_Split/"+ videoFileName);//Destination folder to save.
//                if (!splitFile.exists()) {
//                    splitFile.mkdirs();
//                    System.out.println("Directory Created -> "+ splitFile.getAbsolutePath());
//                }
//
//                int i = 01;// Files count starts from 1
//                InputStream inputStream = new BufferedInputStream( new FileInputStream(file));
//                String videoFile = splitFile.getAbsolutePath() +"/"+ String.format("%02d", i) +"_"+ file.getName();// Location to save the files which are Split from the original file.
//                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(videoFile));
//                System.out.println("File Created Location: "+ videoFile);
//                int totalPartsToSplit = 20;// Total files to split.
//                int splitSize = inputStream.available() / totalPartsToSplit;
//                int streamSize = 0;
//                int read = 0;
//                while ((read = inputStream.read()) != -1) {
//
//                    if (splitSize == streamSize) {
//                        if (i != totalPartsToSplit) {
//                            i++;
//                            String fileCount = String.format("%02d", i); // output will be 1 is 01, 2 is 02
//                            videoFile = splitFile.getAbsolutePath() +"/"+ fileCount +"_"+ file.getName();
//                            outputStream = new FileOutputStream(videoFile);
//                            System.out.println("File Created Location: "+ videoFile);
//                            streamSize = 0;
//                        }
//                    }
//                    outputStream.write(read);
//                    streamSize++;
//                }
//
//                inputStream.close();
//                outputStream.close();
//                System.out.println("Total files Split ->"+ totalPartsToSplit);
//            } else {
//                System.err.println(file.getAbsolutePath() +" File Not Found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static void splitFile(File f) throws IOException {
        System.out.println("splitting the file.....");
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];

        String fileName = f.getName();

        //try-with-resources to ensure closing stream
        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                String filePartName = String.format("%s.%03d", fileName, partCounter++);
                File newFile = new File(f.getParent(), filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        }
    }

}
