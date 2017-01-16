package uk.co.ipolding.slack;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.container.jdk.client.JdkClientContainer;
import uk.co.ipolding.slack.entities.APIResponses;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import static uk.co.ipolding.slack.helpers.SlackConnector.connectToSlack;

public abstract class SlackBot {

    private String botId;
    private Session session;
    private boolean on;

    protected final String getId() {
        if (on) {
            return this.botId;
        } else {
            throw new IllegalStateException(this.getClass().getSimpleName() + " is not switched on!");
        }
    }

    abstract void onMessage(String message);

    public void sendMessage(String text) throws IOException {
        if (this.on) {
            this.session.getBasicRemote().sendText(text);
        }
    }

    public final void switchOn() {
        final SlackBot bot = this;
        bot.on = true;

        try {
            APIResponses.WsInitialization wsInitialization = connectToSlack();
            this.botId = wsInitialization.getSelf().getId();

            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();

            ClientManager client = ClientManager.createClient(JdkClientContainer.class.getName());
            client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    bot.session = session;

                    session.addMessageHandler(String.class, bot::onMessage);}
            }, cec, new URI(wsInitialization.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
