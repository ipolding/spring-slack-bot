package uk.co.ipolding.slack;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;
import uk.co.ipolding.slack.entities.APIMethods;
import uk.co.ipolding.slack.entities.APIResponses;

import java.io.IOException;
import java.util.Optional;

import static uk.co.ipolding.slack.helpers.HttpClient.getUrl;

public class SlackConnector {

      public static APIResponses.WsInitialization connectToSlack() throws IOException {
        String apiAuthToken = System.getProperty("slack.api.token");
        String slackApiUrl = System.getProperty("slack.api.url");
        Assert.notNull(apiAuthToken, "please set slack.api.token");
        Assert.notNull(slackApiUrl, "slack api is null!!");
          String slackConnectionURL = slackApiUrl + APIMethods.RTM_API.value + "?token=" + apiAuthToken;
          Optional<byte[]> url = getUrl(slackConnectionURL);
        APIResponses.WsInitialization wsInitialization = null;
        if (url.isPresent()) {
            return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(url.get(), APIResponses.WsInitialization.class);
        }
        return wsInitialization;
    }

}