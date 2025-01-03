package ru.emiren.infosystemdepartment.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ru.emiren.infosystemdepartment.Controller.Handler.TerminalWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TerminalWebSocketHandler terminalWebSocketHandler;

    WebSocketConfig(TerminalWebSocketHandler terminalWebSocketHandler) {
        this.terminalWebSocketHandler = terminalWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(terminalWebSocketHandler, "/ws").setAllowedOrigins("http://localhost:8080, ws://localhost:8080").withSockJS();
    }
}
