package uk.co.ipolding.slack.entities;

public class Message {

  private long id;
  private final String type = "message";
  private String channel;
  private String text;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Message(long id, String channel, String text) {
    this.id = id;
    this.channel = channel;
    this.text = text;
  }

  public Message() {
  }
}
