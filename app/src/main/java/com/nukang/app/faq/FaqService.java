package com.nukang.app.faq;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;

    public Page<Faq> getFaq(Pageable pageable) {
        return faqRepository.findAll(pageable);
    }

    public Faq post(Faq faq) {

        return faqRepository.save(faq);
    }
}
