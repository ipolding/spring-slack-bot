package uk.co.ipolding.slack;

import javax.websocket.MessageHandler;

abstract class BotAwareMessageHandler {

    private final String botId;
    public final MessageHandler messageHandler;

    protected BotAwareMessageHandler(String botId, MessageHandler messageHandler) {
        this.botId = botId;
        this.messageHandler = messageHandler;
    }


}
