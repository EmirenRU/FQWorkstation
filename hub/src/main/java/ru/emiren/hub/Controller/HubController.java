package ru.emiren.hub.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hub")
public class HubController {

    // Name of site and IP to them
    private final Map<String, String> NameAndIpAddressMap = new HashMap<String, String>(
            Map.of(
                    "Работа с ВКР", "127.0.0.1:8080"
                    // Add additional data here after coma
            )
    );

    @GetMapping("")
    public String createHubPage(Model model){
        List<List<String>> addNameLater = new ArrayList<>();

        for (Map.Entry<String, String> entry : NameAndIpAddressMap.entrySet()){
            List<String> list = new ArrayList<>();
            list.add(entry.getKey());
            list.add(entry.getValue());
            addNameLater.add(list);
        }
        model.addAttribute("addNameLater", addNameLater);

        return "index";
    }
}
