package ru.emiren.infosystemdepartment.Controller.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TerminalWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(TerminalWebSocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception {
        String clientMessage = message.getPayload();
        log.info("Received: " + clientMessage);
    }


}
