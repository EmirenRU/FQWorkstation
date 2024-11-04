package ru.emiren.hub.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HubRestController {

    @GetMapping("get-number")
    public ResponseEntity<Integer> getNumber() {
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }
}
