package ru.emiren.infosystemdepartment.Controller.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

@Component
public class TerminalWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(TerminalWebSocketHandler.class);

    private final JdbcTemplate jdbcTemplate;

    public TerminalWebSocketHandler(@Qualifier("sqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String s = Objects.requireNonNull(session.getLocalAddress()).toString();
        log.info("New connection established with address: {}", s);
    }

    @Override
    protected void handleTextMessage(WebSocketSession socket, TextMessage message) throws Exception {
        String clientMessage = message.getPayload();
        log.info("Received: {}", clientMessage);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed with status: {}", status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Transport error", exception);
    }


}
