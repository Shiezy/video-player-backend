package com.domie.streamer.Service;

import com.domie.streamer.Service.dto.FileUploadDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadService {
    private String mediaFolder = "/apps/media";

    public FileUploadDTO uploadFile(MultipartFile file) throws Exception {
        FileUploadDTO fileUploadDTO = new FileUploadDTO();

        if (file.getContentType() == null || !file.getContentType().contains("video")) {
            throw new Exception("Unsupported Media Type");
        }

        if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {

            String fileName = UUID.randomUUID().toString();

            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(mediaFolder, fileName)));
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();

            fileUploadDTO.setStatusCode(200);
            fileUploadDTO.setError(null);
            fileUploadDTO.setContentType(file.getContentType());
            fileUploadDTO.setFileName(fileName);
            fileUploadDTO.setOriginalFileName(file.getOriginalFilename());
            fileUploadDTO.setName(file.getName());
            fileUploadDTO.setSize(file.getSize());

            // create directory
            String dirName = UUID.randomUUID().toString();
            fileUploadDTO.setFolder(dirName);

            Path path = Paths.get(mediaFolder + "/" + dirName);
            Files.createDirectories(path);


        } else {
            fileUploadDTO.setStatusCode(400);
            fileUploadDTO.setError("Invalid File Uploaded");
        }
        return fileUploadDTO;
    }


    public String getMimeType(File file, String url) throws IOException {
        URLConnection connection = file.toURL().openConnection();
        String mimeType = connection.getContentType();

        if (Objects.equals(mimeType, "content/unknown") && url != null && url.contains("m4s")) {
            mimeType = "video/iso.segment";
        }

        if (Objects.equals(mimeType, "content/unknown") && url != null && url.contains("mp4")) {
            mimeType = "video/mp4";
        }

        return mimeType;
    }

    public byte[] getFile(File videoFile) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(videoFile));
        return StreamUtils.copyToByteArray(inputStream);
    }
}
