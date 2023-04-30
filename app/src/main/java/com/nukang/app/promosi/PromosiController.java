package com.nukang.app.promosi;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("api/v1/promosi")
public class PromosiController {
    @GetMapping("/get-promosi")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType() {
        MediaType contentType = MediaType.IMAGE_JPEG;
        InputStream in = PromosiController.class.getClassLoader().getResourceAsStream("promosiResource/promosi.jpg");
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }
}