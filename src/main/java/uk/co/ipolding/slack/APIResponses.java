package uk.co.ipolding.slack;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class APIResponses {

  public static class WsInitialization {
    private boolean ok;
    private String url;
    private Self self;

    public Self getSelf() {
      return self;
    }

    public void setSelf(Self self) {
      this.self = self;
    }

    public boolean isOk() {
      return ok;
    }

    public void setOk(boolean ok) {
      this.ok = ok;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public static class Self {
      private String id;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }
    }
  }

}
