package ru.emiren.infosystemdepartment.Controller.Protocol;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.emiren.infosystemdepartment.Service.Download.DownloadService;

import java.util.List;

    @RequestMapping("/functions")
    @Controller
    @Slf4j
    public class FunctionsController {
        private final DownloadService downloadService;

        public FunctionsController(DownloadService downloadService) {
            this.downloadService = downloadService;
        }

        @GetMapping("")
        public String protocol() {
            return "functions";
        }

        @GetMapping("send-file-word")
        public String functions(@RequestParam("data") List<List<String>> data, HttpServletResponse response, Model model) {
            log.info("Start generating and sending file");
//            downloadService.generateAndSendFile(data, response);
            log.info("are we here?");
            return "Hello World";
        }
    }
