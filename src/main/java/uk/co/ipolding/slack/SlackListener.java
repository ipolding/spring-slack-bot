package uk.co.ipolding.slack;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@PropertySource("classpath:slack.properties")
public class SlackListener {

  private static final Logger log = LoggerFactory.getLogger(SlackListener.class);

  private final RestTemplate restTemplate;

  @Value("${slack.api}")
  private String slackApiUrl;

  public SlackListener(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /** returns the bot id*/
  public String connectToSlack(WebSocketHandler websocketHandler) {
    log.info("Attemping connection to slack");
    APIResponses.WsInitialization initializedSession = initializeSession();
    initializeSession(initializedSession, websocketHandler);
    return initializedSession.getSelf().getId();
  }

  private APIResponses.WsInitialization initializeSession() {
    String apiAuthToken = System.getProperty("slack.api.token");
    Assert.notNull(apiAuthToken);
    APIResponses.WsInitialization successfulInitialization = restTemplate.getForObject(slackApiUrl + APIMethods.RTM_API.value + "?token={token}", APIResponses.WsInitialization.class, apiAuthToken);
    if (!successfulInitialization.isOk()) {
      log.error("FATAL : Couldn't initialize websocket sesstion");
      System.exit(1);
    }
    return successfulInitialization;
  }

  private void initializeSession(APIResponses.WsInitialization initializedSession, WebSocketHandler websocketHandler) {
    WebSocketClient webSocketClient = new StandardWebSocketClient();
    log.info("connecting using {}", initializedSession.getUrl());
    webSocketClient.doHandshake(websocketHandler, initializedSession.getUrl());
  }
}




