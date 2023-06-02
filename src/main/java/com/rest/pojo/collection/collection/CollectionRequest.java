package com.rest.pojo.collection.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.folder.FolderBase;
import com.rest.pojo.collection.Info;
import com.rest.pojo.collection.folder.FolderRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRequest extends CollectionBase {

  private List<FolderRequest> item;

  public CollectionRequest() {
  }

  public CollectionRequest(Info info, List<FolderRequest> item) {
    super(info);
    this.item = item;
  }

  public List<FolderRequest> getItem() {
    return item;
  }

  public void setItem(List<FolderRequest> item) {
    this.item = item;
  }
}
