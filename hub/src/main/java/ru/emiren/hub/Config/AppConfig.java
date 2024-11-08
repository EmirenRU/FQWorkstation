package ru.emiren.hub.Config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.emiren.hub.Model.WebSiteHolder;


@Configuration
@ImportResource("classpath:applicationContext.xml")
@ComponentScan(basePackages = "ru.emiren.hub")
public class AppConfig {

    @Bean()
    public WebSiteHolder webSiteHolder(ApplicationContext applicationContext) {
        return (WebSiteHolder) applicationContext.getBean("websites");
    }
}
