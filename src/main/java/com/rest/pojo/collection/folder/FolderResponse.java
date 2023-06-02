package com.rest.pojo.collection.folder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.requestroot.RequestRootBase;
import com.rest.pojo.collection.requestroot.RequestRootResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResponse extends FolderBase {

  private List<RequestRootResponse> item;

  public FolderResponse() {
  }

  public FolderResponse(String name, List<RequestRootResponse> requestRoot) {
    super(name);
    this.item = requestRoot;
  }

  public List<RequestRootResponse> getItem() {
    return item;
  }

  public void setItem(List<RequestRootResponse> item) {
    this.item = item;
  }
}

