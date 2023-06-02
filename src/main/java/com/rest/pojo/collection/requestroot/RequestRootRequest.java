package com.rest.pojo.collection.requestroot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.request.RequestBase;
import com.rest.pojo.collection.request.RequestRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootRequest extends RequestRootBase {

  private RequestRequest request;

  public RequestRootRequest() {
  }

  public RequestRootRequest(String name, RequestRequest request) {
    super(name);
    this.request = request;
  }

  public RequestRequest getRequest() {
    return request;
  }

  public void setRequest(RequestRequest request) {
    this.request = request;
  }
}
