package ru.emiren.hub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.emiren.hub.Model.WebSiteHolder;

@RestController
@RequestMapping("/api")
public class HubRestController {


    private WebSiteHolder webSiteHolder;

    @Autowired
    HubRestController(WebSiteHolder webSiteHolder) {
        this.webSiteHolder = webSiteHolder;
    }

    @GetMapping("get-number")
    public ResponseEntity<Integer> getNumber() {
        return ResponseEntity.status(HttpStatus.OK).body(webSiteHolder.getNumberOfWebsites());
    }
}
