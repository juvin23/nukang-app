package com.nukang.app.advertisement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("api/v1/ads")
public class AdvertisementController {
    private Logger log = LoggerFactory.getLogger(AdvertisementController.class);
    static String BASE_DIR = System.getProperty("user.dir");
    @GetMapping("/get-ads")
    @ResponseBody
    public ResponseEntity getImageDynamicType() {
        MediaType contentType = MediaType.IMAGE_JPEG;
        Resource in = null;
        try {
            if(BASE_DIR.startsWith("/") )BASE_DIR = BASE_DIR.substring(1);
            in = new UrlResource("file:///"+BASE_DIR+"/promosiResource/promosi.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(in);
    }
}