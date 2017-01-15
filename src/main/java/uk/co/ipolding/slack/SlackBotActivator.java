package uk.co.ipolding.slack;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.container.jdk.client.JdkClientContainer;

import javax.websocket.*;
import java.net.URI;

import static uk.co.ipolding.slack.SlackConnector.connectToSlack;

public class SlackBotActivator {

    public static void powerOn(BotAwareMessageHandler botMessageHandler) {
        try {
            APIResponses.WsInitialization wsInitialization = connectToSlack();
            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();

            ClientManager client = ClientManager.createClient(JdkClientContainer.class.getName());
            client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    session.addMessageHandler(botMessageHandler.messageHandler);}
            }, cec, new URI(wsInitialization.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}