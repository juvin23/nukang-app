package com.nukang.app.advertisement;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/ads")
public class AdvertisementController {
    private Logger log = LoggerFactory.getLogger(AdvertisementController.class);

    private final AdvertisementService advertisementService;

    @GetMapping("/get-ads")
    @ResponseBody
    public ResponseEntity getImageDynamicType() {

        ResponseEntity res = null;
        try {
            res = advertisementService.getActive();
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return res;
    }
}