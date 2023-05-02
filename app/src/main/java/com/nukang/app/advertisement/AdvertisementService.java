package com.nukang.app.advertisement;

import com.nukang.app.image.ImageModel;
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

    ResponseEntity getActive(){
        log.info("[ads-service] start");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Advertisement> advertisements = advertisementRepository.selectActive(formatter.format(today));


        log.info("[ads-service] end");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(advertisements);
    }

    ResponseEntity getAdBanner(long uid){
        Advertisement ad = advertisementRepository.findById(uid).orElse(null);
        if(ad == null){
            try {
                if(BASE_DIR.startsWith("/"))BASE_DIR = BASE_DIR.substring(1);
                Resource resource = new UrlResource("file:///"+BASE_DIR+"/promosiResource/promosi.png");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        byte[] image = ImageUtils.decompressImage(ad.getImageData());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
