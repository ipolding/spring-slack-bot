package uk.co.ipolding.slack;

import java.io.IOException;

/**
 * Created by ian on 15/01/17.
 */
public class MainMethod {

  public static class EchoBot extends SlackBot {

    @Override
    void onMessage(String message) {
      if (message.contains(getId())) {
        System.out.println(message);
      }
    }
  }

  public static void main(String... args) throws IOException {
    EchoBot echoBot = new EchoBot();
    echoBot.switchOn();
    while (true) {}
  }

}
