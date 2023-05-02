package com.nukang.app.advertisement;

import com.nukang.app.image.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final Logger log = LoggerFactory.getLogger(AdvertisementService.class);
    private final AdvertisementRepository advertisementRepository;
    static String BASE_DIR = System.getProperty("user.dir");

    ResponseEntity<List<byte[]>> getActive(){
        log.info("[ads-service] start");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Advertisement> advertisements = advertisementRepository.selectActive(formatter.format(today));

        if(advertisements.size() <= 0){
            MediaType contentType = MediaType.IMAGE_JPEG;
            Resource in = null;
            try {
                if(BASE_DIR.startsWith("/") )BASE_DIR = BASE_DIR.substring(1);
                in = new UrlResource("file:///"+BASE_DIR+"/promosiResource/promosi.jpg");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        List<byte[]> ads = advertisements.stream()
                                        .map(ad -> ImageUtils
                                        .decompressImage(ad.getImageData()))
                                        .collect(Collectors.toList());
        log.info("[ads-service] end");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(ads);
    }
}
