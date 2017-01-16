package uk.co.ipolding.slack.helpers;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.ipolding.slack.entities.APIMethods;
import uk.co.ipolding.slack.entities.APIResponses;

import java.io.IOException;
import java.util.Optional;

import static uk.co.ipolding.slack.helpers.HttpClient.getUrl;

public class SlackConnector {

      public static APIResponses.WsInitialization connectToSlack() throws IOException {
        String apiAuthToken = System.getProperty("slack.api.token");
        String slackApiUrl = System.getProperty("slack.api.url");
          if (null == apiAuthToken || null == slackApiUrl) {
              throw new ExceptionInInitializerError(
                      "Please ensure system properties slack.api.token and slack.api.url are set!");
          }
          String slackConnectionURL = slackApiUrl + APIMethods.RTM_API.value + "?token=" + apiAuthToken;
          Optional<byte[]> url = getUrl(slackConnectionURL);
        APIResponses.WsInitialization wsInitialization = null;
        if (url.isPresent()) {
            return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(url.get(), APIResponses.WsInitialization.class);
        }
        return wsInitialization;
    }

}