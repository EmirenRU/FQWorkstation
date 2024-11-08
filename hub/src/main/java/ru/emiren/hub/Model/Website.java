package ru.emiren.hub.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Website {
    private String name;
    private String url;
    private String imgPath;


    @Override
    public String toString() {
        return "Website{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
