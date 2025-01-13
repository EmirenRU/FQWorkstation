package ru.emiren.support.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.emiren.support.service.SupportService;

@RestController
@RequestMapping("/api/support")
public class SupportRestController {
    private SupportService supportService;

    public SupportRestController(SupportService supportService) {
        this.supportService = supportService;
    }

}
