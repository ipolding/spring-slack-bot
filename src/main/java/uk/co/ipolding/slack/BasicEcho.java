package uk.co.ipolding.slack;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class BasicEcho extends TextWebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        Map map = objectMapper.readValue(message.asBytes(), Map.class);
        if ("message".equals(map.get("type"))) {
            System.out.println(new String(message.asBytes()));
            echo(session, map);
        }
    }

    private void echo(WebSocketSession socketSession, Map message) throws IOException {
        TextMessage echo =
                new TextMessage(objectMapper.writeValueAsBytes(new Message(1, (String) message.get("channel"), (String) message.get("text"))));
        socketSession.sendMessage(echo);

    }
}
