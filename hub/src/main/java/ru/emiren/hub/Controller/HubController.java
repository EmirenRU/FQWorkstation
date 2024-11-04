package ru.emiren.hub.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hub")
public class HubController {

    // Name of site and IP to them
    private final List<List<String>> NameAndIpAddressMap = new ArrayList<>(
            List.of(
                // Add new data through List.of
                List.of(
                    "Работа с ВКР", "127.0.0.1:8080", "img/1.jpg"
                )
                    // Next example
                    // List.of(NameOfSite, ipAddress, imgSrc)
            )
    );

    @GetMapping("")
    public String createHubPage(Model model){
        model.addAttribute("addNameLater", NameAndIpAddressMap);
        return "index";
    }
}
