package com.nukang.app.image;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;

@Service
public class ImageService {
    final static String BASE_DIR = System.getProperty("user.dir");

    public String upload(String directory, MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();
        String filePath = BASE_DIR+"/"+directory+"/"+file.getOriginalFilename()+".png";
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(bytes);
        stream.close();
        return file.getOriginalFilename();
    }
    public ResponseEntity getImage(String uid) {
        MediaType contentType =  MediaType.IMAGE_JPEG;
        Resource resource = null;
        try {
            System.out.println(BASE_DIR+"/userResources/"+ uid +".png");
            resource = new UrlResource("file://"+BASE_DIR+"/userResources/"+ uid +".png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(resource != null && !resource.exists() && !resource.isReadable()){
            try {
                resource = new UrlResource("file://"+BASE_DIR+"/app/userResources/placeholder.png");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }
}
