package ru.emiren.support.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emiren.support.service.SupportService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class SupportRestController {
    private SupportService supportService;

    public SupportRestController(SupportService supportService) {
        this.supportService = supportService;
    }

    /**
     * Saving ticket to the Database
     * @param request contains in params username, message, email, phone
     * @return
     */
    @PostMapping("/support/message")
    public ResponseEntity<String> handleMessage(@RequestBody Map<String, String> request) {
        log.info("In handle support message method with {}", request.get("username"));
        return supportService.handleMessage(request);
    }

    /**
     * Sends to client a json with all tickets
     * @return a json with list of tickets
     */
    @GetMapping("/support/receive-messages")
    public ResponseEntity<String> receiveAllMessages() {
        return supportService.getAllTickets();
    }

    /**
     * Sends a specific ticket with id to client
     * @param request constains a ID param
     * @return a json with ticket details
     */
    @GetMapping("/support/view-ticket")
    public ResponseEntity<String> viewTicket(HttpServletRequest request) {
        return supportService.viewTicket(request);
    }

}
