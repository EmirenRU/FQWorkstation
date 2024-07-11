package ru.emiren.infosystemdepartment.Controller.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grpc/")
public class InterAppLayerController {
    private final String goIpLayer = "192.168.0.1:80";

    @PostMapping("/v1/update-database")
    public void UpdateDatabase() {
        return;

    }
}
