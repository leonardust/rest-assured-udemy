package com.rest.pojo.collection.folder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.requestroot.RequestRootBase;
import com.rest.pojo.collection.requestroot.RequestRootRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderRequest extends FolderBase {

  private List<RequestRootRequest> item;

  public FolderRequest() {
  }

  public FolderRequest(String name, List<RequestRootRequest> requestRoot) {
    super(name);
    this.item = requestRoot;
  }

  public List<RequestRootRequest> getItem() {
    return item;
  }

  public void setItem(List<RequestRootRequest> item) {
    this.item = item;
  }
}

