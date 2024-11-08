package ru.emiren.hub.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class WebSiteHolder {
    private List<Website> websites;

    public WebSiteHolder(List<Website> websites) {
        this.websites = websites;
    }

    public void storeWebsiteDetails(String name, String URL, String imgPath){
        websites.add(new Website(name, URL, imgPath));
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

    public int getNumberOfWebsites(){
        return websites.size();
    }

    public String toString(){
        return websites.toString();
    }
}
