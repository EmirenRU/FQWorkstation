package ru.emiren.infosystemdepartment.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grpc/")
public class InterAppLayerController {
    private final String goIpLayer = "192.168.0.1:80";

    @GetMapping("/idk/why/i/need/this/grpc")
    public String idkWhyINeedThisGrpc() {
        return goIpLayer;
    }
}
