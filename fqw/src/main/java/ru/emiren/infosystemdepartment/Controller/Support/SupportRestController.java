package ru.emiren.infosystemdepartment.Controller.Support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class SupportRestController {
    private final DataService dataService;

    public SupportRestController(DataService dataService) {
        this.dataService = dataService;
    }


    /**
     * Saving ticket to the Database
     * @param request contains in params username, message, email, phone
     * @return
     */
    @PostMapping("/support/message")
    public ResponseEntity<String> handleMessage(@RequestBody Map<String, String> request) {
        log.info("In handle support message method with {}", request.get("username"));
        return dataService.handleMessage(request);
    }

    /**
     * Sends to client a json with all tickets
     * @return a json with list of tickets
     */
    @GetMapping("/support/receive-messages")
    public ResponseEntity<String> receiveAllMessages() {
        return dataService.getAllTickets();
    }

    /**
     * Sends a specific ticket with id to client
     * @param request constains a ID param
     * @return a json with ticket details
     */
    @GetMapping("/support/view-ticket")
    public ResponseEntity<String> viewTicket(HttpServletRequest request) {
        return dataService.viewTicket(request);
    }
}
