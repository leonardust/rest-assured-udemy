package com.rest.pojo.collection.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.Body;
import com.rest.pojo.collection.Header;
import com.rest.pojo.collection.Url;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse extends RequestBase {

  private Url url;

  public RequestResponse() {
  }

  public RequestResponse(Url url, String method, List<Header> header, Body body, String description) {
    super(method, header, body, description);
    this.url = url;
  }

  public Url getUrl() {
    return url;
  }

  public void setUrl(Url url) {
    this.url = url;
  }
}
