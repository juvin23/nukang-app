package com.nukang.app.promosi;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.MalformedURLException;

@RestController
@RequestMapping("api/v1/promosi")
public class PromosiController {
    final static String BASE_DIR = System.getProperty("user.dir");
    @GetMapping("/get-promosi")
    @ResponseBody
    public ResponseEntity getImageDynamicType() {
        MediaType contentType = MediaType.IMAGE_JPEG;
        Resource in = null;
        try {
            in = new UrlResource("file://"+BASE_DIR+"/userResources/promosi.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(in);
    }
}