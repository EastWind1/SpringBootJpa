package test.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketService implements WebSocketHandler {
    private static Logger logger = LogManager.getLogger(WebSocketService.class);
    private static CopyOnWriteArraySet<WebSocketSession> clients = new CopyOnWriteArraySet();


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        if(clients.contains(webSocketSession)) {
            throw new Exception("已创建连接");
        }
        clients.add(webSocketSession);
        logger.info("New connection from " + webSocketSession.getPrincipal().getName() +". Total count: " + clients.size());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        logger.info("User " + webSocketSession.getPrincipal().getName() +"send: " + webSocketMessage.getPayload());
        for (WebSocketSession client : clients) {
                client.sendMessage(webSocketMessage);
        }

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.error("Connection error in" + webSocketSession.getPrincipal().getName() + ": " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        clients.remove(webSocketSession);
        logger.info("A connection closed. Total count: " + clients.size());

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
