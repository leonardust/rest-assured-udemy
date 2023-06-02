package com.rest.pojo.collection.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.Info;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CollectionBase {

  private Info info;

  public CollectionBase() {
  }

  public CollectionBase(Info info) {
    this.info = info;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }
}
