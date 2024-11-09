package ru.emiren.hub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.emiren.hub.Model.WebSiteHolder;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class HubController {

    private final WebSiteHolder webSiteHolder;


    @Autowired
    HubController(WebSiteHolder webSiteHolder) {
        this.webSiteHolder = webSiteHolder;
    }


    @GetMapping("")
    public String home(Model model) {
        return "redirect:/hub";
    }

    @GetMapping("/hub")
    public String createHubPage(Model model){
//        model.addAttribute("websiteList", webSiteHolder.getWebsites());
//        System.out.println(webSiteHolder);
        return "index";
    }
}
