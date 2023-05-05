package com.nukang.app.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    @GetMapping
    ResponseEntity getFaqList(@PageableDefault Pageable pageable){
        Page<Faq> page;
        try{
            page = faqService.getFaq(pageable);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(page);
    }

    @PostMapping
    ResponseEntity postFaq(@RequestBody Faq faq){
        Faq posted;
        try{
            posted = faqService.post(faq);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(posted);
    }
}
