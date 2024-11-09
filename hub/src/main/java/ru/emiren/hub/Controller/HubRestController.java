package ru.emiren.hub.Controller;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.emiren.hub.Model.WebSiteHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/api")
@Log4j2
public class HubRestController {

    private final WebSiteHolder webSiteHolder;

    @Autowired
    HubRestController(WebSiteHolder webSiteHolder) {
        this.webSiteHolder = webSiteHolder;
    }

    @GetMapping("/get-grid")
    public ResponseEntity<Integer> getNumber() {
        log.info("get-grid");
        return ResponseEntity.status(HttpStatus.OK).body(webSiteHolder.getNumberOfWebsites());
    }

    @GetMapping("/recieve/{id}")
    public ResponseEntity<String> recieve(@PathVariable Integer id) {
        log.info("In Recieve method");
        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(webSiteHolder.getWebsite(id)));
    }

    @GetMapping("/recieve_img/{id}")
    public ResponseEntity<?> recieveImg(@PathVariable String id) {
        log.info("In Recieve Img method");
        try {
            File file = ResourceUtils.getFile(String.format("classpath:static/img/%s", id));
            byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(id.endsWith(".png") ? "image/png" : "image/jpeg"));

            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(bytes);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
