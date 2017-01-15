package uk.co.ipolding.slack;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@PropertySource("classpath:slack.properties")
public class SlackListener {

  private static final Logger log = LoggerFactory.getLogger(SlackListener.class);

  @Value("${slack.api}")
  private String slackApiUrl;

  public SlackListener() {
      }

      private APIResponses.WsInitialization initializeSession() {
         String apiAuthToken = System.getProperty("slack.api.token");
         Assert.notNull(apiAuthToken);
        try {
          URL url = new URL(slackApiUrl + APIMethods.RTM_API.value + "?token=" + apiAuthToken);
          URLConnection urlConnection = url.openConnection();
          urlConnection.connect();
          byte[] response;
          try (InputStream inputStream = urlConnection.getInputStream(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            int data;
            while ((data = inputStream.read()) != -1) {
              byteArrayOutputStream.write(data);
            }
            response = byteArrayOutputStream.toByteArray();
            APIResponses.WsInitialization successfulInitialization = new ObjectMapper().readValue(response, APIResponses.WsInitialization.class);
            if (!successfulInitialization.isOk()) {
              log.error("FATAL : Couldn't initialize websocket session");
              throw new ExceptionInInitializerError("Couldn't initialize websocket session");
            } else {
              return successfulInitialization;
            }
        }} catch (Exception e) {
          e.printStackTrace();
          System.exit(1);
        }
        return null;
      }

  /** returns the bot id*/
  public String connectToSlack(WebSocketHandler websocketHandler) {
    log.info("Attemping connection to slack");
    APIResponses.WsInitialization initializedSession = initializeSession();
    initializeSession(initializedSession, websocketHandler);
    return initializedSession.getSelf().getId();
  }

  private void initializeSession(APIResponses.WsInitialization initializedSession, WebSocketHandler websocketHandler) {
    WebSocketClient webSocketClient = new StandardWebSocketClient();
    log.info("connecting using {}", initializedSession.getUrl());
    webSocketClient.doHandshake(websocketHandler, initializedSession.getUrl());
  }
}




