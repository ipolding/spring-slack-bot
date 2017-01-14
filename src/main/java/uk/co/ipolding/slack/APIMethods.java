package uk.co.ipolding.slack;

public enum APIMethods {

  RTM_API("rtm.start");

  public final String value;

  APIMethods(String value) {
    this.value = value;
  }
}
